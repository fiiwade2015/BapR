package ro.bapr.internal.repository.api;

import org.openrdf.repository.Repository;
import org.openrdf.repository.manager.LocalRepositoryManager;
import org.springframework.beans.factory.annotation.Value;

import ro.bapr.internal.repository.GraphRepositoryManager;

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
            manager = GraphRepositoryManager.getInstance(repositoryId, baseDir, indexes).getSesameManager();
        }

        return manager.getRepository(repositoryId);
    }


}
