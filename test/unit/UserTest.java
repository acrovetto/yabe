package unit;
import models.User;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

public class UserTest extends BasicTest {

    @Test
    public void createAndRetrieveUser() {
        // Create a new user and save it
        new User("bob@gmail.com", "secret", "Bob").save();

        // Retrieve the user with e-mail address bob@gmail.com
        User bob = User.find("byEmail", "bob@gmail.com").first();

        // Test
        assertNotNull(bob);
        assertEquals("Bob", bob.fullname);
    }

    @Test
    public void connectTest() {
        String email = "toto@titi.fr";
        String password = "123456";
        String name = "Toto";

        User user = User.connect(email, password);
        assertNull(user);

        // Create a new user and save it
        new User(email, password, name).save();

        user = User.connect(email, password);
        assertNotNull(user);

        user.delete();
    }

}
