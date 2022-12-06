package ru.snow4dv;

import ru.snow4dv.GitUnits.GitObject;
import ru.snow4dv.GitUnits.GitRepo;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        GitRepo gitUtility = new GitRepo("/home/m1ke/git_repos/kisscm");


        byte[] obj = gitUtility.readGitObject("4c78c3ea49f36fae8b954f8c9dce64d87914c20d");
        GitObject object = GitObject.createObject(obj);

        System.out.println(new String(obj));
    }
}