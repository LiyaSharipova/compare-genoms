package com.github.liyasharipova;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) throws FileNotFoundException {

        if (args.length == 0) {
            throw new IllegalArgumentException(
                    "Должен быть один аргумент, обозначающий размер последовательности в каждом геноме");
        }
        int k = Integer.parseInt(args[0]);

        String fileName1 = "Genome_1.txt";
        String fileName2 = "Genome_2.txt";

        Set<String> genomParts1 = extractGenomPartsSet(k, fileName1);
        Set<String> genomParts2 = extractGenomPartsSet(k, fileName2);

    }

    private static Set<String> extractGenomPartsSet(int k, String fileName)
            throws FileNotFoundException {
        StringBuilder stringBuilder = new StringBuilder();
        Set<String> genomParts;
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNext()) {
                String genom = scanner.nextLine();
                stringBuilder.append(genom);
            }
        }
        return extractGenomPartsDividedByFrom(k, stringBuilder);
    }

    private static Set<String> extractGenomPartsDividedByFrom(int k, StringBuilder genom) {
        Set<String> genomParts = new LinkedHashSet<>();

        int partsAmount = genom.length() / k;

        for (int i = 0; i < partsAmount; i++) {
            String part = genom.substring(i * k, k * (i + 1));
            genomParts.add(part);
        }
        return genomParts;
    }
}
