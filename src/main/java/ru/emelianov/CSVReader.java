package ru.emelianov;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    public static List<Airport> read(String filePath) throws IOException {
        List<Airport> airports = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length >= 4) {
                    String name = parts[1].trim();
                    String code = parts[4].trim();
                    String city = parts[2].trim();
                    String country = parts[3].trim();

                    Airport airport = new Airport(name, code, city, country);
                    airports.add(airport);
                }
            }
        }

        return airports;
    }
}
