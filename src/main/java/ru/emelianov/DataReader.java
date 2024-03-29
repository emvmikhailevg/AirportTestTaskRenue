package ru.emelianov;

import java.io.IOException;

public interface DataReader {

    TreeNode readLine(String filePath, int indexColumnId) throws IOException;
}
