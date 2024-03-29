package ru.emelianov;

import java.io.BufferedReader;
import java.io.FileReader;
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
                System.err.println("Неправильный формат ввода");
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
                        System.err.println("Файл с данными не найден или недоступен для чтения");
                    }
                    break;
                case "--indexed-column-id":
                    try {
                        indexedColumnId = Integer.parseInt(value);
                    } catch (NumberFormatException e) {
                        System.err.println("Неправильный формат номера колонки. Пожалуйста, введите целое число");
                    }
                    break;
                case "--input-file":
                    File inputFile = new File(value);
                    if (inputFile.exists() && inputFile.isFile() && inputFile.canRead()) {
                        inputFilePath = value;
                    } else {
                        System.err.println("Файл с входными строками не найден или недоступен для чтения");
                    }
                    break;
                case "--output-file":
                    outputFilePath = value;
                    break;
                default:
                    System.err.println("Неизвестный аргумент командной строки");
                    break;
            }
        }

        long startCountInitTime = System.currentTimeMillis();

        scanner.close();

        TreeNode treeNode = new CSVReader().readLine(dataFilePath, indexedColumnId);

        long stopCountInitTime = System.currentTimeMillis();
        long initTime = stopCountInitTime - startCountInitTime;

        List<SearchResult> result = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                long startSearchTime = System.currentTimeMillis();
                List<Integer> additionalIndexes;
                String input = line.trim();
                TreeNode fullWordResultNode = performSearch(treeNode, input);

                if (fullWordResultNode != null) {
                    additionalIndexes = performDFS(fullWordResultNode);
                    long stopSearchTime = System.currentTimeMillis();
                    long time = stopSearchTime - startSearchTime;
                    result.add(new SearchResult(input, additionalIndexes, time));
                }
            }
        }

        ConversionData conversionData = new ConversionData(initTime, result);
        Converter converter = new JSONConverter();
        converter.convert(outputFilePath, conversionData);
    }

    private static TreeNode performSearch(TreeNode tree, String input) {
        TreeNode currentNode = tree;
        for (int i = 0; i < input.length(); i++) {
            String symbol = String.valueOf(input.charAt(i));
            if (currentNode != null) {
                currentNode = currentNode.getChildes().getOrDefault(symbol, null);
            } else {
                return null;
            }

            if (i == input.length() - 1 && currentNode != null) {
                return currentNode;
            }
        }

        return null;
    }

    private static List<Integer> performDFS(TreeNode treeNode) {
        List<Integer> indexes = new ArrayList<>();
        if (!treeNode.getChildes().isEmpty()) {
            for (TreeNode node : treeNode.getChildes().values()) {
                indexes.addAll(performDFS(node));
            }
        } else {
            return treeNode.getIndexes();
        }

        return indexes;
    }
}
