package ru.snow4dv;

import ru.snow4dv.GitUnits.*;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        GitRepo gitRepo = new GitRepo("c:/users/ASUS/git_repos/test-repo", true);
        GitTreeDotGenerator generator = new GitTreeDotGenerator(gitRepo);
        System.out.println(generator.buildDotGraph());
    }





}