package ro.bapr.internal.utils;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 23.01.2016.
 */
public abstract class ServiceUtil {

    public static String transformDbId(String id, String dbBaseUrl) {
        return dbBaseUrl + id;
    }
}
