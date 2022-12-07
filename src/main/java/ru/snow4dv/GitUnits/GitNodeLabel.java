package ru.snow4dv.GitUnits;

public class GitNodeLabel {
    enum NodeType {
        BLOB, TREE
    }

    private final NodeType nodeType;
    private final String hash;

    private final String nodeName;

    public GitNodeLabel(NodeType nodeType, String hash, String nodeName) {
        this.nodeType = nodeType;
        this.hash = hash;
        this.nodeName = nodeName;
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    public String getHash() {
        return hash;
    }

    public boolean isBlob() {
        return nodeType == NodeType.BLOB;
    }

    public boolean isTree() {
        return nodeType == NodeType.TREE;
    }

    public String getNodeName() {
        return nodeName;
    }
}
