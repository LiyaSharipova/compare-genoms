package com.github.liyasharipova;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) {

        if (args.length == 0) {
            throw new IllegalArgumentException(
                    "Должен быть один аргумент, обозначчающий размер последовательности в каждом геноме");
        }
        int k = Integer.parseInt(args[0]);

        GenomsList genomsList = new GenomsList();
        try (Scanner scanner = new Scanner(new File("Genome_1.txt"))) {
            String genom = null;
            if (scanner.hasNext()) {
                genom = scanner.nextLine();
            }
            List<String> genomParts = extractGenomPartsDividedByFrom(k, genom);
            genomsList.getAllGenoms().add(genomParts);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(genomsList);
    }

    private static List<String> extractGenomPartsDividedByFrom(int k, String genom) {
        ArrayList<String> parts = new ArrayList<>();

        int partsAmount = genom.length() / k;

        for (int i = 0; i < partsAmount; i++) {
            String part = genom.substring(i * k, 1 + k * (i + 1));
            parts.add(part);
        }
        return parts;
    }
}
