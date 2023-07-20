/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package client.managers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import resources.task.Route;
import resources.utility.Response;

import java.io.IOException;

import static resources.utility.Status.ERROR;

/**
 * Makes strings out of objects in order to send data through Streams of bytes.
 */
public class SerializationManager {
    private static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    /**
     * Default constructor.
     */
    public SerializationManager() {}

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
            e.printStackTrace();
        }
        return res;
    }

    public static String longRouteSer(long n, Route r) {
        return "" + n + "\n" + objSer(r);
    }

    public static Response readResp(String json) {
        try {
            return mapper.readValue(json, Response.class);
        } catch (Exception ex) {
            return new Response(ERROR, "Error while response deserialization");
        }
    }
}
