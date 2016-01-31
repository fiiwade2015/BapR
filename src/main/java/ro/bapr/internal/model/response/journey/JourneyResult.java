package ro.bapr.internal.model.response.journey;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonProperty;

import ro.bapr.internal.model.response.Context;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 31.01.2016.
 */
public class JourneyResult {
    @JsonProperty("@context")
    private Context context;

    @JsonProperty("@graph")
    private Collection<JourneyData> items;

    public Context getContext() {
        return context;
    }

    public JourneyResult setContext(Context context) {
        this.context = context;
        return this;
    }

    public Collection<JourneyData> getItems() {
        return items;
    }

    public JourneyResult setItems(Collection<JourneyData> items) {
        this.items = items;
        return this;
    }
}
