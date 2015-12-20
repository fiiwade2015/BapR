package ro.bapr.internal.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 21.11.2015.
 */
@ContextConfiguration(locations = {"/config/context-config.xml"})
public class Main {
    @Autowired
    public TestFactory factory;

    public static void main(String[] args) {
        Main m = new Main();
        Model22 s = m.factory.createTask();
        System.out.println(s);
    }
}
