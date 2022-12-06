package ru.snow4dv;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.stream.Stream;
import java.util.zip.InflaterInputStream;

public class FilesUtility {
    static Stream<String> ReadFile(String path) throws FileNotFoundException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        return bufferedReader.lines();
    }

}
