package com.github.liyasharipova;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) throws FileNotFoundException {

        if (args.length == 0) {
            throw new IllegalArgumentException(
                    "Должен быть один аргумент, обозначчающий размер последовательности в каждом геноме");
        }
        int k = Integer.parseInt(args[0]);

        StringBuilder stringBuilder = new StringBuilder();
        Set<String> genomParts = null;

        try (Scanner scanner = new Scanner(new File("Genome_1.txt"))) {
            String genom = null;
            if (scanner.hasNext()) {
                genom = scanner.nextLine();
                stringBuilder.append(genom);
            }
            genomParts = extractGenomPartsDividedByFrom(k, stringBuilder);
        }



    }

    private static Set<String> extractGenomPartsDividedByFrom(int k, StringBuilder genom) {
        Set<String> genomParts = new HashSet<>();

        int partsAmount = genom.length() / k;

        for (int i = 0; i < partsAmount; i++) {
            String part = genom.substring(i * k, 1 + k * (i + 1));
            genomParts.add(part);
        }
        return genomParts;
    }
}
