package com.file.convertor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class CsvToJson {

    public static void main(String[] args) {

        String csvFilePath = CsvToJson.class.getClassLoader().getResource("file.csv").getPath();
        String jsonFilePath = CsvToJson.class.getClassLoader().getResource("file.json").getPath();

        List<String> headers = new ArrayList<>();
        List<Map<String, String>> rows = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            int lineCount = 0;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (lineCount == 0) {
                    for (String value : values) {
                        headers.add(value.trim());
                    }
                } else {
                    Map<String, String> row = new LinkedHashMap<>();
                    for (int i = 0; i < headers.size(); i++) {
                        row.put(headers.get(i), values[i].trim());
                    }
                    rows.add(row);
                }
                lineCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONArray jsonArray = new JSONArray();
        for (Map<String, String> row : rows) {
            JSONObject jsonObject = new JSONObject();
            for (Map.Entry<String, String> entry : row.entrySet()) {
                jsonObject.put(entry.getKey(), entry.getValue());
            }
            jsonArray.add(jsonObject);
        }

        try (FileWriter file = new FileWriter(jsonFilePath)) {
            file.write(jsonArray.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

