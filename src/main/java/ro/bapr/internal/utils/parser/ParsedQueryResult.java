package ro.bapr.internal.utils.parser;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import org.openrdf.model.IRI;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 15.01.2016.
 */
public class ParsedQueryResult {
    Map<String, IRI> variableTypes;
    List<Map<String, Object>> resultItems;

    public ParsedQueryResult(ConcurrentMap<String, IRI> variableTypes, List<Map<String, Object>> resultItems) {
        this.variableTypes = variableTypes;
        this.resultItems = resultItems;
    }

    public Map<String, IRI> getVariableTypes() {
        return variableTypes;
    }

    public List<Map<String, Object>> getResultItems() {
        return resultItems;
    }
}
