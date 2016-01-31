package ro.bapr.internal.repository;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.openrdf.model.IRI;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.SimpleValueFactory;
import org.openrdf.model.vocabulary.FOAF;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.model.vocabulary.XMLSchema;
import org.openrdf.query.QueryLanguage;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.springframework.beans.factory.annotation.Value;

import ro.bapr.internal.model.request.Journey;
import ro.bapr.internal.model.request.JourneyUpdate;
import ro.bapr.internal.model.request.RegisterModel;
import ro.bapr.internal.model.request.UserLocation;
import ro.bapr.internal.repository.api.AbstractRepository;
import ro.bapr.internal.repository.api.UserRepository;
import ro.bapr.vocabulary.BAPR;
import ro.bapr.vocabulary.GEO;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 17.01.2016.
 */
@org.springframework.stereotype.Repository
public class UserRepositoryImpl extends AbstractRepository implements UserRepository {
    @Value("${user.update.current.location}")
    protected String updateUL;

    @Value("${user.update.journey.location}")
    protected String updateJourneyLocation;

    @Override
    public String registerUser(RegisterModel model) {
        RepositoryConnection conn = null;
        try {
            Repository repo = getRepository();
            ValueFactory valueFactory = repo.getValueFactory();
            conn = repo.getConnection();

            SecureRandom random = new SecureRandom();
            String userId = new BigInteger(130, random).toString(32);

            IRI entityId = valueFactory.createIRI(appNamespace, userId);

            conn.add(entityId, FOAF.NAME, valueFactory.createLiteral(model.getUsername(), "en"));
            conn.add(entityId, BAPR.PASSWORD, valueFactory.createLiteral(model.getPassword(), XMLSchema.STRING));
            conn.add(entityId, RDF.TYPE, FOAF.PERSON);

            conn.add(entityId, GEO.LAT,
                    valueFactory.createLiteral(Double.toString(model.getLatitude()), XMLSchema.FLOAT));

            conn.add(entityId, GEO.LONG,
                    valueFactory.createLiteral(Double.toString(model.getLongitude()), XMLSchema.FLOAT));

            return userId;
        } finally {
            if(conn != null) {
                conn.commit();
                conn.close();
            }
        }
    }

    @Override
    public String addJourney(Journey journey, String userId) {
        RepositoryConnection conn = null;
        try {
            Repository repo = getRepository();
            ValueFactory valueFactory = repo.getValueFactory();
            conn = repo.getConnection();

            final SecureRandom random = new SecureRandom();
            String randomId = new BigInteger(130, random).toString(32);

            IRI journeyId = valueFactory.createIRI(appNamespace, randomId);

            conn.add(journeyId, RDF.TYPE, BAPR.JOURNEY);
            conn.add(journeyId, BAPR.JOURNEY_NAME, valueFactory.createLiteral(journey.getName(), "en"));
            conn.add(journeyId, BAPR.JOURNEY_STATUS, valueFactory.createLiteral(journey.getStatus(), "en"));
            conn.add(journeyId, BAPR.JOURNEY_CREATION_DATE, valueFactory.createLiteral(journey.getCreationDate()));

            final RepositoryConnection finalConn = conn;
            journey.getLocationIds()
                    .forEach(locationId -> {
                        String randomString = new BigInteger(130, random).toString(32);
                        IRI journeyLocationId = valueFactory.createIRI(appNamespace, randomString);

                        finalConn.add(journeyLocationId, RDF.TYPE, BAPR.JOURNEY_LOCATION);
                        finalConn.add(journeyLocationId, BAPR.JOURNEY_LOCATION_ID,
                                valueFactory.createIRI(appNamespace, locationId));
                        finalConn.add(journeyLocationId,
                                BAPR.JOURNEY_STATUS, valueFactory.createLiteral("not visited"));

                        finalConn.add(journeyId,
                                BAPR.JOURNEY_PROPERTY_HAS_LOCATION, journeyLocationId);

                    });


            IRI ownerIRI = valueFactory.createIRI(appNamespace, userId);
            conn.add(ownerIRI, BAPR.HAS_JOURNEY, journeyId);
            conn.add(journeyId, BAPR.HAS_OWNER, ownerIRI);

            return randomId;
        } finally {
            if(conn != null) {
                conn.commit();
                conn.close();
            }
        }
    }

    @Override
    public UserLocation updateUserLocation(UserLocation userLocation, String userId) {
        Repository repo = this.getRepository();
        RepositoryConnection conn = repo.getConnection();
        SimpleValueFactory factory = SimpleValueFactory.getInstance();

        String latitude = factory.createLiteral(String.valueOf(userLocation.getLatitude()), XMLSchema.FLOAT).toString();
        String longitude = factory.createLiteral(String.valueOf(userLocation.getLongitude()), XMLSchema.FLOAT).toString();
        userId = factory.createIRI(appNamespace, userId).toString();

        updateUL = updateUL.replaceAll(":id:", userId)
                .replaceAll(":lat:", latitude)
                .replaceAll(":long:", longitude);

        conn.prepareUpdate(QueryLanguage.SPARQL, updateUL).execute();
        conn.commit();

        return userLocation;
    }

    @Override
    public JourneyUpdate updateUserJourney(JourneyUpdate journeyUpdate, String userId) {

        Repository repo = this.getRepository();
        RepositoryConnection conn = repo.getConnection();
        SimpleValueFactory factory = SimpleValueFactory.getInstance();

        String journeyId = factory.createIRI(appNamespace, String.valueOf(journeyUpdate.getJourneyId())).toString();
        String entityId = factory.createIRI(appNamespace, String.valueOf(journeyUpdate.getEntityId())).toString();
        String status = factory.createLiteral(String.valueOf(journeyUpdate.getStatus()), XMLSchema.STRING).toString();
        userId = factory.createIRI(appNamespace, userId).toString();

        updateJourneyLocation = updateJourneyLocation
                .replaceAll(":id:", userId)
                .replaceAll(":journeyId:", journeyId)
                .replaceAll(":entityId:", entityId)
                .replaceAll(":status:", status);

        conn.prepareUpdate(QueryLanguage.SPARQL, updateJourneyLocation).execute();
        conn.commit();

        return journeyUpdate;
    }
}
