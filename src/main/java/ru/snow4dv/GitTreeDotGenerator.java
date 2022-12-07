package ru.snow4dv;

import ru.snow4dv.GitUnits.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class GitTreeDotGenerator {
    private final GitRepo gitRepo;

    private final Map<String, GitCommit> commits;
    private final Map<String, GitTree> trees;
    private final Map<String, GitBlob> blobs;



    public GitTreeDotGenerator(GitRepo gitRepo) {
        this.gitRepo = gitRepo;
        this.commits = gitRepo.getMapOfCertainObjects(GitCommit.class);
        this.trees = gitRepo.getMapOfCertainObjects(GitTree.class);
        this.blobs = gitRepo.getMapOfCertainObjects(GitBlob.class);
    }


    public String buildDotGraph() {
        TreeSet<String> graphLines = new TreeSet<>();
        graphLines.add("Digraph Git_Tree {");
        Collection<GitCommit> gitCommits = commits.values();
        for (GitCommit commit :
                gitCommits) {
            processCommit(graphLines, commit);
        }
        StringBuilder resultGraph = new StringBuilder();
        graphLines.forEach(s -> resultGraph.append(s).append('\n'));
        resultGraph.append('}');
        return resultGraph.toString();
    }

    private void processCommit(TreeSet<String> graphLines, GitCommit commit) {
        graphLines.add(String.format("node_%d[label=\"%s %s\\n%s\", color=\"%s\"]", commit.getIndex(),
                "Commit ", commit.getHash().substring(0,6), commit.getMessage(), "#00FF00"));

        GitNodeLabel rootTree = commit.getTreeLabel();
        graphLines.add(String.format("node_%d -> node_%d", commit.getIndex(),
                trees.get(rootTree.getHash()).getIndex()));

        List<String> parentCommitsHashes = commit.getParentCommits();
        for (String parentCommitHash :
                parentCommitsHashes) {
            GitCommit parentCommit = commits.get(parentCommitHash);
            graphLines.add(String.format("node_%d -> node_%d", parentCommit.getIndex(),
                    commit.getIndex()));
        }

        addTreeInfoToGraph(graphLines, rootTree);
    }

    private void addTreeInfoToGraph(TreeSet<String> graphLines, GitNodeLabel treeLabel) {
        GitTree mainTree = trees.get(treeLabel.getHash());
        graphLines.add(String.format("node_%d[label=\"%s %s\\n%s\", color=\"%s\"]", mainTree.getIndex(),
                "Tree ", mainTree.getHash().substring(0,6), treeLabel.getNodeName(), "#0000FF"));
        List<GitNodeLabel> labels = mainTree.getSubNodes();
        for (GitNodeLabel currentLabel :
                labels) {
            if(currentLabel.isTree()) {
                graphLines.add(String.format("node_%d -> node_%d", mainTree.getIndex(),
                        trees.get(currentLabel.getHash()).getIndex()));
                addTreeInfoToGraph(graphLines, currentLabel);
            } else {
                graphLines.add(String.format("node_%d[label=\"%s %s\\n%s\"]", blobs.get(currentLabel.getHash()).getIndex(),
                        "Blob ", currentLabel.getHash().substring(0,6), currentLabel.getNodeName()));
                graphLines.add(String.format("node_%d -> node_%d", mainTree.getIndex(),
                        blobs.get(currentLabel.getHash()).getIndex()));
            }
        }

    }

}
