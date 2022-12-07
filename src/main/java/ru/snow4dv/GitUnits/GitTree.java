package ru.snow4dv.GitUnits;

import java.util.ArrayList;
import java.util.List;

public class GitTree extends GitNode{

    enum ReadObjectType {
        PERMISSION, FILE_NAME, HASH
    }

    private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();

    private final List<GitNodeLabel> subNodes = new ArrayList<>();


    /**
     * Creates git tree object
     * @param treeFile read tree file without length and type information
     */
    public GitTree(byte[] treeFile) {
        super("tree", treeFile.length, new String(treeFile));

        StringBuilder permissionSB = new StringBuilder(), fileNameSB = new StringBuilder();

        byte[] hashBytes = new byte[20];
        int currentByteIndex = 0;

        ReadObjectType currentObject = ReadObjectType.PERMISSION;

        for (int i = 0; i < treeFile.length; i++) {
            char currentChar = (char) treeFile[i];

            if(currentChar == ' ' && currentObject == ReadObjectType.PERMISSION) {
                currentObject = ReadObjectType.FILE_NAME;
                continue;
            }

            if(currentChar == '\0' && currentObject == ReadObjectType.FILE_NAME) {
                currentObject = ReadObjectType.HASH;
                continue;
            }

            switch (currentObject) {
                case PERMISSION:
                    permissionSB.append(currentChar);
                    break;
                case FILE_NAME:
                    fileNameSB.append(currentChar);
                    break;
                case HASH:
                    hashBytes[currentByteIndex++] = treeFile[i];
                    break;
            }

            if(currentByteIndex  == 20) { // Then entity of tree is fully-read
                String hash = bytesToHex(hashBytes);
                String permission = permissionSB.toString();
                String nodeName = fileNameSB.toString();

                if(permission.equals("40000")) { // if entity is tree
                    subNodes.add(new GitNodeLabel(GitNodeLabel.NodeType.TREE, hash, nodeName));
                } else {
                    subNodes.add(new GitNodeLabel(GitNodeLabel.NodeType.BLOB, hash, nodeName));
                }

                currentObject = ReadObjectType.PERMISSION;
                currentByteIndex = 0;
                permissionSB = new StringBuilder();
                fileNameSB = new StringBuilder();
            }
        }
    }
    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

}
