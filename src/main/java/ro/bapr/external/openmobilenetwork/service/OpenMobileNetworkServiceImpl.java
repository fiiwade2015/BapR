package ro.bapr.external.openmobilenetwork.service;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.bapr.external.openmobilenetwork.repository.api.OpenMobileNetworkRepository;
import ro.bapr.external.openmobilenetwork.service.api.OpenMobileNetworkService;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 17.11.2015.
 */
@Service
public class OpenMobileNetworkServiceImpl implements OpenMobileNetworkService {

    @Autowired
    private OpenMobileNetworkRepository repo;

    @Override
    public Collection<Map<String, Object>> query(String queryString) {
        return repo.query(queryString);
    }
}
