package ru.emelianov;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class JSONConverter {

    public static void convert(String filePath, List<AirportSearchResult> searchResults) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            Files.createDirectories(Paths.get(file.getParent()));
            file.createNewFile();
        }

        Gson gson = new Gson();
        String json = gson.toJson(searchResults);

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(json);
        }
    }
}
