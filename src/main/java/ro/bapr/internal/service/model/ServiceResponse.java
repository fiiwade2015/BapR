package ro.bapr.internal.service.model;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 17.01.2016.
 */
public class ServiceResponse<T> {
    private T result;
    private Messages message;
    private Status status;

    public T getResult() {
        return result;
    }

    public ServiceResponse setResult(T result) {
        this.result = result;
        return this;
    }

    public Messages getMessage() {
        return message;
    }

    public ServiceResponse setMessage(Messages message) {
        this.message = message;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public ServiceResponse setStatus(Status status) {
        this.status = status;
        return this;
    }

    public enum Status {
        SUCCESS, FAIL
    }

    public enum Messages {
        USER_EXISTS("User already exists");

        private String description;

        Messages(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}
