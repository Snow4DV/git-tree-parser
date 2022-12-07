package ru.snow4dv.GitUnits;

import java.util.Arrays;

public abstract class GitObject {
    private String type;
    private String hash;
    private byte[] content;

    protected GitObject(String type, String hash, byte[] content) {
        this.type = type;
        this.hash = hash;
        this.content = content;
    }

    public String getHash() {
        return hash;
    }

    public String getType() {
        return type;
    }

    public byte[] getContent() {
        return content;
    }

    public static GitObject createObject(String type, String hash, byte[] objectByteArray) {
        switch(type) {
            case "commit":
                return new GitCommit(hash, objectByteArray);
            case "tree":
                return new GitTree(hash, objectByteArray);
            case "blob":
                return new GitBlob(hash, objectByteArray);
        }
        return null;
    }

    public static GitObject createObject(String hash, byte[] content) {
        return GitObject.createObject(hash, content, true);
    }

    public static GitObject createObject(String hash, byte[] content,  boolean skipFile) {
        String objectPlainText = new String(content);
        String type = objectPlainText.substring(0, objectPlainText.indexOf(' '));
        //String gitObjectString = objectPlainText.substring(objectPlainText.indexOf('\0') + 1);
        int startIndex = -1;
        for(int i = 0; i < content.length; i++) {
            if(content[i] == '\0') {
                startIndex = i + 1;
                break;
            }
        }

        if(type.equals("blob") && skipFile) return null;

        return GitObject.createObject(type, hash, Arrays.copyOfRange(content, startIndex, content.length));
    }

}
