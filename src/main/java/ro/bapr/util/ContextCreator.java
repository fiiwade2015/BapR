package ro.bapr.util;

import org.openrdf.query.algebra.TupleExpr;
import org.openrdf.query.algebra.helpers.StatementPatternCollector;
import org.openrdf.query.parser.ParsedQuery;
import org.openrdf.query.parser.QueryParser;
import org.openrdf.query.parser.sparql.SPARQLParserFactory;
import org.springframework.stereotype.Component;
import ro.bapr.response.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 05.12.2015.
 */
@Component
public class ContextCreator {

    /**
     * For every statement tuple (subject, predicate, object) search if the object is present in the SELECT statement
     * (is a binding parameter). If so, it adds the (object : namespace) mapping to the context.
     * E.g: For
     *   PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>
     *   PREFIX dbo: <http://dbpedia.org/ontology/>
     *   PREFIX foaf: <http://xmlns.com/foaf/0.1/>
     *   SELECT distinct ?s, ?type, ?latitude, ?longitude, ?name WHERE {
     *       ?s a dbo:Place .
     *       ?s geo:lat ?latitude .
     *       ?s geo:long ?longitude .
     *       ?s dbo:type ?type .
     *       ?s foaf:name ?name
     *   FILTER ( ?long > 13 && ?long < 15 && ?lat > 52 && ?lat < 55)
     *   }
     * the returned mapping is : [
     *              {latitude : http://www.w3.org/2003/01/geo/wgs84_pos#lat},
     *              {longitude : http://www.w3.org/2003/01/geo/wgs84_pos#long},
     *              {type : http://dbpedia.org/ontology/type},
     *              {name : http://xmlns.com/foaf/0.1/name}
     *           ]
     * @param queryString query to be parsed
     * @return a {@code Context} object containing the mapping (parameterName : namespace)
     */
    public Context create(String queryString) {
        SPARQLParserFactory factory = new SPARQLParserFactory();
        QueryParser parser = factory.getParser();
        ParsedQuery parsedQuery = parser.parseQuery(queryString, null);

        StatementPatternCollector collector = new StatementPatternCollector();
        TupleExpr tupleExpr = parsedQuery.getTupleExpr();
        tupleExpr.visit(collector);

        Map<String, String> contextItems = new HashMap<>();
        collector.getStatementPatterns()
                .stream()
                .filter(pattern -> tupleExpr.getBindingNames().contains(pattern.getObjectVar().getName()))
                .forEach(pattern -> contextItems.put(pattern.getObjectVar().getName(), pattern.getPredicateVar().getValue().stringValue()));

        Context ctx = new Context();
        ctx.setItems(contextItems);
        return ctx;
    }
}
