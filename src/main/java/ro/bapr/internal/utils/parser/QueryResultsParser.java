package ro.bapr.internal.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;
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
import org.openrdf.model.vocabulary.RDFS;
import org.openrdf.model.vocabulary.XMLSchema;
import org.openrdf.query.BindingSet;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 15.01.2016.
 */
public abstract class QueryResultsParser {
    private static final String SPARQL_RESULTS_SOLUTION = "http://www.w3.org/2005/sparql-results#solution";
    private static final String SPARQL_RESULTS_BINDING = "http://www.w3.org/2005/sparql-results#binding";
    private static final String SPARQL_RESULTS_VALUE = "http://www.w3.org/2005/sparql-results#value";
    private static final String SPARQL_RESULTS_VARIABLE= "http://www.w3.org/2005/sparql-results#variable";

    public static ParsedQueryResult parseStatements(List<Statement> queryResults) {
        List<Statement> querySolutions = filterCollectionBy(queryResults, SPARQL_RESULTS_SOLUTION);
        List<Statement> solutionBindings =  filterCollectionBy(queryResults, SPARQL_RESULTS_BINDING);
        Map<String, Statement> variables = getFilteredMapBy(queryResults, SPARQL_RESULTS_VARIABLE);
        Map<String, Statement> values = getFilteredMapBy(queryResults, SPARQL_RESULTS_VALUE);

        return buildParsedResults(querySolutions, solutionBindings, variables, values);
    }

    public static ParsedQueryResult parseBindingSets(List<BindingSet> result) {
        ConcurrentMap<String, IRI> variableTypes = new ConcurrentHashMap<>();
        List<Map<String, Object>> items = new ArrayList<>();

        result.stream()
                .parallel()
                .forEach(set -> {
                    Map<String, Object> itemBindings = new HashMap<>();

                    set.getBindingNames().stream()
                            //.parallel()
                            .forEach(binding -> {
                                Value value = set.getValue(binding);
                                String bindingName = set.getBinding(binding).getName();
                                String stringValue = value.stringValue();

                                updateVariableTypes(variableTypes, bindingName, value);

                                if ("id".equalsIgnoreCase(bindingName)) {
                                   // stringValue = addSeeAlso(itemBindings, (IRI) value, stringValue, variableTypes);
                                    stringValue = ((IRI)value).getLocalName();
                                }
                                if("seeAlso".equalsIgnoreCase(bindingName)) {
                                    List<String> seeAlso = new ArrayList<>();
                                    seeAlso.add(stringValue);
                                    itemBindings.put(bindingName, seeAlso);
                                } else {
                                    itemBindings.put(bindingName, stringValue);
                                }
                            });

                    items.add(itemBindings);
                });

        ParsedQueryResult parsedResult = null;
        if(!variableTypes.isEmpty() && !items.isEmpty()) {
            parsedResult = new ParsedQueryResult(variableTypes, items);
        }
        return parsedResult;
    }

    private static String addSeeAlso(Map<String, Object> itemBindings, IRI value, String stringValue, ConcurrentMap<String, IRI> variableTypes) {
        List<String> seeAlso = null;
        Object genericSeeAlso = itemBindings.get("seeAlso");

        if(genericSeeAlso == null) {
            seeAlso = new ArrayList<>();
        } else if(genericSeeAlso instanceof List){
            seeAlso = (List)genericSeeAlso;
        } else if(genericSeeAlso instanceof String) {
            seeAlso = new ArrayList<>();
            seeAlso.add((String)genericSeeAlso);
        }

        if(seeAlso == null) {
            seeAlso = new ArrayList<>();
        }

        seeAlso.add(stringValue);
        itemBindings.put("seeAlso", seeAlso);
        variableTypes.putIfAbsent("seeAlso", RDFS.SEEALSO);
        stringValue = value.getLocalName();

        return stringValue;
    }

    private static ParsedQueryResult buildParsedResults(List<Statement> querySolutions, List<Statement> solutionBindings,
                                                 Map<String, Statement> variables, Map<String, Statement> values) {
        ConcurrentMap<String, IRI> variableTypes = new ConcurrentHashMap<>();
        List<Map<String, Object>> resultItems = new ArrayList<>();

        querySolutions.stream()
                .parallel()
                .forEach(solution -> {
                    Map<String, Object> items = new HashMap<>();

                    List<Statement> bindings = getBindingsForSolution(solutionBindings, solution);
                    bindings.forEach(binding -> addItemAndUpdateVariableType(items, binding, variables, values, variableTypes));

                    resultItems.add(items);
                });

        return new ParsedQueryResult(variableTypes, resultItems);
    }

    /**
     *
     * @param items         contains the mapping between select variables and their actual values for an entity
     * @param variableTypes the mapping between each select variable and it's type (name -> string, geo:lat -> float, etc)
     * @param binding
     * @param variables
     * @param values
     */
    private static void addItemAndUpdateVariableType(Map<String, Object> items, Statement binding,
                                              Map<String, Statement> variables, Map<String, Statement> values,
                                              ConcurrentMap<String, IRI> variableTypes) {

        String bindingKey = binding.getObject().stringValue();
        Statement variableStmt = variables.get(bindingKey);
        Statement valueStmt = values.get(bindingKey);

        updateVariableTypes(variableTypes, variableStmt.getObject().stringValue(), valueStmt.getObject());

        String bindingName = variableStmt.getObject().stringValue();
        String stringValue = valueStmt.getObject().stringValue();

        if("id".equalsIgnoreCase(bindingName)) {
            stringValue = addSeeAlso(items, (IRI) valueStmt.getObject(), stringValue, variableTypes);
        }

        if("seeAlso".equalsIgnoreCase(bindingName)) {
            List<String> seeAlso = (List<String>)items.get("seeAlso");
            if(seeAlso == null) {
                seeAlso = new ArrayList<>();
            }

            seeAlso.add(stringValue);
            items.put(bindingName, seeAlso);
        } else {
            items.put(bindingName, stringValue);
        }
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

    private static Map<String, Statement> getFilteredMapBy(List<Statement> queryResults, String filterItem) {
        return queryResults.stream().parallel()
                .filter(statement -> filterItem.equalsIgnoreCase(statement.getPredicate().stringValue()))
                .collect(Collectors.toMap(s -> s.getSubject().stringValue(), Function.identity()));
    }

    private static List<Statement> filterCollectionBy(List<Statement> queryResults, String filterItem) {
        return queryResults.stream().parallel()
                .filter(statement -> filterItem.equalsIgnoreCase(statement.getPredicate().stringValue()))
                .collect(Collectors.toList());
    }


}
