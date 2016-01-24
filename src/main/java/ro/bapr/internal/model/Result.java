package ro.bapr.internal.model;

import java.util.Collection;

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
    private Collection<LDObject> items;

    public Context getContext() {
        return context;
    }

    public Result setContext(Context context) {
        this.context = context;
        return this;
    }

    public Collection<LDObject> getItems() {
        return items;
    }

    public Result setItems(Collection<LDObject> items) {
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
