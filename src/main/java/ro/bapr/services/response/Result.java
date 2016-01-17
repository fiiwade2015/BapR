package ro.bapr.services.response;

import java.util.Collection;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 06.12.2015.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result {
    @JsonProperty("@context")
    private Context context;

    @JsonProperty("@graph")
    private Collection<Map<String, Object>> items;

    public Context getContext() {
        return context;
    }

    public Result setContext(Context context) {
        this.context = context;
        return this;
    }

    public Collection<Map<String, Object>> getItems() {
        return items;
    }

    public Result setItems(Collection<Map<String, Object>> items) {
        this.items = items;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Result{");
        sb.append("context=").append(context);
        sb.append(", items=").append(items);
        sb.append('}');
        return sb.toString();
    }
}
