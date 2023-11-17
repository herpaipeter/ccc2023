package hu.herpaipeter.numbersequences;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class NumberSequencesMultiParent {

    private static final List<Integer> adds = List.of(1, 3);
    private static final List<Integer> primes = List.of(7, 11, 13, 17, 19, 23, 29, 31, 37, 41);
    public static void main(String[] args) {

    }

    public List<NumberLinkMultiParent> calculate(long number) {
        List<NumberLinkMultiParent> results = new ArrayList<>();
        results.add(new NumberLinkMultiParent(number, new ArrayList<>(), new ArrayList<>(), 0));
        calculateChildren(results);
        return results;
    }

//    public List<List<Long>> getSequences(List<NumberLinkMultiParent> NumberLinkMultiParents) {
//        return NumberLinkMultiParents.stream()
//                .filter(nl -> 0 == nl.number())
//                .filter(NumberLinkMultiParent::isLeaf)
//                .map(nl -> getParentList(nl))
//                .collect(Collectors.toList());
//    }
//
//    public Optional<List<Long>> getShortestSequence(List<NumberLinkMultiParent> NumberLinkMultiParents) {
//        return NumberLinkMultiParents.stream()
//                .filter(nl -> 0 == nl.number())
//                .filter(NumberLinkMultiParent::isLeaf)
//                .map(this::getParentList)
//                .sorted(Comparator.comparingInt(List::size))
//                .findFirst();
//    }
//
//    private List<Long> getParentList(NumberLinkMultiParent nl) {
//        List<Long> result = new ArrayList<>();
//        NumberLinkMultiParent next = nl;
//        for (; !next.isRoot(); next = next.parent()) {
//            result.add(next.number());
//        }
//        result.add(next.number());
//        return result;
//    }

    private void calculateChildren(List<NumberLinkMultiParent> results) {
        List<NumberLinkMultiParent> newChildren = new ArrayList<>();
        results.stream().filter(nl -> 0 < nl.number())
                .filter(NumberLinkMultiParent::isLeaf)
                .forEach(nl -> {
                    newChildren.addAll(addNewChildren(results, nl));
                });
        results.addAll(newChildren);
        if (!newChildren.isEmpty()) {
            calculateChildren(results);
        }
    }

    private List<NumberLinkMultiParent> addNewChildren(List<NumberLinkMultiParent> links, NumberLinkMultiParent nl) {
        if (0 < nl.number()) {
            if (nl.additionCount() < 3) {
                List<NumberLinkMultiParent> subtracted = adds.stream()
                        .map(add -> nl.number() - add)
                        .filter(sub -> 0 <= sub)
                        .map(sub -> {
                            int newAdditionalCount = nl.additionCount() + 1;
                            Optional<NumberLinkMultiParent> parentOptional = linksContainsNumberWithAddCount(links, sub, newAdditionalCount);
                            if (parentOptional.isPresent()) {
                                parentOptional.get().parent().add(nl);
                                return parentOptional.get();
                            } else {
                                NumberLinkMultiParent link = new NumberLinkMultiParent(sub, new ArrayList<>(), new ArrayList<>(), newAdditionalCount);
                                link.parent().add(nl);
                                return link;
                            }
                        })
                        .collect(Collectors.toList());
                nl.children().addAll(subtracted);
            }
            List<NumberLinkMultiParent> divided = primes.stream()
                    .map(prime -> getDividableResult(nl.number(), prime))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .map(div -> {
                        Optional<NumberLinkMultiParent> parentOptional = linksContainsNumberWithAddCount(links, div, 0);
                        if (parentOptional.isPresent()) {
                            parentOptional.get().parent().add(nl);
                            return parentOptional.get();
                        } else {
                            NumberLinkMultiParent link = new NumberLinkMultiParent(div, new ArrayList<>(), new ArrayList<>(), 0);
                            link.parent().add(nl);
                            return link;
                        }
                    })
                    .collect(Collectors.toList());
            nl.children().addAll(divided);
            return nl.children();
        }
        return List.of();
    }

    private Optional<NumberLinkMultiParent> linksContainsNumberWithAddCount(List<NumberLinkMultiParent> links, Long sub, int newAdditionalCount) {
        return links.stream().filter(l -> l.number() == sub && l.additionCount() == newAdditionalCount).findFirst();
    }

    private Optional<Long> getDividableResult(long number, int prime) {
        long result = number / prime;
        if (result * prime == number) {
            return Optional.of(result);
        }
        return Optional.empty();
    }

}