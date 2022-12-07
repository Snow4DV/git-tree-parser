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
     * @param treeContent read tree file without length and type information
     */
    public GitTree(String hash, byte[] treeContent) {
        super("tree", hash, treeContent);

        StringBuilder permissionSB = new StringBuilder(), fileNameSB = new StringBuilder();

        byte[] hashBytes = new byte[20];
        int currentByteIndex = 0;

        ReadObjectType currentObject = ReadObjectType.PERMISSION;

        for (int i = 0; i < treeContent.length; i++) {
            char currentChar = (char) treeContent[i];

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
                    hashBytes[currentByteIndex++] = treeContent[i];
                    break;
            }

            if(currentByteIndex  == 20) { // Then entity of tree is fully-read
                String subNodeHash = bytesToHex(hashBytes);
                String subNodePermissions = permissionSB.toString();
                String subNodeName = fileNameSB.toString();

                if(subNodePermissions.equals("40000")) { // if entity is tree
                    subNodes.add(new GitNodeLabel(GitNodeLabel.NodeType.TREE, subNodeHash, subNodeName));
                } else {
                    subNodes.add(new GitNodeLabel(GitNodeLabel.NodeType.BLOB, subNodeHash, subNodeName));
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

    public List<GitNodeLabel> getSubNodes() {
        return subNodes;
    }

}
