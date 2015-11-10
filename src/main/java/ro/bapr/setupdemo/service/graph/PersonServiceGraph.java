package ro.bapr.setupdemo.service.graph;
import ro.bapr.setupdemo.model.graph.PersonGraph;

import java.util.List;

/**
 * Created by valentin.spac on 11/10/2015.
 */
public interface PersonServiceGraph {
    public PersonGraph create(PersonGraph shop);
    public PersonGraph delete(int id);
    public List<PersonGraph> findAll();
    public PersonGraph update(PersonGraph shop);
    public PersonGraph findById(int id);
}
