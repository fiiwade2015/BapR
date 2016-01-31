package ro.bapr.internal.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.bapr.external.dbpedia.repository.api.DBPediaRepository;
import ro.bapr.external.openmobilenetwork.repository.api.OpenMobileNetworkRepository;
import ro.bapr.internal.model.response.Result;
import ro.bapr.internal.service.api.EntityService;

/**
 * Created by valentin.spac on 12/11/2015.
 */
@Service
public class EntityServiceImpl extends  AbstractService implements EntityService {

    //region Class members
    @Autowired
    private DBPediaRepository dbPediaRepository;

    @Autowired
    private OpenMobileNetworkRepository wifiService;

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
        String transformedId = transformDbId(resourceId, DBPEDIA_RESOURCE_BASE_URL);
        String queryString = GET_ENTITY_DETAILS.replaceAll(":id:", transformedId);

        return query(queryString, () -> dbPediaRepository.query(queryString));
    }

}
