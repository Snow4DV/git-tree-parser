package ru.snow4dv;

import java.io.IOException;

public class GitRepositoryNotFound extends IOException {
    public GitRepositoryNotFound(String path) {
        super("Git repository not found at: " + path);
    }
}
