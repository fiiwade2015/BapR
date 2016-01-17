package ro.bapr.services.response;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.openrdf.model.IRI;
import org.openrdf.model.Value;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 06.12.2015.
 */

public class Context {
    private Map<String, Map<String, Object>> items;

    @JsonIgnore
    private Map<IRI, Value> additionalProperties;

    @JsonAnyGetter
    public Map<String, Map<String, Object>> getItems() {
        return items;
    }

    public Context setItems(Map<String, Map<String, Object>> items) {
        this.items = items;
        return this;
    }

    public Map<IRI, Value> getAdditionalProperties() {
        return additionalProperties;
    }

    public Context setAdditionalProperties(Map<IRI, Value> additionalProperties) {
        this.additionalProperties = additionalProperties;
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
