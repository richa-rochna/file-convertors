package com.file.convertor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JsonlToJson {
    public static void main(String[] args) {

        String inputFile = JsonlToJson.class.getClassLoader().getResource("input.jsonl").getPath();
        String outputFile = JsonlToJson.class.getClassLoader().getResource("output.json").getPath();
        List<JsonElement> jsonList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            Gson gson = new GsonBuilder().create();
            JsonParser jsonParser = new JsonParser();

            String line;
            while ((line = br.readLine()) != null) {
                JsonElement jsonElement = jsonParser.parse(line);
                jsonList.add(jsonElement);
            }

            String json = gson.toJson(jsonList);
            try (FileWriter fileWriter = new FileWriter(outputFile)) {
                fileWriter.write(json);
            }

            System.out.println("Conversion completed successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
