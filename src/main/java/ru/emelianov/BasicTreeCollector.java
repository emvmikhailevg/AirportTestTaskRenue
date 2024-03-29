package ru.emelianov;

public class BasicTreeCollector implements TreeCollector {

    @Override
    public void collect(TreeNode initialNode, String value, int index) {
        TreeNode childNode = null;
        for (char c : value.toCharArray()) {
            if (childNode == null) {
                childNode = initialNode.putChild(String.valueOf(c));
            } else {
                childNode = childNode.putChild(String.valueOf(c));
            }
        }
        if (childNode != null) {
            childNode.addIndex(index);
        }
    }
}
