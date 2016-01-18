package ro.bapr.internal.model;

import java.util.Date;
import java.util.List;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 18.01.2016.
 */

public class Journey {
    private String name;
    private List<String> locationIds;
    private String status;// = "building";
    private Date creationDate;// = new Date();

    public String getName() {
        return name;
    }

    public Journey setName(String name) {
        this.name = name;
        return this;
    }

    public List<String> getLocationIds() {
        return locationIds;
    }

    public Journey setLocationIds(List<String> locationIds) {
        this.locationIds = locationIds;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Journey setStatus(String status) {
        this.status = status;
        return this;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Journey setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }
}
