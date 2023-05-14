/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package resources.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import resources.task.Route;

import java.io.IOException;

/**
 * Makes strings out of objects in order to send data through Streams of bytes.
 */
public class Serializer {
    private static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    /**
     * Default constructor.
     */
    public Serializer() {}

    /**
     * Returns string out of any object.
     * @param r object to be serialized.
     * @return string as a result of serialization.
     */
    public static String objSer(Object r) {
        String res = "";
        try {
            res = mapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(r);

        } catch (IOException e) {
            System.err.println("error while " + r.getClass() + " serialization:" + e.getMessage());
        }
        return res;
    }

    public static String longSer(Long l) {
        return String.valueOf(l);
    }

    public static String longRouteSer(long n, Route r) {
        return longSer(n) + "\n" + objSer(r);
    }

    public static String doubleSer(Double d) {
        return String.valueOf(d);
    }
}
