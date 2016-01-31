package ro.bapr.internal.model.request;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 31.01.2016.
 */
public class UserLocation {
    private long latitude;
    private long longitude;

    public long getLatitude() {
        return latitude;
    }

    public UserLocation setLatitude(long latitude) {
        this.latitude = latitude;
        return this;
    }

    public long getLongitude() {
        return longitude;
    }

    public UserLocation setLongitude(long longitude) {
        this.longitude = longitude;
        return this;
    }
}
