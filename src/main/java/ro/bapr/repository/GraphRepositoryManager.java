package ro.bapr.repository;

import org.openrdf.repository.config.RepositoryConfig;
import org.openrdf.repository.config.RepositoryImplConfig;
import org.openrdf.repository.manager.LocalRepositoryManager;
import org.openrdf.repository.sail.config.SailRepositoryConfig;
import org.openrdf.sail.config.SailImplConfig;
import org.openrdf.sail.nativerdf.config.NativeStoreConfig;

import java.io.File;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 16.11.2015.
 */
public class GraphRepositoryManager {

    private final String repositoryId;
    private final String baseDir;
    private final String indexes;

    private LocalRepositoryManager manager;
    private static GraphRepositoryManager instance;

    private GraphRepositoryManager(String repositoryId, String baseDir, String indexes) {
        this.repositoryId = repositoryId;
        this.baseDir = baseDir;
        this.indexes = indexes;

    }

    public static GraphRepositoryManager getInstance(String repositoryId, String baseDir, String indexes) {
        if(instance == null) {
            instance = new GraphRepositoryManager(repositoryId, baseDir, indexes);
        }
        return instance;
    }

    public LocalRepositoryManager getSesameManager() {
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
