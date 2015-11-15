package ro.bapr.repository;

import info.aduna.iteration.Iterations;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openrdf.model.Model;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.LinkedHashModel;
import org.openrdf.model.vocabulary.FOAF;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.model.vocabulary.RDFS;
import org.openrdf.model.vocabulary.XMLSchema;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryResult;
import org.openrdf.repository.config.RepositoryConfig;
import org.openrdf.repository.config.RepositoryImplConfig;
import org.openrdf.repository.manager.LocalRepositoryManager;
import org.openrdf.repository.sail.config.SailRepositoryConfig;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.Rio;
import org.openrdf.sail.config.SailImplConfig;
import org.openrdf.sail.nativerdf.config.NativeStoreConfig;
import org.springframework.beans.factory.annotation.Value;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 15.11.2015.
 */

@org.springframework.stereotype.Repository
public class PersonGraphRepositoryImpl {
    private final Logger log = LogManager.getLogger(PersonGraphRepositoryImpl.class);

    @Value("${sesame.config.repository.id}")
    private String repositoryId;
    @Value("${sesame.config.base.dir}")
    private String baseDir;
    @Value("${sesame.config.storage.indexes}")
    private String indexes;

    private static LocalRepositoryManager manager;

    public void test() {
        log.debug("Test method called");
        RepositoryConnection conn = null;
        try {

            Repository repo = getManager().getRepository(repositoryId);

            ValueFactory f = repo.getValueFactory();
            String namespace = "http://example.org/";

            URI john = f.createIRI(namespace, "john");

            conn = repo.getConnection();

            conn.add(john, RDF.TYPE, FOAF.PERSON);
            conn.add(john, RDFS.LABEL, f.createLiteral("John", XMLSchema.STRING));

            RepositoryResult<Statement> stmts = conn.getStatements(null, null, null, true);
            Model model = Iterations.addAll(stmts, new LinkedHashModel());

            model.setNamespace("rdf", RDF.NAMESPACE);
            model.setNamespace("rdfs", RDFS.NAMESPACE);
            model.setNamespace("foaf", FOAF.NAMESPACE);
            model.setNamespace("ex", namespace);
            Rio.write(model, System.out, RDFFormat.TURTLE);
        } finally {
            if(conn != null) {
                conn.commit();
                conn.close();
            }
        }

    }

    private LocalRepositoryManager getManager() {
        if(manager == null) {
            manager = new LocalRepositoryManager(new File(baseDir));
            // create a configuration for the SAIL stack
            SailImplConfig backendConfig = new NativeStoreConfig(indexes);
            // create a configuration for the repository implementation
            RepositoryImplConfig repositoryTypeSpec = new SailRepositoryConfig(backendConfig);


            RepositoryConfig repConfig = new RepositoryConfig(repositoryId, repositoryTypeSpec);
            manager.initialize();
            manager.addRepositoryConfig(repConfig);
        }

        return manager;
    }

    public String get() {
        log.debug("Get method called");
        String namespace = "http://example.org/";
        Repository repo = getManager().getRepository(repositoryId);
        RepositoryConnection conn = repo.getConnection();
        RepositoryResult<Statement> stmts = conn.getStatements(null, null, null, true);
        Model model = Iterations.addAll(stmts, new LinkedHashModel());

        model.setNamespace("rdf", RDF.NAMESPACE);
        model.setNamespace("rdfs", RDFS.NAMESPACE);
        model.setNamespace("foaf", FOAF.NAMESPACE);
        model.setNamespace("ex", namespace);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Rio.write(model, stream, RDFFormat.TURTLE);

        return new String(stream.toByteArray());
    }
}
