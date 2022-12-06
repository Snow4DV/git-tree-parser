package ru.snow4dv;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

public class GitCommit extends GitObject {
    private GitUser author;
    private GitUser committer;
    private final List<String> parentCommits = new ArrayList<>();
    private String treeHash;
    private String message;

    private GitTimestamp authorTimestamp;
    private GitTimestamp committerTimestamp;

    /**
     * Full commit in plain text (without type-length description)
     * @param commitPlainText Text content of commit object
     */
    protected GitCommit(String commitPlainText) {
        super("commit", commitPlainText.length(), commitPlainText);
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
