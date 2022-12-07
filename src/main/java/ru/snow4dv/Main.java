package ru.snow4dv;

import ru.snow4dv.GitUnits.GitObject;
import ru.snow4dv.GitUnits.GitRepo;
import ru.snow4dv.GitUnits.GitTree;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        GitRepo gitUtility = new GitRepo("/home/m1ke/git_repos/kisscm");


        byte[] obj = gitUtility.readGitObject("a72beb03ef4a6fe3a367a9e54b05392234948acc");
        GitObject object = GitObject.createObject(obj);

        System.out.println(new String(obj));
    }
}