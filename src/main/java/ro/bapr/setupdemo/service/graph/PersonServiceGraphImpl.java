package ro.bapr.setupdemo.service.graph;

import org.springframework.data.neo4j.conversion.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.bapr.setupdemo.model.graph.PersonGraph;
import ro.bapr.setupdemo.repos.graph.PersonRepoNeo4j;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by valentin.spac on 11/10/2015.
 */
@Service
public class PersonServiceGraphImpl implements PersonServiceGraph {

    @Resource
    private PersonRepoNeo4j PersonGraphRepository;

    @Override
    @Transactional
    public PersonGraph create(PersonGraph PersonGraph) {
        return PersonGraphRepository.save(PersonGraph);
    }

    @Override
    @Transactional(rollbackFor=PersonGraphNotFound.class)
    public PersonGraph delete(int id) {
        PersonGraph deletedPersonGraph = PersonGraphRepository.findOne((long) id);


        PersonGraphRepository.delete(deletedPersonGraph);
        return deletedPersonGraph;
    }

    @Override
    @Transactional
    public List<PersonGraph> findAll() {
        List<PersonGraph> list = new ArrayList<>();
        Result<PersonGraph> result = PersonGraphRepository.findAll();
        result.spliterator().forEachRemaining(list::add);

        return list;
    }

    @Override
    @Transactional(rollbackFor=PersonGraphNotFound.class)
    public PersonGraph update(PersonGraph personGraph) {
        PersonGraph updatedPersonGraph = PersonGraphRepository.findOne((long) personGraph.getId());

        updatedPersonGraph.setName(personGraph.getName());
        updatedPersonGraph.setAge(personGraph.getAge());
        return updatedPersonGraph;
    }

    @Override
    public PersonGraph findById(int id) {
        return PersonGraphRepository.findOne((long)id);
    }

    public class PersonGraphNotFound extends Throwable {
    }
}
