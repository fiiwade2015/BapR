package ro.bapr.setupdemo.repos.graph;

import org.springframework.data.neo4j.repository.GraphRepository;
import ro.bapr.setupdemo.model.graph.PersonGraph;

/**
 * Created by valentin.spac on 11/10/2015.
 */
public interface PersonRepoNeo4j extends GraphRepository<PersonGraph> {
}
