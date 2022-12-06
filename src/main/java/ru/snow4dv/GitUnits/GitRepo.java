package ru.snow4dv.GitUnits;

import ru.snow4dv.GitRepositoryNotFound;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.InflaterInputStream;

public class GitRepo {
    private String localRepoPath;

    private final List<GitCommit> commits = new ArrayList<>();

    public GitRepo(String localRepoPath) throws GitRepositoryNotFound {
        this.localRepoPath = localRepoPath;
        initFields();
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
            for (File objectDirPath :
                    files) {
                if(objectDirPath.isDirectory()) {
                    File[] objectFiles = objectDirPath.listFiles();
                    for (File objectFile :
                            objectFiles) {
                        String objectHash = objectDirPath.getName() + objectFile.getName();

                    }
                }
            }
        } catch(NullPointerException ex) {
            throw new GitRepositoryNotFound(localRepoPath);
        }
    }
}
