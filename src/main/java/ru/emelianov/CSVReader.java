package ru.emelianov;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader implements DataReader {

    private static final TreeCollector collector = new BasicTreeCollector();

    public TreeNode readLine(String filePath, int indexColumnId) throws IOException {
        TreeNode tree = new TreeNode();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int valueIndex = Integer.parseInt(parts[0].trim().replace("\"", ""));
                String value = parts[indexColumnId].trim().replace("\"", "");
                collector.collect(tree, value, valueIndex);
            }
        }

        return tree;
    }
}
