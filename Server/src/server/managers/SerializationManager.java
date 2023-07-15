/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package server.managers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import resources.task.Route;
import resources.utility.Request;

import java.io.IOException;

/**
 * Returns object from json serialized lines.
 */
public class SerializationManager {
    private static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

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

    public static Request readReq(String json) {
        try {
            return mapper.readValue(json, Request.class);
        } catch (JsonProcessingException e) {
            return new Request("error", "error", "error");
        }
    }

    public static Route readRoute(String json) {
        try {
            return mapper.readValue(json, Route.class);
        } catch (Exception ex) {
            System.err.println("Route input from json:" + ex.getMessage());
            return null;
        }
    }

    public static Long readLong(String ser) {
        return Long.parseLong(ser);
    }

    public static Double readDouble(String d) {
        return Double.parseDouble(d);
    }
}
