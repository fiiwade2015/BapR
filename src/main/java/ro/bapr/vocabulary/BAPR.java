package ro.bapr.vocabulary;

import org.openrdf.model.IRI;
import org.openrdf.model.impl.SimpleValueFactory;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 17.01.2016.
 */
public abstract class BAPR {
    public static final String NAMESPACE = "http://www.bapr.com/ontology/";
    public static final String PREFIX = "bapr";
    public static final IRI PASSWORD;

    static {
        SimpleValueFactory factory = SimpleValueFactory.getInstance();
        PASSWORD = factory.createIRI(NAMESPACE, "password");
    }
}
