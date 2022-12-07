package ru.snow4dv;

import ru.snow4dv.GitUnits.GitCommit;
import ru.snow4dv.GitUnits.GitObject;
import ru.snow4dv.GitUnits.GitRepo;
import ru.snow4dv.GitUnits.GitTree;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        GitRepo gitRepo = new GitRepo("c:/users/ASUS/git_repos/kisscm");

        List<GitCommit> commits = gitRepo.getObjectsOfType(GitCommit.class);
        List<GitTree> trees = gitRepo.getObjectsOfType(GitTree.class);


        /*byte[] obj = gitUtility.readGitObject("a72beb03ef4a6fe3a367a9e54b05392234948acc");
        GitObject object = GitObject.createObject(obj);*/


    }
}