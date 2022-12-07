package ru.snow4dv.GitUnits;

import java.util.ArrayList;
import java.util.List;

public class GitCommit extends GitObject {
    private GitUser author;
    private GitUser committer;
    private final List<String> parentCommits = new ArrayList<>();
    private String treeHash;
    private String message;

    private GitTimestamp authorTimestamp;
    private GitTimestamp committerTimestamp;

    protected GitCommit(String hash, byte[] commitData) {
        super("commit",  hash, commitData);
        String commitPlainText = new String(commitData);

        String[] commitLines = commitPlainText.split("\n");

        int commitMessageStartIndex = 0;
        for (int i = 0; i < commitLines.length; i++) {
            if(commitLines[i].equals("")) {
                commitMessageStartIndex = i + 1;
                break;
            }

            String commitParameterType = commitLines[i].substring(0, commitLines[i].indexOf(' '));
            String commitParameterArgument = commitLines[i].substring(commitLines[i].indexOf(' ') + 1);
            switch(commitParameterType) {
                case "tree":
                    this.treeHash = commitParameterArgument;
                    break;
                case "parent":
                    this.parentCommits.add(commitParameterArgument);
                    break;
                case "author":
                    author = new GitUser(commitParameterArgument);
                    authorTimestamp = new GitTimestamp(commitParameterArgument);
                    break;
                case "committer":
                    committer = new GitUser(commitParameterArgument);
                    committerTimestamp = new GitTimestamp(commitParameterArgument);
                    break;
            }
        }

        StringBuilder message = new StringBuilder();

        for (int i = commitMessageStartIndex; i < commitLines.length; i++) {
            if(i != commitMessageStartIndex) message.append('\n');

            message.append(commitLines[i]);
        }

        this.message = message.toString();
    }

    public GitUser getAuthor() {
        return author;
    }

    public GitUser getCommitter() {
        return committer;
    }

    public List<String> getParentCommits() {
        return parentCommits;
    }

    public String getTreeHash() {
        return treeHash;
    }

    public String getMessage() {
        return message;
    }
}
