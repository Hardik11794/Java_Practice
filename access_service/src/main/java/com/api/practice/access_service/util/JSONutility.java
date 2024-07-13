package com.api.practice.access_service.util;

import com.api.practice.access_service.Config.JacksonConfiguration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Function;


public class JSONutility<T> {

    private String url;
    private Class<T> clazz;

    private JacksonConfiguration objectMapperConfig;
    private ObjectMapper objectMapper;

    public JSONutility(String scanfilePath,Class<T> clazz) {
        this.url = scanfilePath;
        this.clazz = clazz;
        this.objectMapperConfig = new JacksonConfiguration();
        this.objectMapper = objectMapperConfig.objectMapper();
    }

    public T getData(){

        Function<String, String> removeBrackets = data -> data.startsWith("[") && data.endsWith("]") ? data.substring(1, data.length() - 1) : data;

        try {
            String dataFile = new String(Files.readAllBytes(Paths.get(url)));
            String data = removeBrackets.apply(dataFile);

            if(isValidJson(data)){
                return (T) mapData(data, clazz);
            }else{
                return (T) "Invalid Json";
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);

        }

    }

    public T mapData(String data, Class<T> clazz) {


        try {
            return objectMapper.readValue(data, clazz);
        } catch (JsonProcessingException e) {
            // Log the exception or provide more context
            throw new RuntimeException("Error while deserializing JSON data", e);
        }
    }


    public static boolean isValidJson(String jsonString) {
        try {
            new ObjectMapper().readTree(jsonString);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
