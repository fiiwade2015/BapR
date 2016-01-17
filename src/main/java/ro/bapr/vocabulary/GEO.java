package ro.bapr.vocabulary;

import org.openrdf.model.IRI;
import org.openrdf.model.impl.SimpleValueFactory;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 17.01.2016.
 */
public abstract class GEO {
    public static final String NAMESPACE = "http://www.w3.org/2003/01/geo/wgs84_pos#";
    public static final String PREFIX = "geo";
    public static final IRI LAT;
    public static final IRI LONG;
    public static final IRI POINT;


    static {
        SimpleValueFactory factory = SimpleValueFactory.getInstance();
        LAT = factory.createIRI(NAMESPACE, "lat");
        LONG = factory.createIRI(NAMESPACE, "long");
        POINT = factory.createIRI(NAMESPACE, "Point");
    }
}
