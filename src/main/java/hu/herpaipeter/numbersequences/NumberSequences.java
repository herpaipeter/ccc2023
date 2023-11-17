package hu.herpaipeter.numbersequences;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class NumberSequences {

    private static final List<Integer> adds = List.of(1, 3);
    private static final List<Integer> primes = List.of(7, 11, 13, 17, 19, 23, 29, 31, 37, 41);
    public static void main(String[] args) {

    }

    public List<NumberLink> calculate(long number) {
        List<NumberLink> results = new ArrayList<>();
        results.add(new NumberLink(number, null, new ArrayList<>(), 0));
        calculateChildren(results);
        return results;
    }

    public List<List<Long>> getSequences(List<NumberLink> numberLinks) {
        return numberLinks.stream()
                .filter(nl -> 0 == nl.number())
                .filter(NumberLink::isLeaf)
                .map(nl -> getParentList(nl))
                .collect(Collectors.toList());
    }

    public Optional<List<Long>> getShortestSequence(List<NumberLink> numberLinks) {
        return numberLinks.stream()
                .filter(nl -> 0 == nl.number())
                .filter(NumberLink::isLeaf)
                .map(this::getParentList)
                .sorted(Comparator.comparingInt(List::size))
                .findFirst();
    }

    private List<Long> getParentList(NumberLink nl) {
        List<Long> result = new ArrayList<>();
        NumberLink next = nl;
        for (; !next.isRoot(); next = next.parent()) {
            result.add(next.number());
        }
        result.add(next.number());
        return result;
    }

    private void calculateChildren(List<NumberLink> results) {
        List<NumberLink> newChildren = new ArrayList<>();
        results.stream().filter(nl -> 0 < nl.number())
                .filter(NumberLink::isLeaf)
                .forEach(nl -> {
                    newChildren.addAll(addNewChildren(nl));
                });
        results.addAll(newChildren);
        if (!newChildren.isEmpty()) {
            calculateChildren(results);
        }
    }

    private List<NumberLink> addNewChildren(NumberLink nl) {
        if (0 < nl.number()) {
            if (nl.additionCount() < 3) {
                List<NumberLink> subtracted = adds.stream()
                        .map(add -> nl.number() - add)
                        .filter(sub -> 0 <= sub)
                        .map(sub -> new NumberLink(sub, nl, new ArrayList<>(), nl.additionCount() + 1))
                        .collect(Collectors.toList());
                nl.children().addAll(subtracted);
            }
            List<NumberLink> divided = primes.stream()
                    .map(prime -> getDividableResult(nl.number(), prime))
                    .filter(Optional::isPresent)
                    .map(divOpt -> new NumberLink(divOpt.get(), nl, new ArrayList<>(), 0))
                    .collect(Collectors.toList());
            nl.children().addAll(divided);
            return nl.children();
        }
        return List.of();
    }

    private Optional<Long> getDividableResult(long number, int prime) {
        long result = number / prime;
        if (result * prime == number) {
            return Optional.of(result);
        }
        return Optional.empty();
    }

}