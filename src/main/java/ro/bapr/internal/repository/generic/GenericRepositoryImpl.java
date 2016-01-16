package ro.bapr.internal.repository.generic;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.openrdf.model.IRI;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.model.vocabulary.RDFS;
import org.openrdf.model.vocabulary.XMLSchema;
import org.openrdf.query.BindingSet;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.QueryResults;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.manager.LocalRepositoryManager;
import org.springframework.stereotype.Service;

import ro.bapr.internal.repository.GraphRepositoryManager;
import ro.bapr.service.response.Result;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 14.12.2015.
 */
@Service
public class GenericRepositoryImpl implements GenericRepository {

    //TODO extract this in an abstract class
    @org.springframework.beans.factory.annotation.Value("${sesame.config.repository.id}")
    private String repositoryId;
    @org.springframework.beans.factory.annotation.Value("${sesame.config.base.dir}")
    private String baseDir;
    @org.springframework.beans.factory.annotation.Value("${sesame.config.storage.indexes}")
    private String indexes;
    @org.springframework.beans.factory.annotation.Value("${sesame.app.namespace}")
    private String appNamespace;

    private LocalRepositoryManager manager;

    @Override
    public void save(Result result) {
        RepositoryConnection conn = null;
        try {
            Repository repo = getManager().getRepository(repositoryId);
            ValueFactory valueFactory = repo.getValueFactory();
            conn = repo.getConnection();
            Map<String, Map<String, Object>> ctxItems = result.getContext().getItems();
            final RepositoryConnection finalConn = conn;
            result.getItems().forEach(entity -> {
                IRI entityId = valueFactory.createIRI(appNamespace, (String) entity.get("id"));

                entity.forEach((predicate, value) -> {
                    if (!"id".equalsIgnoreCase(predicate)) {
                        IRI predicateIRI = buildPredicate(predicate, valueFactory, result);

                        if (value instanceof Collection) {
                            for (Object o : ((Collection) value)) {
                                addStatement(entityId, predicate, (String) o, predicateIRI,
                                        valueFactory, ctxItems, finalConn);
                            }
                        } else {
                            addStatement(entityId, predicate, (String) value, predicateIRI,
                                    valueFactory, ctxItems, finalConn);
                        }
                    }

                    //add additional properties
                    result.getContext().getAdditionalProperties()
                            .forEach((property, object) -> finalConn.add(entityId, property, object));
                });
            });

        } finally {
            if(conn != null) {
                conn.commit();
                conn.close();
            }
        }

    }

    private void addStatement(IRI john, String predicate, String object, IRI predicateIRI,
                              ValueFactory valueFactory, Map<String, Map<String, Object>> ctxItems,
                              RepositoryConnection finalConn) {

        String type =  ctxItems.get(predicate).get("@type").toString();
        IRI typeIRI;
        if(type == null || type.trim().isEmpty()) {
            typeIRI = XMLSchema.STRING;
        } else {
            typeIRI = valueFactory.createIRI(type);
        }

        if (RDF.LANGSTRING.equals(typeIRI)) {
            finalConn.add(john, predicateIRI, valueFactory.createLiteral(object, "en"));
        } else {
            finalConn.add(john, predicateIRI, valueFactory.createLiteral(object, typeIRI));
        }
    }


    private IRI buildPredicate(String predicate, ValueFactory valueFactory, Result result) {
        IRI predicateIRI;
        //this "if" statement may be useless due to later changes. Please verify
        if ("seeAlso".equalsIgnoreCase(predicate)) {
            predicateIRI = RDFS.SEEALSO;
        } else {
            Map<String, Object> itemIdType = result.getContext().getItems().get(predicate);
            predicateIRI = valueFactory.createIRI(itemIdType.get("@id").toString());
        }
        return predicateIRI;
    }

    @Override
    public List<BindingSet> query(String queryString) {
        Repository repo = getManager().getRepository(repositoryId);
        RepositoryConnection conn = repo.getConnection();

        TupleQueryResult tt = conn.prepareTupleQuery(QueryLanguage.SPARQL, queryString).evaluate();

        return QueryResults.stream(tt).collect(Collectors.toList());

    }

    //TODO extract this in an abstract class
    private LocalRepositoryManager getManager() {
        if(manager == null) {
            manager = GraphRepositoryManager.getInstance(repositoryId, baseDir, indexes).getSesameManager();
        }

        return manager;
    }
}
