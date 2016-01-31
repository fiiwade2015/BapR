package ro.bapr.vocabulary;

import org.openrdf.model.IRI;
import org.openrdf.model.impl.SimpleValueFactory;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 17.01.2016.
 */
public abstract class BAPR {
    public static final String NAMESPACE = "http://www.bapr.com/";
    public static final String ONTOLOGY_NAMESPACE = NAMESPACE + "ontology";
    public static final String PROPERTY_NAMESPACE = NAMESPACE + "property";
    public static final String JOURNEY_NAMESPACE = ONTOLOGY_NAMESPACE + "/Journey";
    public static final String USER_NAMESPACE = ONTOLOGY_NAMESPACE + "/User";

    public static final String PREFIX = "bapr";
    public static final IRI PASSWORD;
    public static final IRI JOURNEY;
    public static final IRI JOURNEY_NAME;
    public static final IRI JOURNEY_CREATION_DATE;
    public static final IRI JOURNEY_STATUS;
    public static final IRI JOURNEY_PROPERTY_HAS_LOCATION;
    public static final IRI HAS_JOURNEY;
    public static final IRI HAS_OWNER;
    public static final IRI JOURNEY_LOCATION;
    public static final IRI JOURNEY_LOCATION_ID; //face legatura cu un actual location/entity

    static {
        SimpleValueFactory factory = SimpleValueFactory.getInstance();
        PASSWORD = factory.createIRI(USER_NAMESPACE, "/password");
        JOURNEY = factory.createIRI(JOURNEY_NAMESPACE);
        JOURNEY_NAME = factory.createIRI(JOURNEY_NAMESPACE, "/name");
        JOURNEY_CREATION_DATE = factory.createIRI(JOURNEY_NAMESPACE, "/creationDate");
        JOURNEY_STATUS = factory.createIRI(JOURNEY_NAMESPACE, "/status");
        JOURNEY_LOCATION = factory.createIRI(JOURNEY_NAMESPACE, "/location");
        JOURNEY_LOCATION_ID = factory.createIRI(JOURNEY_NAMESPACE, "/locationId");

        JOURNEY_PROPERTY_HAS_LOCATION = factory.createIRI(PROPERTY_NAMESPACE, "/hasLocation");
        HAS_OWNER = factory.createIRI(PROPERTY_NAMESPACE, "/hasOwner");
        HAS_JOURNEY = factory.createIRI(PROPERTY_NAMESPACE, "/hasJourney");
    }
}
