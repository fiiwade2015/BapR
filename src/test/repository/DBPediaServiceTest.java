package repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openrdf.query.TupleQueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ro.bapr.external.dbpedia.service.api.DBPediaService;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 17.11.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/config/context-config.xml"})
public class DBPediaServiceTest {

    @Autowired
    private DBPediaService service;

    @Test
    public void testService() {
        TupleQueryResult result = service.query("select distinct ?Concept where {[] a ?Concept} LIMIT 100");
        Assert.assertNotNull("Returned model should not be null", result);
    }
}
