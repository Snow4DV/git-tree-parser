package ru.snow4dv.GitUnits;

import java.util.Arrays;

public abstract class GitObject {
    private String type;
    private int length;
    private String plainText;

    protected GitObject(String type, int length, String plainText) {
        this.type = type;
        this.length = length;
        this.plainText = plainText;
    }

    public String getType() {
        return type;
    }

    public int getLength() {
        return length;
    }

    public String getPlainText() {
        return plainText;
    }

    public static GitObject createObject(String type, String objectPlainText, byte[] objectByteArray) {
        switch(type) {
            case "commit":
                return new GitCommit(objectPlainText);
            case "tree":
                return new GitTree(objectByteArray);
        }
        return null;
    }


    public static GitObject createObject(byte[] array) {
        String objectPlainText = new String(array);
        String type = objectPlainText.substring(0, objectPlainText.indexOf(' '));
        String gitObjectString = objectPlainText.substring(objectPlainText.indexOf('\0') + 1);
        int startIndex = -1;
        for(int i = 0; i < array.length; i++) {
            if(array[i] == '\0') {
                startIndex = i + 1;
                break;
            }
        }
        return GitObject.createObject(type, gitObjectString, Arrays.copyOfRange(array, startIndex, array.length));
    }

}
