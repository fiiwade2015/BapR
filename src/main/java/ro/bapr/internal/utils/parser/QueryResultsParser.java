package ro.bapr.internal.utils.parser;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.openrdf.model.IRI;
import org.openrdf.model.Literal;
import org.openrdf.model.Value;
import org.openrdf.model.vocabulary.XMLSchema;
import org.openrdf.query.BindingSet;

import ro.bapr.internal.model.KeyValue;
import ro.bapr.internal.model.LDObject;
import ro.bapr.internal.model.LDResult;
import ro.bapr.internal.model.ParsedQueryResult;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 15.01.2016.
 */
public abstract class QueryResultsParser {

    public static Optional<ParsedQueryResult> parseBindingSets(List<BindingSet> result) {
        ConcurrentMap<String, IRI> variableTypes = new ConcurrentHashMap<>();
        LDResult finalResult = new LDResult();

        result.stream()
                .parallel()
                .forEach(set -> finalResult.add(parseBindingSet(variableTypes, set)));

        ParsedQueryResult parsedResult = null;
        Collection<LDObject> responseObjects = finalResult.getMergedResults();

        if(!variableTypes.isEmpty() && !responseObjects.isEmpty()) {
           parsedResult = new ParsedQueryResult(variableTypes, responseObjects);
        }

        return Optional.ofNullable(parsedResult);
    }

    private static LDObject parseBindingSet(ConcurrentMap<String, IRI> variableTypes, BindingSet set) {
        LDObject ldObject = new LDObject();

        set.getBindingNames().stream()
                //.parallel()
                .forEach(binding -> {
                    Value value = set.getValue(binding);
                    String bindingName = set.getBinding(binding).getName();
                    String stringValue = value.stringValue();

                    KeyValue item = buildLDObject(variableTypes, ldObject, bindingName, stringValue, value);
                    ldObject.addKeyValue(item);
                });

        return ldObject;
    }

    private static KeyValue buildLDObject(ConcurrentMap<String, IRI> variableTypes,
                                          LDObject ldObject,
                                          String bindingName,
                                          String stringValue,
                                          Value valueStmtObject) {
        updateVariableTypes(variableTypes, bindingName, valueStmtObject);

        if("id".equalsIgnoreCase(bindingName)) {
            ldObject.addKeyValue(new KeyValue("seeAlso", stringValue));
            stringValue = ((IRI) valueStmtObject).getLocalName();
        }

        KeyValue item = new KeyValue();
        item.setKey(bindingName);
        item.put(stringValue);

        return item;

    }

    /**
     * Updates the variable types (string,float etc) needed for context and local storage
     */
    public static void updateVariableTypes(ConcurrentMap<String, IRI> variableTypes,
                                     String variableName,
                                     Value value) {
        if(value instanceof Literal) {
            Literal literalValue = (Literal)value;
            variableTypes.putIfAbsent(variableName, literalValue.getDatatype());
        } else if(value instanceof IRI) {
            variableTypes.putIfAbsent(variableName, XMLSchema.STRING);

        } else {
            //for debugging/development purposes
            throw new RuntimeException("Variable type is not literal or IRI! "
                    + value.getClass().getSuperclass().getCanonicalName());
        }
    }

}
