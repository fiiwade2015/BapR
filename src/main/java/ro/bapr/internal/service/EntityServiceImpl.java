package ro.bapr.internal.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.openrdf.query.BindingSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.bapr.external.dbpedia.repository.api.DBPediaRepository;
import ro.bapr.external.openmobilenetwork.repository.api.OpenMobileNetworkRepository;
import ro.bapr.internal.model.Context;
import ro.bapr.internal.model.ParsedQueryResult;
import ro.bapr.internal.model.Result;
import ro.bapr.internal.service.api.EntityService;
import ro.bapr.internal.service.api.GenericService;
import ro.bapr.internal.utils.ContextCreator;
import ro.bapr.internal.utils.ServiceUtil;
import ro.bapr.internal.utils.parser.QueryResultsParser;

/**
 * Created by valentin.spac on 12/11/2015.
 */
@Service
public class EntityServiceImpl implements EntityService {

    //region Class members
    @Autowired
    private DBPediaRepository dbPediaRepository;

    @Autowired
    private GenericService genericService;

    @Autowired
    private OpenMobileNetworkRepository wifiService;

    @Autowired
    private ContextCreator contextCreator;

    @org.springframework.beans.factory.annotation.Value("${entities.get.all}")
    private String GET_ALL_ENTITIES;

    @org.springframework.beans.factory.annotation.Value("${wifi.get.all}")
    private String GET_ALL_WIFI;

    @org.springframework.beans.factory.annotation.Value("${entity.get.details}")
    private String GET_ENTITY_DETAILS;

    @org.springframework.beans.factory.annotation.Value("${dbpedia.resource.baser.url}")
    private String DBPEDIA_RESOURCE_BASE_URL;


    //endregion

    public Result getEntities(double lat, double lng, Optional<Double> optionalRadius) {
        double radius = optionalRadius.isPresent() ? optionalRadius.get() : 0;

        if(!optionalRadius.isPresent() || optionalRadius.get() == 0) {
            radius = approximateRadiusForRegion();
        }

        String queryString = GET_ALL_ENTITIES.replaceAll(":lat:", String.valueOf(lat))
                .replaceAll(":long:", String.valueOf(lng))
                .replaceAll(":radius:", String.valueOf(radius));

        return query(queryString, () -> dbPediaRepository.query(queryString));
    }


    public Result getWifi(double lat, double lng, Optional<Double> optionalRadius) {
        double radius = optionalRadius.isPresent() ? optionalRadius.get() : 0;

        if (!optionalRadius.isPresent() || optionalRadius.get() == 0) {
            radius = approximateRadiusForRegion();
        }

        String queryString = GET_ALL_WIFI.replaceAll(":lat:", String.valueOf(lat))
                .replaceAll(":long:", String.valueOf(lng))
                .replaceAll(":radius:", String.valueOf(radius));

        return query(queryString, () -> wifiService.query(queryString));
    }

    private double approximateRadiusForRegion() {

        return 1d;
    }


    @Override
    public Result getEntityDetails(String resourceId) {
        String transformedId = ServiceUtil.transformDbId(resourceId, DBPEDIA_RESOURCE_BASE_URL);
        String queryString = GET_ENTITY_DETAILS.replaceAll(":id:", transformedId);

        return query(queryString, () -> dbPediaRepository.query(queryString));
    }

    private Result query(String queryString,
                         Supplier<List<BindingSet>> responseSupplier) {

        List<BindingSet> queryResults = genericService.query(queryString);
        boolean saveData = false;
        if (queryResults == null || queryResults.isEmpty()) {
            saveData = true;
            queryResults = responseSupplier.get();
        }

        Optional<ParsedQueryResult> parsedResultsOptional = QueryResultsParser.parseBindingSets(queryResults);

        Result result = new Result();
        if (parsedResultsOptional.isPresent()){
            ParsedQueryResult parsedResults = parsedResultsOptional.get();
            Context ctx = contextCreator.create(queryString, parsedResults.getVariableTypes());

            result.setItems(parsedResults.getResultItems());
            result.setContext(ctx);

            if (saveData) {
                genericService.save(result);
            }
        }

        return result;
    }

}
