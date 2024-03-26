package ru.emelianov;

import java.io.IOException;
import java.util.*;

import java.io.File;

public class AirportSearch {

    private static String dataFilePath = null;
    private static int indexedColumnId = -1;
    private static String inputFilePath = null;
    private static String outputFilePath = null;

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        while (dataFilePath == null
                || indexedColumnId == -1
                || inputFilePath == null
                || outputFilePath == null) {

            String userInput = scanner.nextLine().trim();
            String[] parts = userInput.split(" ", 2);

            if (parts.length != 2) {
                System.err.println("Неправильный формат ввода.");
                continue;
            }

            String argument = parts[0];
            String value = parts[1];

            switch (argument) {
                case "--data":
                    File dataFile = new File(value);
                    if (dataFile.exists() && dataFile.isFile() && dataFile.canRead()) {
                        dataFilePath = value;
                    } else {
                        System.err.println("Файл с данными не найден или недоступен для чтения.");
                    }
                    break;
                case "--indexed-column-id":
                    try {
                        indexedColumnId = Integer.parseInt(value);
                    } catch (NumberFormatException e) {
                        System.err.println("Неправильный формат номера колонки. Пожалуйста, введите целое число.");
                    }
                    break;
                case "--input-file":
                    File inputFile = new File(value);
                    if (inputFile.exists() && inputFile.isFile() && inputFile.canRead()) {
                        inputFilePath = value;
                    } else {
                        System.err.println("Файл с входными строками не найден или недоступен для чтения.");
                    }
                    break;
                case "--output-file":
                    outputFilePath = value;
                    break;
                default:
                    System.err.println("Неизвестный аргумент командной строки.");
                    break;
            }
        }

        scanner.close();

        List<Airport> airports = CSVReader.read(dataFilePath);

        List<String> inputStrings = InputFileReader.read(inputFilePath);

        List<AirportSearchResult> searchResults = new ArrayList<>();

        for (String inputString : Objects.requireNonNull(inputStrings)) {
            List<Integer> matchingIndexes = performSearch(airports, indexedColumnId, inputString);
            long searchTime = calculateSearchTime(airports, indexedColumnId, inputString);
            AirportSearchResult result = new AirportSearchResult(inputString, matchingIndexes, searchTime);
            searchResults.add(result);
        }

        JSONConverter.convert(outputFilePath, searchResults);
    }

    private static List<Integer> performSearch(List<Airport> airports, int indexedColumnId, String inputString) {
        Map<String, List<Integer>> indexedMap = new HashMap<>();

        for (int i = 0; i < airports.size(); i++) {
            Airport airport = airports.get(i);
            String property;
            switch (indexedColumnId) {
                case 1:
                    property = airport.getName();
                    break;
                case 2:
                    property = airport.getCode();
                    break;
                case 3:
                    property = airport.getCity();
                    break;
                case 4:
                    property = airport.getCountry();
                    break;
                default:
                    throw new IllegalArgumentException("Некорректный индекс колонки");
            }

            indexedMap.computeIfAbsent(property, k -> new ArrayList<>()).add(i + 1);
        }

        return indexedMap.getOrDefault(inputString, new ArrayList<>());
    }

    private static long calculateSearchTime(List<Airport> airports, int indexedColumnId, String inputString) {
        long startTime = System.nanoTime();

        performSearch(airports, indexedColumnId, inputString);

        long endTime = System.nanoTime();

        return endTime - startTime;
    }
}
