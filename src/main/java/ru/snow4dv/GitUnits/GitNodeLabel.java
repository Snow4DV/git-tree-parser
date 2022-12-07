package ru.snow4dv.GitUnits;

import java.io.IOException;

public class GitNodeLabel {
    enum NodeType {
        BLOB, TREE, COMMIT_TREE
    }

    private final NodeType nodeType;
    private final String hash;

    private final String nodeName;

    public GitNodeLabel(NodeType nodeType, String hash, String nodeName) {
        this.nodeType = nodeType;
        this.hash = hash;
        this.nodeName = nodeName;
    }


    public GitNodeLabel(String hash) {
        this.nodeType = NodeType.COMMIT_TREE;
        this.hash = hash;
        this.nodeName = "root";
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
        return nodeType != NodeType.BLOB;
    }

    public String getNodeName() {
        return nodeName;
    }

    public GitObject loadGitObject(GitRepo repo) throws IOException {
        return repo.getGitObject(hash);
    }

}
