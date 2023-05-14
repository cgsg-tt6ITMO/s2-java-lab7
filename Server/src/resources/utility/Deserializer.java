/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package resources.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import resources.task.Route;

/**
 * Returns object from json serialized lines.
 */
public class Deserializer {
    private static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    public static Response readResp(String json) {
        Response res = null;
        try {
            res = mapper.readValue(json, Response.class);
        } catch (Exception ex) {
            System.err.println("Resp input from json:" + ex.getMessage());
            ex.printStackTrace();
        }
        return res;
    }

    public static Response[] responses(String json) {
        Response[] res = null;
        try {
            res = mapper.readValue(json, Response[].class);
        } catch (Exception ex) {
            System.err.println("Arr input from json:" + ex.getMessage());
            ex.printStackTrace();
        }
        return res;
    }

    public static Request readReq(String json) {
        Request res = null;
        try {
            res = mapper.readValue(json, Request.class);
        } catch (JsonProcessingException e) {
            return new Request("exit", "error");
        }
        return res;
    }

    public static Request[] readArr(String json) {
        Request[] res = null;
        try {
            res = mapper.readValue(json, Request[].class);
        } catch (Exception ex) {
            System.err.println("Arr input from json:" + ex.getMessage());
            ex.printStackTrace();
        }
        return res;
    }

    public static Route readRoute(String json) {
        Route res = null;
        try {
            res = mapper.readValue(json, Route.class);

        } catch (Exception ex) {
            System.err.println("Route input from json:" + ex.getMessage());
            ex.printStackTrace();
        }
        return res;
    }

    public static Long readLong(String ser) {
        return Long.parseLong(ser);
    }

    public static Double readDouble(String d) {
        return Double.parseDouble(d);
    }
}
