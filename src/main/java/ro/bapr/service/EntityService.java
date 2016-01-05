package ro.bapr.service;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import org.openrdf.model.IRI;
import org.openrdf.model.Literal;
import org.openrdf.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ro.bapr.external.dbpedia.repository.api.DBPediaRepository;
import ro.bapr.external.openmobilenetwork.repository.api.OpenMobileNetworkRepository;
import ro.bapr.internal.service.generic.GenericService;
import ro.bapr.response.Context;
import ro.bapr.response.Result;
import ro.bapr.util.ContextCreator;

/**
 * Created by valentin.spac on 12/11/2015.
 */
@Service
public class EntityService {
    @Autowired
    private DBPediaRepository dbPediaRepository;

    @Autowired
    private GenericService genericService;

    @Autowired
    private OpenMobileNetworkRepository wifiService;

    @Autowired
    private ContextCreator contextCreator;

    @Value("${entities.get.all}")
    private String GET_ALL_ENTITIES;

    @Value("${wifi.get.all}")
    private String GET_ALL_WIFI;

    public Result getEntities(double lat, double lng, Optional<Double> optionalRadius) {
        double radius = optionalRadius.isPresent() ? optionalRadius.get() : 0;

        if(!optionalRadius.isPresent() || optionalRadius.get() == 0) {
            radius = approximateRadiusForRegion();
        }

        String queryString = GET_ALL_ENTITIES.replaceAll(":lat:", String.valueOf(lat))
                .replaceAll(":long:", String.valueOf(lng))
                .replaceAll(":radius:", String.valueOf(radius));


        //first query local db
        genericService.query(queryString);

        //query external db
        Model items = dbPediaRepository.query(queryString);
        //parse items, generate context and build result
        Result result = buildResponse(items, queryString);
        //save in local db>?????

        return result;

    }

    private Result buildResponse(Model model, String queryString) {
        model.iterator().forEachRemaining(set -> {
            if(set.getObject() instanceof Literal) {
                System.out.println(((Literal) set.getObject()).getDatatype());
            } else if(set.getObject() instanceof IRI) {
                System.out.println(((IRI) set.getObject()).getLocalName());
            }
        });

        Context ctx = contextCreator.create(queryString);
        Result result = new Result();
        result.setContext(ctx);

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

        Context ctx = contextCreator.create(queryString);
        Collection<Map<String, Object>> items = wifiService.query(queryString);

        Result result = new Result();
        result.setContext(ctx);
        result.setItems(items);

        return result;
    }

    private double approximateRadiusForRegion() {

        return 1d;
    }
}
