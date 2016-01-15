package ro.bapr.external.openmobilenetwork.repository.api;

import java.util.List;

import org.openrdf.model.Statement;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 17.11.2015.
 */
public interface OpenMobileNetworkRepository {
    List<Statement> query(String queryString);
}
