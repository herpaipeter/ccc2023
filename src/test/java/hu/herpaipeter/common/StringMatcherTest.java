package hu.herpaipeter.common;

import org.junit.jupiter.api.Test;

import java.util.List;

class StringMatcherTest {

    @Test
    void readTestFile() {
        List<String> strings = FileReader.readResources("numbersequences/test.txt");
        strings.forEach(System.out::println);
    }

}