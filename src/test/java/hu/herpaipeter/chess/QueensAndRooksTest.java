package hu.herpaipeter.chess;

import hu.herpaipeter.common.FileReader;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

class QueensAndRooksTest {

    @Test
    void testSample() throws IOException {
        List<String> board = FileReader.readResources("chess/example.in");
        List<String> strings = new QueensAndRooks().placeFigures(board);
        writeOut(strings, "example.out");
    }

    @Test
    void level1() throws IOException {
        List<String> board = FileReader.readResources("chess/level1.in");
        List<String> strings = new QueensAndRooks().placeFigures(board);
        writeOut(strings, "level1.out");
    }

    @Test
    void level2() throws IOException {
        List<String> board = FileReader.readResources("chess/level2.in");
        List<String> strings = new QueensAndRooks().placeFigures(board);
        writeOut(strings, "level2.out");
    }

    @Test
    void level3() throws IOException {
        List<String> board = FileReader.readResources("chess/level3.in");
        List<String> strings = new QueensAndRooks().placeFigures(board);
        writeOut(strings, "level3.out");
    }

    @Test
    void level4() throws IOException {
        List<String> board = FileReader.readResources("chess/level4.in");
        List<String> strings = new QueensAndRooks().placeFigures(board);
        writeOut(strings, "level4.out");
    }

    @Test
    void level5() throws IOException {
        List<String> board = FileReader.readResources("chess/level5.in");
        List<String> strings = new QueensAndRooks().placeFigures(board);
        writeOut(strings, "level5.out");
    }
    private static void writeOut(List<String> strings, String fileName) throws IOException {
        String outputFormat = strings.stream().collect(Collectors.joining("\n"));
        Files.write(Paths.get(fileName), outputFormat.getBytes());
    }

}