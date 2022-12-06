package ru.snow4dv;

import java.io.*;
import java.nio.file.Paths;
import java.util.zip.InflaterInputStream;

public class GitRepo {
    private String localRepoPath;

    public GitRepo(String localRepoPath) {
        this.localRepoPath = localRepoPath;
    }

    public byte[] readGitObject(String objectName) throws IOException {
        String stringWithSlash = new StringBuilder(objectName).insert(2, '/').toString();
        InflaterInputStream stream = new InflaterInputStream(new FileInputStream(Paths.get(localRepoPath, ".git/objects", stringWithSlash).toString()));
        return stream.readAllBytes();
    }
}
