package ru.snow4dv.GitUnits;

import java.io.FileInputStream;
import java.util.Arrays;

public abstract class GitObject {

    static int lastIndex = 0;
    private String type;
    private String hash;
    private byte[] content;
    protected int index;

    protected GitObject(String type, String hash, byte[] content) {
        this.type = type;
        this.hash = hash;
        this.content = content;
        this.index = lastIndex++;
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
        return GitObject.createObject(hash, content, false);
    }

    public static GitObject createObject(String hash, byte[] content,  boolean loadFiles) {
        String objectType = "";

        //String gitObjectString = objectPlainText.substring(objectPlainText.indexOf('\0') + 1);
        int startIndex = -1;
        for(int i = 0; i < content.length; i++) {
            if(content[i] == '\0') {
                startIndex = i + 1;
                break;
            } else if(content[i] == ' '){
                //objectDescriptionSB.append((char) content[i]);
                objectType = new String(Arrays.copyOfRange(content, 0, i));
            }
        }

        if(objectType.equals("blob") && !loadFiles) return null;

        return GitObject.createObject(objectType, hash, Arrays.copyOfRange(content, startIndex, content.length));
    }

    public int getIndex() {
        return index;
    }
}
