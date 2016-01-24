package ro.bapr.internal.repository;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.openrdf.model.IRI;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.vocabulary.FOAF;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.model.vocabulary.XMLSchema;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;

import ro.bapr.internal.model.Journey;
import ro.bapr.internal.model.RegisterModel;
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

            SecureRandom random = new SecureRandom();
            String randomId = new BigInteger(130, random).toString(32);

            IRI journeyId = valueFactory.createIRI(appNamespace, randomId);

            conn.add(journeyId, RDF.TYPE, BAPR.JOURNEY);
            conn.add(journeyId, BAPR.JOURNEY_NAME, valueFactory.createLiteral(journey.getName(), "en"));
            conn.add(journeyId, BAPR.JOURNEY_STATUS, valueFactory.createLiteral(journey.getStatus(), "en"));
            conn.add(journeyId, BAPR.JOURNEY_CREATION_DATE, valueFactory.createLiteral(journey.getCreationDate()));

            final RepositoryConnection finalConn = conn;
            journey.getLocationIds()
                    .forEach(locationId -> finalConn.add(journeyId,
                            BAPR.JOURNEY_PROPERTY_HAS_LOCATION, valueFactory.createIRI(BAPR.NAMESPACE, locationId)));


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
}
