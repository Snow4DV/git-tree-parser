package ru.snow4dv;

import java.io.IOException;

public class GitObjectNotFound extends RuntimeException {
    public GitObjectNotFound(String path) {
        super("Git error - object with hash " + path + " not found.\nCheck if all objects are unpacked.");
    }
}
