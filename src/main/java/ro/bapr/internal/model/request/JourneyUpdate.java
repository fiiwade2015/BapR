package ro.bapr.internal.model.request;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 31.01.2016.
 */
public class JourneyUpdate {
    private String entityId;
    private String status;

    public String getEntityId() {
        return entityId;
    }

    public JourneyUpdate setEntityId(String entityId) {
        this.entityId = entityId;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public JourneyUpdate setStatus(String status) {
        this.status = status;
        return this;
    }
}
