package ro.bapr.internal.model;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import org.openrdf.model.IRI;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 15.01.2016.
 */
public class ParsedQueryResult {
    Map<String, IRI> variableTypes;
    Collection<LDObject> resultItems;

    public ParsedQueryResult(ConcurrentMap<String, IRI> variableTypes, Collection<LDObject> resultItems) {
        this.variableTypes = variableTypes;
        this.resultItems = resultItems;
    }

    public Map<String, IRI> getVariableTypes() {
        return variableTypes;
    }

    public Collection<LDObject> getResultItems() {
        return resultItems;
    }
}
