package ro.bapr.internal.utils.parser;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.openrdf.model.IRI;
import org.openrdf.model.Literal;
import org.openrdf.model.Statement;
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
    @Deprecated
    private static final String SPARQL_RESULTS_SOLUTION = "http://www.w3.org/2005/sparql-results#solution";
    @Deprecated
    private static final String SPARQL_RESULTS_BINDING = "http://www.w3.org/2005/sparql-results#binding";
    @Deprecated
    private static final String SPARQL_RESULTS_VALUE = "http://www.w3.org/2005/sparql-results#value";
    @Deprecated
    private static final String SPARQL_RESULTS_VARIABLE= "http://www.w3.org/2005/sparql-results#variable";


    public static ParsedQueryResult parseBindingSets(List<BindingSet> result) {
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

        return parsedResult;
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


    private static void buildSolutionResult(List<Statement> solutionBindings,
                                            Map<String, Statement> variables,
                                            Map<String, Statement> values,
                                            ConcurrentMap<String, IRI> variableTypes,
                                            LDResult ldResult, Statement solution) {

        LDObject ldObject = new LDObject();
        List<Statement> bindings = getBindingsForSolution(solutionBindings, solution);

        bindings.forEach(binding -> {
            String bindingKey = binding.getObject().stringValue();
            Statement variableStmt = variables.get(bindingKey);
            Statement valueStmt = values.get(bindingKey);

            String bindingName = variableStmt.getObject().stringValue();
            String stringValue = valueStmt.getObject().stringValue();
            Value valueStmtObject = valueStmt.getObject();

            KeyValue item = buildLDObject(variableTypes, ldObject, bindingName, stringValue, valueStmtObject);
            ldObject.addKeyValue(item);
        });

        ldResult.add(ldObject);
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

    private static List<Statement> getBindingsForSolution(List<Statement> solutionBindings, Statement solution) {
        return solutionBindings.stream()
                //.parallel()
                .filter(binding ->
                        binding.getSubject().stringValue().equalsIgnoreCase(solution.getObject().stringValue()))
                .collect(Collectors.toList());
    }

    /**
     * Updates the variable types (string,float etc) needed for context and local storage
     */
    private static void updateVariableTypes(ConcurrentMap<String, IRI> variableTypes,
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

    @Deprecated
    public static ParsedQueryResult parseStatements(List<Statement> queryResults) {
        List<Statement> querySolutions = filterCollectionBy(queryResults, SPARQL_RESULTS_SOLUTION);
        List<Statement> solutionBindings =  filterCollectionBy(queryResults, SPARQL_RESULTS_BINDING);
        Map<String, Statement> variables = getFilteredMapBy(queryResults, SPARQL_RESULTS_VARIABLE);
        Map<String, Statement> values = getFilteredMapBy(queryResults, SPARQL_RESULTS_VALUE);

        return buildParsedResults(querySolutions, solutionBindings, variables, values);
    }

    @Deprecated
    private static ParsedQueryResult buildParsedResults(List<Statement> querySolutions, List<Statement> solutionBindings,
                                                        Map<String, Statement> variables, Map<String, Statement> values) {
        ConcurrentMap<String, IRI> variableTypes = new ConcurrentHashMap<>();
        LDResult finalResult = new LDResult();

        querySolutions.stream()
                .parallel()
                .forEach(solution -> buildSolutionResult(solutionBindings, variables, values, variableTypes, finalResult, solution));

        Collection<LDObject> results = finalResult.getMergedResults();
        return new ParsedQueryResult(variableTypes, results);
    }

    @Deprecated
    private static Map<String, Statement> getFilteredMapBy(List<Statement> queryResults, String filterItem) {
        return queryResults.stream().parallel()
                .filter(statement -> filterItem.equalsIgnoreCase(statement.getPredicate().stringValue()))
                .collect(Collectors.toMap(s -> s.getSubject().stringValue(), Function.identity()));
    }

    @Deprecated
    private static List<Statement> filterCollectionBy(List<Statement> queryResults, String filterItem) {
        return queryResults.stream().parallel()
                .filter(statement -> filterItem.equalsIgnoreCase(statement.getPredicate().stringValue()))
                .collect(Collectors.toList());
    }


}
