package ro.bapr.internal.repository.api;

import ro.bapr.internal.model.RegisterModel;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 17.01.2016.
 */
public interface UserRepository {

    String registerUser(RegisterModel model);
}
