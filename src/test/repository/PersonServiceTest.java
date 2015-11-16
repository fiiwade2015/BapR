package repository;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ro.bapr.model.Person;
import ro.bapr.service.api.PersonNotFound;
import ro.bapr.service.api.PersonSqlService;

import static org.hamcrest.core.IsInstanceOf.instanceOf;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 16.11.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/config/context-config.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PersonServiceTest {

    @Qualifier("personSqlServiceImpl")
    @Autowired
    private PersonSqlService service;

    @Test
    public void stage1_testCreateNewPerson() {
        Person p = new Person("test", 10);
        Person created = service.create(p);
        Assert.assertThat(created, instanceOf(Person.class));
        Assert.assertEquals(created.getAge(), p.getAge());
        Assert.assertEquals(created.getName(), p.getName());
    }

    @Test
    public void stage2_testRetrievePerson() {
        Person p = service.findByName("test");
        Assert.assertNotNull(p);
        Assert.assertEquals(p.getAge(), 10);
    }

    @Test
    public void stage3_testDeletePerson() {
        Person p = service.findByName("test");
        try {
            Person deleted = service.delete(p.getId());
            Assert.assertEquals(p.getId(), deleted.getId());
        } catch (PersonNotFound personNotFound) {
            personNotFound.printStackTrace();
        }

    }

}
