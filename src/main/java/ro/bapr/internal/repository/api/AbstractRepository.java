package ro.bapr.internal.repository.api;

import java.io.File;

import org.openrdf.repository.Repository;
import org.openrdf.repository.config.RepositoryConfig;
import org.openrdf.repository.config.RepositoryImplConfig;
import org.openrdf.repository.manager.LocalRepositoryManager;
import org.openrdf.repository.sail.config.SailRepositoryConfig;
import org.openrdf.sail.config.SailImplConfig;
import org.openrdf.sail.nativerdf.config.NativeStoreConfig;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 17.01.2016.
 */
public abstract class AbstractRepository {
    @Value("${sesame.config.repository.id}")
    protected String repositoryId;
    @Value("${sesame.config.base.dir}")
    protected String baseDir;
    @Value("${sesame.config.storage.indexes}")
    protected String indexes;
    @Value("${sesame.app.namespace}")
    protected String appNamespace;

    private LocalRepositoryManager manager;

    protected Repository getRepository() {
        if(manager == null) {
            manager = getSesameManager();
        }
        Repository repo = manager.getRepository(repositoryId);
        repo.initialize();
        return repo;
    }

    private LocalRepositoryManager getSesameManager() {
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


}
