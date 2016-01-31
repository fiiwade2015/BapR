package ro.bapr.internal.model.request;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 17.01.2016.
 */
public class RegisterModel {
    private String username;
    private String password;
    private double latitude;
    private double longitude;

    public String getUsername() {
        return username;
    }

    public RegisterModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RegisterModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public double getLatitude() {
        return latitude;
    }

    public RegisterModel setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public double getLongitude() {
        return longitude;
    }

    public RegisterModel setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }
}


