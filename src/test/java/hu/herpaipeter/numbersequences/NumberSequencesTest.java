package hu.herpaipeter.numbersequences;

import hu.herpaipeter.common.FileReader;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class NumberSequencesTest {

    @Test
    void test1() {
        List<NumberLink> calculate = new NumberSequences().calculate(1);
        assertEquals(2, calculate.size());
    }

    @Test
    void test2() {
        List<NumberLink> calculate = new NumberSequences().calculate(2);
        assertEquals(3, calculate.size());
    }

    @Test
    void test3() {
        List<NumberLink> calculate = new NumberSequences().calculate(3);
        assertEquals(5, calculate.size());
    }

    @Test
    void test7() {
        List<NumberLink> calculate = new NumberSequences().calculate(7);
        assertEquals(16, calculate.size());
    }

    @Test
    void testSequences() {
        NumberSequences numberSequences = new NumberSequences();
        List<NumberLink> calculate = numberSequences.calculate(1364);
        List<List<Long>> sequences = numberSequences.getSequences(calculate);
        Optional<List<Long>> shortestSequence = numberSequences.getShortestSequence(calculate);
        shortestSequence.ifPresent(System.out::println);
        List<List<Long>> distinct = sequences.stream().sorted(Comparator.comparingInt(List::size)).distinct().collect(Collectors.toList());
        System.out.println(sequences.size());
        System.out.println(distinct.size());
        //sequences.forEach(System.out::println);
    }

    @Test
    void sequencesInput1() throws IOException {
        List<String> strings = FileReader.readResources("numbersequences/input1.in");
        NumberSequences numberSequences = new NumberSequences();
        List<List<Long>> collected = strings.stream().map(num -> numberSequences.calculate(Long.parseLong(num)))
                .map(list -> numberSequences.getShortestSequence(list))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        collected.forEach(System.out::println);
        String result = collected.stream().map(list -> list.stream().map(l -> l.toString()).collect(Collectors.joining(", "))).collect(Collectors.joining("\n"));
        Files.write(Paths.get("outputS1"), result.getBytes());
    }

    @Test
    void sequencesSizeInput1() throws IOException {
        List<String> strings = FileReader.readResources("numbersequences/input1.in");
        NumberSequences numberSequences = new NumberSequences();
        List<List<List<Long>>> collected = strings.stream().map(num -> numberSequences.calculate(Long.parseLong(num)))
                .map(list -> numberSequences.getSequences(list))
                .collect(Collectors.toList());
        String result = collected.stream().map(List::size).map(i -> i.toString()).collect(Collectors.joining("\n"));
        Files.write(Paths.get("outputS3"), result.getBytes());
    }

    @Test
    void sequencesFor307291() throws IOException {
        NumberSequences numberSequences = new NumberSequences();
        List<NumberLink> calculate = numberSequences.calculate(307291L);
        List<List<Long>> sequences = numberSequences.getSequences(calculate);
        List<List<Long>> collected = sequences.stream().sorted(Comparator.comparingInt(List::size)).distinct().collect(Collectors.toList());
        String collect = collected.stream().map(list -> list.stream().map(l -> l.toString()).collect(Collectors.joining(", "))).collect(Collectors.joining("\n"));
        Files.write(Paths.get("outputS5"), collect.getBytes());
    }

    @Test
    void sequencesInput2() throws IOException {
        List<String> strings = FileReader.readResources("numbersequences/input3.in");
        NumberSequencesMultiParent numberSequences = new NumberSequencesMultiParent();
        List<NumberLinkMultiParent> calculate = numberSequences.calculate(162976411275680L);
        System.out.println(calculate.size());
//        List<List<Long>> collected = strings.stream().map(num -> numberSequences.calculate(Long.parseLong(num)))
//                .map(list -> numberSequences.getShortestSequence(list))
//                .filter(Optional::isPresent)
//                .map(Optional::get)
//                .collect(Collectors.toList());
//        String result = collected.stream().map(list -> list.stream().map(l -> l.toString()).collect(Collectors.joining(", "))).collect(Collectors.joining("\n"));
//        Files.write(Paths.get("outputS2"), result.getBytes());
    }
}