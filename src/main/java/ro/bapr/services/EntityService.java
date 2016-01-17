package ro.bapr.services;

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
import ro.bapr.services.response.Context;
import ro.bapr.services.response.ContextCreator;
import ro.bapr.services.response.Result;

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
        ParsedQueryResult parsedResults;

        List<BindingSet> queryResult = genericService.query(queryString);
        if(queryResult != null && !queryResult.isEmpty()) {
            parsedResults = QueryResultsParser.parseBindingSets(queryResult);
        } else {
            //if no info extracted from local db, query external service
            saveData = true;
            List<Statement> queryResults = dbPediaRepository.query(queryString);
            parsedResults = QueryResultsParser.parseStatements(queryResults);

        }

        Context ctx = contextCreator.create(queryString, parsedResults.getVariableTypes());

        Result result = new Result();
        result.setItems(parsedResults.getResultItems());
        result.setContext(ctx);

        if(saveData) {
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

        //first query local db
        boolean saveData = false;
        ParsedQueryResult parsedResults;

        List<BindingSet> queryResult = genericService.query(queryString);
        if(queryResult != null && !queryResult.isEmpty()) {
            parsedResults = QueryResultsParser.parseBindingSets(queryResult);
        } else {
            //if no info extracted from local db, query external service
            saveData = true;
            List<BindingSet> queryResults = wifiService.query(queryString);
            parsedResults = QueryResultsParser.parseBindingSets(queryResults);

        }

        Context ctx = contextCreator.create(queryString, parsedResults.getVariableTypes());

        Result result = new Result();
        result.setItems(parsedResults.getResultItems());
        result.setContext(ctx);

        if(saveData) {
            genericService.save(result);
        }

        return result;
    }

    private double approximateRadiusForRegion() {

        return 1d;
    }

}
