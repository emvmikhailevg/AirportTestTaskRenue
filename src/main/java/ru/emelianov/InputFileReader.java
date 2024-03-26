package ru.emelianov;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InputFileReader {

    public static List<String> read(String filePath) throws IOException {
        List<String> inputStrings = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                inputStrings.add(line.trim());
            }
        }

        return inputStrings;
    }
}
