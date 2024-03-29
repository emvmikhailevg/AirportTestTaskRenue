package ru.emelianov;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeNode {

    private final String key;
    private final Map<String, TreeNode> childes = new HashMap<>();
    private final List<Integer> indexes = new ArrayList<>();

    public TreeNode() {
        this.key = null;
    }

    public TreeNode(String key) {
        this.key = key;
    }

    public Map<String, TreeNode> getChildes() {
        return childes;
    }

    public void addIndex(int index) {
        indexes.add(index);
    }

    public List<Integer> getIndexes() {
        return indexes;
    }

    public TreeNode putChild(String key) {
        if (childes.containsKey(key)) {
            return childes.get(key);
        } else {
            TreeNode child = new TreeNode(key);
            this.childes.put(key, child);
            return child;
        }
    }
}
