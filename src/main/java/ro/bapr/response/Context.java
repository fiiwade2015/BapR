package ro.bapr.response;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 06.12.2015.
 */

public class Context {
    private Map<String, String> items;

    @JsonAnyGetter
    public Map<String, String> getItems() {
        return items;
    }

    public Context setItems(Map<String, String> items) {
        this.items = items;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Context{");
        sb.append("items=").append(items);
        sb.append('}');
        return sb.toString();
    }
}
