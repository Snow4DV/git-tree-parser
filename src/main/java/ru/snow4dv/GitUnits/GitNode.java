package ru.snow4dv.GitUnits;

public class GitNode extends GitObject{
    private String permission;

    private String hash; // TODO: think of other implementation or finish this one


    /**
     * Method reads blob or tree according to current object fields
     * @return GitTree or GitBlob upcasted to GitNode
     */
    public GitNode readData() {
        if(permission.startsWith("1")) {

        }
    }
    protected GitNode(String type, int length, String plainText) {
        super(type, length, plainText);
    }
}
