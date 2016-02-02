package ro.bapr.internal.service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.openrdf.model.impl.SimpleValueFactory;
import org.openrdf.model.vocabulary.XMLSchema;

import ro.bapr.external.dbpedia.repository.api.DBPediaRepository;
import ro.bapr.external.openmobilenetwork.repository.api.OpenMobileNetworkRepository;
import ro.bapr.internal.model.request.EntityReview;
import ro.bapr.internal.model.response.Result;
import ro.bapr.internal.repository.api.GenericRepository;
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

    @Autowired
    private GenericRepository repository;

    @org.springframework.beans.factory.annotation.Value("${entities.get.all}")
    private String GET_ALL_ENTITIES;

    @org.springframework.beans.factory.annotation.Value("${wifi.get.all}")
    private String GET_ALL_WIFI;

    @org.springframework.beans.factory.annotation.Value("${entity.get.details}")
    private String GET_ENTITY_DETAILS;

    @org.springframework.beans.factory.annotation.Value("${dbpedia.resource.baser.url}")
    private String DBPEDIA_RESOURCE_BASE_URL;

    @Value("${entity.upsert.details.review}")
    protected String upsertReview;

    //endregion

    @Override
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

    @Override
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
        String transformedId = transformDbId(resourceId, appNamespace);
        String queryString = GET_ENTITY_DETAILS.replaceAll(":id:", transformedId);

        return query(queryString, () -> dbPediaRepository.query(queryString));
    }

    @Override
    public EntityReview addReview(String entityId, String userId, EntityReview review) {
        SimpleValueFactory factory = SimpleValueFactory.getInstance();

        String comment = factory.createLiteral(String.valueOf(review.getComment()),  XMLSchema.STRING).toString();
        String rating = factory.createLiteral(String.valueOf(review.getRating()), XMLSchema.INTEGER).toString();
        String locationId = factory.createIRI(appNamespace, String.valueOf(entityId)).toString();
        userId = factory.createIRI(appNamespace, userId).toString();

        final SecureRandom random = new SecureRandom();
        String revId = new BigInteger(130, random).toString(32);
        revId = factory.createIRI(appNamespace, revId).toString();

        String parsedQuery = upsertReview
                .replaceAll(":id:", userId)
                .replaceAll(":revId:", revId)
                .replaceAll(":locId:", locationId)
                .replaceAll(":rating:", rating)
                .replaceAll(":comment:", comment);
        repository.update(parsedQuery);

        return review;
    }
}
