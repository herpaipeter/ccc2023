package hu.herpaipeter.numbersequences;

import java.util.List;

public record NumberLink(long number, NumberLink parent, List<NumberLink> children, int additionCount) {

    public boolean isRoot() {
        return parent == null;
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }

    @Override
    public String toString() {
        return "NumberLink{" +
                "number=" + number +
                ", parent=" + (null !=  parent ? parent.number : "null") +
                ", children=" + children.size() +
                '}';
    }
}
