package ro.bapr.service;

import java.util.List;
import java.util.Optional;

import org.openrdf.model.Statement;
import org.openrdf.query.BindingSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.bapr.external.dbpedia.repository.api.DBPediaRepository;
import ro.bapr.external.openmobilenetwork.repository.api.OpenMobileNetworkRepository;
import ro.bapr.internal.service.generic.GenericService;
import ro.bapr.internal.utils.parser.ParsedQueryResult;
import ro.bapr.internal.utils.parser.QueryResultsParser;
import ro.bapr.service.response.Context;
import ro.bapr.service.response.ContextCreator;
import ro.bapr.service.response.Result;

/**
 * Created by valentin.spac on 12/11/2015.
 */
@Service
public class EntityService {

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
    //endregion

    public Result getEntities(double lat, double lng, Optional<Double> optionalRadius) {
        double radius = optionalRadius.isPresent() ? optionalRadius.get() : 0;

        if(!optionalRadius.isPresent() || optionalRadius.get() == 0) {
            radius = approximateRadiusForRegion();
        }

        String queryString = GET_ALL_ENTITIES.replaceAll(":lat:", String.valueOf(lat))
                .replaceAll(":long:", String.valueOf(lng))
                .replaceAll(":radius:", String.valueOf(radius));


        //first query local db
        boolean saveData = false;
        List<BindingSet> queryResult = genericService.query(queryString);
        ParsedQueryResult parsedResults = QueryResultsParser.parseBindingSets(queryResult);

        //if no info extracted from local db, query external service
        if(parsedResults == null) {
            List<Statement> queryResults = dbPediaRepository.query(queryString);
            saveData = true;
            parsedResults = QueryResultsParser.parseStatements(queryResults);
        }

        Context ctx = contextCreator.create(queryString, parsedResults.getVariableTypes());

        Result result = new Result();
        result.setItems(parsedResults.getResultItems());
        result.setContext(ctx);

        if(saveData) {
            //save entities, trebuie pus chestia aia cu a dbo:Place
            genericService.save(result);
        }

        return result;
    }


    public Result getWifi(double lat, double lng, Optional<Double> optionalRadius) {
        double radius = optionalRadius.isPresent() ? optionalRadius.get() : 0;

        if (!optionalRadius.isPresent() || optionalRadius.get() == 0) {
            radius = approximateRadiusForRegion();
        }

        String queryString = GET_ALL_WIFI.replaceAll(":lat:", String.valueOf(lat))
                .replaceAll(":long:", String.valueOf(lng))
                .replaceAll(":radius:", String.valueOf(radius));

        List<Statement> queryResults =  wifiService.query(queryString);
        ParsedQueryResult parsedResults = QueryResultsParser.parseStatements(queryResults);

        Context ctx = contextCreator.create(queryString, parsedResults.getVariableTypes());

        Result result = new Result();
        result.setItems(parsedResults.getResultItems());
        result.setContext(ctx);

        return result;
    }

    private double approximateRadiusForRegion() {

        return 1d;
    }

}
