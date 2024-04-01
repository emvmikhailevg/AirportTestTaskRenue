package ru.emelianov;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class JSONConverter implements Converter {

    @Override
    public void convert(String outputFilePath, ConversionData conversionData) {
        Map<String, Object> result = new LinkedHashMap<>();
        List<Map<String, Object>> sortedResults = new ArrayList<>();

        conversionData.getResult().sort(Comparator.comparing(SearchResult::getSearch));
        List<SearchResult> resultValues = new ArrayList<>(conversionData.getResult());

        for (SearchResult searchResult : resultValues) {
            Collections.sort(searchResult.getResult());
        }

        for (SearchResult searchResult : resultValues) {
            Map<String, Object> resultMap = new LinkedHashMap<>();
            resultMap.put("search", searchResult.getSearch());
            resultMap.put("result", searchResult.getResult());
            resultMap.put("time", searchResult.getTime());
            sortedResults.add(resultMap);
        }

        result.put("initTime", conversionData.getInitTime());
        result.put("result", sortedResults);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try {
            File file = new File(outputFilePath);
            boolean isNewFile = file.createNewFile();
            FileWriter writer = new FileWriter(file);
            gson.toJson(result, writer);
            writer.close();

            if (isNewFile) {
                System.out.println("Файл успешно создан: " + file.getName());
            } else {
                System.out.println("Файл успешно перезаписан: " + file.getName());
            }
        } catch (IOException e) {
            System.err.println("Ошибка записи: " + e);
        }
    }
}
