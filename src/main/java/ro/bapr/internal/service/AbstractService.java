package ro.bapr.internal.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.openrdf.query.BindingSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import ro.bapr.internal.model.Context;
import ro.bapr.internal.model.ParsedQueryResult;
import ro.bapr.internal.model.Result;
import ro.bapr.internal.service.api.GenericService;
import ro.bapr.internal.utils.ContextCreator;
import ro.bapr.internal.utils.parser.QueryResultsParser;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 24.01.2016.
 */
public abstract class AbstractService {

    @Autowired
    private ContextCreator contextCreator;

    @Autowired
    private GenericService genericService;

    @Value("${sesame.app.namespace}")
    protected String appNamespace;

    protected Result query(String queryString,
                         Supplier<List<BindingSet>> responseSupplier) {

        List<BindingSet> queryResults = genericService.query(queryString);
        boolean saveData = false;
        if (queryResults == null || queryResults.isEmpty()) {
            saveData = true;
            queryResults = responseSupplier.get();
        }

        Optional<ParsedQueryResult> parsedResultsOptional = QueryResultsParser.parseBindingSets(queryResults);

        Result result = new Result();
        if (parsedResultsOptional.isPresent()){
            ParsedQueryResult parsedResults = parsedResultsOptional.get();
            Context ctx = contextCreator.create(queryString, parsedResults.getVariableTypes());

            result.setItems(parsedResults.getResultItems());
            result.setContext(ctx);

            if (saveData) {
                genericService.save(result);
            }
        }

        return result;
    }

    protected String transformDbId(String id, String dbBaseUrl) {
        return dbBaseUrl + id;
    }

}
