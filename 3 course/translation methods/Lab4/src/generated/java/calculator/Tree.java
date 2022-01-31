package calculator;

import java.util.ArrayList;
import java.util.List;

public class Tree {
    private final String node;
    private final List<Tree> children;

    public Tree(final String node) {
        this.node = node;
        this.children = new ArrayList<>();
    }

    public void addChild(final Tree node) {
        children.add(node);
    }

    public void addChild(final String node) {
        children.add(new Tree(node));
    }

    public String getNode() {
        return node;
    }

    public List<Tree> getChildren() {
        return children;
    }
}
