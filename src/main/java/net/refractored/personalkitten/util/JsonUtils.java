package net.refractored.personalkitten.util;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JsonUtils {
    private static Gson gson = new Gson();

    public static String mapToJson(Map<Object, Object> map) {
        return gson.toJson(map);
    }

    public static HashMap mapFromJson(String json) {
        if (json == null) {
            return new HashMap();
        }
        return gson.fromJson(json, HashMap.class);
    }

    public static String listToJson(ArrayList<Object> list) {
        return gson.toJson(list);
    }

    public static ArrayList listFromJson(String json) {
        return gson.fromJson(json, ArrayList.class);
    }

    public static String objectToJson(Object object) {
        return gson.toJson(object);
    }

    public static Object objectFromJson(String json, Class<?> clazz) {
        return gson.fromJson(json, clazz);
    }
}
