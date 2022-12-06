package ru.snow4dv;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.InflaterInputStream;

public class GitRepo {
    private String localRepoPath;

    private final List<GitCommit> commits = new ArrayList<>();

    public GitRepo(String localRepoPath) {
        this.localRepoPath = localRepoPath;
    }

    public byte[] readGitObject(String objectName) throws IOException {
        String stringWithSlash = new StringBuilder(objectName).insert(2, '/').toString();
        InflaterInputStream stream = new InflaterInputStream(new FileInputStream(Paths.get(localRepoPath, ".git/objects", stringWithSlash).toString()));
        return stream.readAllBytes();
    }


    private void initFields() throws GitRepositoryNotFound {
        File repoDirectory = Paths.get(localRepoPath, ".git/objects").toFile();
        File[] files = repoDirectory.listFiles();
        try {
            for (File file :
                    files) {
                if(file.isDirectory()) {
                    File[] objectFiles = file.listFiles();
                    for (int i = 0; i < objectFiles.length; i++) {
                    }
                }
            }
        } catch(NullPointerException ex) {
            throw new GitRepositoryNotFound(localRepoPath);
        }
    }
}
