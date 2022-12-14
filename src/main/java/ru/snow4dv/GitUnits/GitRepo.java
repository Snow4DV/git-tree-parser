package ru.snow4dv.GitUnits;

import ru.snow4dv.GitObjectNotFound;
import ru.snow4dv.GitRepositoryNotFound;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.InflaterInputStream;

public class GitRepo {
    private String localRepoPath;

    private final boolean loadFiles; // TODO: implement enabling file loading

    private final List<GitObject> objects = new ArrayList<>();

    public GitRepo(String localRepoPath) throws GitRepositoryNotFound {
        this.localRepoPath = localRepoPath;
        loadFiles = false;
        loadRepoObjects();
    }

    public GitRepo(String localRepoPath, boolean loadFiles) throws GitRepositoryNotFound {
        this.localRepoPath = localRepoPath;
        this.loadFiles = loadFiles;
        loadRepoObjects();
    }

    public byte[] readGitObject(String objectName) throws IOException {
        String stringWithSlash = new StringBuilder(objectName).insert(2, '/').toString();
        InflaterInputStream stream = new InflaterInputStream(new FileInputStream(Paths.get(localRepoPath, ".git/objects", stringWithSlash).toString()));
        return stream.readAllBytes();
    }


    public GitObject getGitObject(String hash) throws IOException {
        return GitObject.createObject(hash, readGitObject(hash), loadFiles);
    }

    private void loadRepoObjects() throws GitRepositoryNotFound {
        File repoDirectory = Paths.get(localRepoPath, ".git/objects").toFile();
        File[] files = repoDirectory.listFiles();
        String objectHash = "NO_HASH";
        try {
            for (File objectDirPath :
                    files) {
                if(objectDirPath.isDirectory()) {
                    File[] objectFiles = objectDirPath.listFiles();
                    for (File objectFile :
                            objectFiles) {
                        objectHash = objectDirPath.getName() + objectFile.getName();
                        GitObject gitObject = getGitObject(objectHash);
                        if(gitObject != null) {
                            objects.add(gitObject);
                        }

                    }
                }
            }
        } catch(NullPointerException e) {
            throw new GitRepositoryNotFound(localRepoPath);
        } catch (IOException e) {
            throw new GitObjectNotFound(objectHash);
        }
    }

    public <T extends GitObject> List<T> getObjectsOfType(Class<T> objectClass) {
        return objects.stream()
                .filter(gitObject -> objectClass.isAssignableFrom(gitObject.getClass()))
                .map(gitObject -> (T) gitObject)
                .collect(Collectors.toList());
    }

    public <T extends GitObject> Map<String, T> getMapOfCertainObjects(Class<T> objectClass) {
        return objects.stream()
                .filter(gitObject -> objectClass.isAssignableFrom(gitObject.getClass()))
                .map(gitObject -> (T) gitObject)
                .collect(Collectors.toMap(T::getHash, t -> t));

    }

    public GitCommit getFirstCommit() {
        for (GitObject gitObject :
                objects) {
            if (gitObject instanceof GitCommit) {
                GitCommit commit = (GitCommit) gitObject;
                if (commit.getParentCommits().size() == 0) {
                    return commit;
                }
            }
        }
        return null;
    }

}
