package ro.bapr.controller;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 31.10.2015.
 */
class Endpoint {
    public static final String ENTITIES = "/entities";
    public static final String DETAILS = "/details";
    public static final String WIFI = "/wifi";
    public static final String REGISTER = "/register";
    public static final String ENTITY_DETAILS = ENTITIES + "/{id}" + DETAILS;
}
