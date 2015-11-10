package ro.bapr.setupdemo.model.graph;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

/**
 * Created by valentin.spac on 11/10/2015.
 */
@NodeEntity
@Configurable
public class PersonGraph {

    @GraphId
    private int id;
    private String name;
    private int age;

    public PersonGraph() { }

    public PersonGraph(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
