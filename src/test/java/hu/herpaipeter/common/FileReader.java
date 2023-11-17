package hu.herpaipeter.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileReader {

    private static final String AOC_2022_INPUT_FILE_DEFAULT_PATH = "src\\test\\java\\hu\\herpaipeter\\aoc2022\\%s\\input.txt";
    private static final String AOC_2022_FILE_PATH = "src\\test\\java\\hu\\herpaipeter\\aoc2022\\%s\\%s";


    public static List<String> readAoCInputFileLines(String dayName) {
        return readStringLines(String.format(AOC_2022_INPUT_FILE_DEFAULT_PATH, dayName));
    }

    public static List<String> readAoCInputFileLines(String dayName, String filename) {
        return readStringLines(String.format(AOC_2022_FILE_PATH, dayName, filename));
    }

    public static List<String> readResources(String filename) {
        String path = FileReader.class.getClassLoader().getResource(filename).getPath();
        return readStringLines(path);
    }

    public static List<String> readStringLines(String fileName) {
        try {
            return Files.readAllLines(Paths.get(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> readStringLines(Path filePath) {
        try {
            return Files.readAllLines(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
