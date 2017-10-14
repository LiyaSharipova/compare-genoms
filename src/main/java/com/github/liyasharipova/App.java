package com.github.liyasharipova;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Hello world!
 */
public class App {

    private static Connection conn;

    public App() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        conn = null;
        conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/mydb", "postgres", "postgres");
    }

    public static void main(String[] args) throws FileNotFoundException, SQLException {

        if (args.length == 0) {
            throw new IllegalArgumentException(
                    "Должен быть один аргумент, обозначчающий размер последовательности в каждом геноме");
        }
        int k = Integer.parseInt(args[0]);

        StringBuilder stringBuilder = new StringBuilder();
        Set<String> genomParts1 = null;
        Set<String> genomParts2 = null;

        try (Scanner scanner = new Scanner(new File("Genome_1.txt"))) {
            String genom = null;
            if (scanner.hasNext()) {
                genom = scanner.nextLine();
                stringBuilder.append(genom);
            }
            genomParts1 = extractGenomPartsDividedByFrom(k, stringBuilder);
            addGenomToDB(genomParts1, 1);
            addGenomToDB(genomParts2, 2);

        }


    }


    private static double getJaccardSimilarity(Set<String> genomParts1, Set<String> genomParts2) {

    }

    private static void addGenomToDB(Set<String> genomParts, int id) throws SQLException {
        for (String part :
                genomParts) {
            PreparedStatement p = conn.prepareStatement("INSERT INTO parts (value, genom_id) " +
                    "VALUES (?,?)");
            p.setString(1, part);
            p.setInt(2, id);
            p.executeUpdate();
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
