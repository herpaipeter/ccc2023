package hu.herpaipeter.numbersequences;

import java.util.List;

public record NumberLinkMultiParent(long number, List<NumberLinkMultiParent> parent, List<NumberLinkMultiParent> children, int additionCount) {

    public boolean isRoot() {
        return parent.isEmpty();
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }

    @Override
    public String toString() {
        return "NumberLink{" +
                "number=" + number +
                ", parent=" + parent.size() +
                ", children=" + children.size() +
                '}';
    }
}
