package com.github.liyasharipova;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedHashSet;
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
                    "Должен быть один аргумент, обозначающий размер последовательности в каждом геноме");
        }
        int k = Integer.parseInt(args[0]);

        String fileName1 = "Genome_1.txt";
        String fileName2 = "Genome_2.txt";

        Set<String> genomParts1 = extractGenomPartsSet(k, fileName1);
        Set<String> genomParts2 = extractGenomPartsSet(k, fileName2);
        addGenomToDB(genomParts1, 1);
        addGenomToDB(genomParts2, 2);
        System.out.println("JaccardSimilarity = " + getJaccardSimilarity(genomParts1, genomParts2));
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

    private static double getJaccardSimilarity(Set<String> genomParts1, Set<String> genomParts2) throws SQLException {
        PreparedStatement p = conn.prepareStatement("SELECT count(value)::REAL FROM parts AS p " +
                "WHERE (p.genom_id = 1)  INTERSECT " +
                "SELECT count(value)::REAL FROM parts AS p " +
                "WHERE (p.genom_id = 2) /" +
                "(SELECT count(VALUE)::REAL FROM parts AS p " +
                "WHERE (p.genom_id = 1) UNION " +
                "SELECT count(value)::REAL FROM parts AS p " +
                "WHERE (p.genom_id = 2)) ");
        return p.executeUpdate();
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
}
