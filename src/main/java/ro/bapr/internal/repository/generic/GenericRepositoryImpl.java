package ro.bapr.internal.repository.generic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.openrdf.model.IRI;
import org.openrdf.model.Model;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.LinkedHashModel;
import org.openrdf.model.vocabulary.FOAF;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.model.vocabulary.RDFS;
import org.openrdf.model.vocabulary.XMLSchema;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryResult;
import org.openrdf.repository.manager.LocalRepositoryManager;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.Rio;

import info.aduna.iteration.Iterations;
import ro.bapr.internal.repository.GraphRepositoryManager;
import ro.bapr.response.Result;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 14.12.2015.
 */
@Service
public class GenericRepositoryImpl implements GenericRepository {

    //TODO extract this in an abstract class
    @Value("${sesame.config.repository.id}")
    private String repositoryId;
    @Value("${sesame.config.base.dir}")
    private String baseDir;
    @Value("${sesame.config.storage.indexes}")
    private String indexes;
    @Value("${sesame.app.namespace}")
    private String appNamespace;

    private LocalRepositoryManager manager;

    @Override
    public void save(Result result) {
        RepositoryConnection conn = null;
        try {

            Repository repo = getManager().getRepository(repositoryId);
            ValueFactory valueFactory = repo.getValueFactory();

            IRI john = valueFactory.createIRI(appNamespace, "john");

            conn = repo.getConnection();
            IRI name = valueFactory.createIRI("http://www.openmobilenetwork" +
                    ".org/ontology/hasSSID"); //context

            conn.add(john, name, valueFactory.createLiteral("linksys", XMLSchema.STRING));


            conn.add(john, RDF.TYPE, FOAF.PERSON);
            conn.add(john, RDFS.LABEL, valueFactory.createLiteral("John", XMLSchema.STRING));

            RepositoryResult<Statement> stmts = conn.getStatements(null, null, null, true);
            Model model = Iterations.addAll(stmts, new LinkedHashModel());

            model.setNamespace("rdf", RDF.NAMESPACE);
            model.setNamespace("rdfs", RDFS.NAMESPACE);
            model.setNamespace("foaf", FOAF.NAMESPACE);
            model.setNamespace("ex", appNamespace);
            Rio.write(model, System.out, RDFFormat.TURTLE);
        } finally {
            if(conn != null) {
                //conn.commit();
                conn.close();
            }
        }

    }


    //TODO extract this in an abstract class
    private LocalRepositoryManager getManager() {
        if(manager == null) {
            manager = GraphRepositoryManager.getInstance(repositoryId, baseDir, indexes).getSesameManager();
        }

        return manager;
    }
}
