package ro.bapr.internal.test;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 21.11.2015.
 */
@Component
public class TestFactory {

    @Lookup
    public Model22 createTask() {
        return null;
    }
}
