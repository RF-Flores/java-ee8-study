package pers.ricardo.control;

import javax.ws.rs.Produces;
import java.util.function.Consumer;

public class FatalLoggerExposer {

    @Produces
    public Consumer<Throwable> exposeFatalLogger() {
        // .....
        return Throwable::printStackTrace;

    }
}
