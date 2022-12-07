package ru.snow4dv.GitUnits;

public class GitBlob extends GitNode {


    public GitBlob(String hash, byte[] content) {
        super("blob", hash, content);
    }

    private String getContentAsPlainText() {
        return new String(getContent());
    }

}
