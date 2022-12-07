package ru.snow4dv.GitUnits;

public class GitNode extends GitObject{
    protected GitNode(String type, String hash, byte[] content) {
        super(type, hash, content);
    }
}
