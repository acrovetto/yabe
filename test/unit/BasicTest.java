package unit;
import org.junit.*;

import java.util.*;
import play.test.*;
import models.*;

public abstract class BasicTest extends UnitTest {

    @Before
    public void setup() {
        Fixtures.deleteAll();
    }
}
