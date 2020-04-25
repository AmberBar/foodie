package com.amber.foodie.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class JsonUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();


    public static String objectToJson(Object data) {
        try {
            return OBJECT_MAPPER.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
        try {
            return OBJECT_MAPPER.readValue(jsonData, new TypeReference<List<T>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public static <T> T jsonToObject(String jsonData, Class<T> beanType) {
        try {
            return OBJECT_MAPPER.readValue(jsonData, beanType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
