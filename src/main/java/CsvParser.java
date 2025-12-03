package com.student_work;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvParser {

    public static int parseProductId(String raw) {
        if (raw == null) throw new IllegalArgumentException("ID cannot be null");
        raw = raw.trim();
        if (raw.isEmpty()) throw new IllegalArgumentException("ID cannot be empty");
        if (raw.startsWith("\"") && raw.endsWith("\"")) {
            raw = raw.substring(1, raw.length() - 1);
        }
        raw = raw.trim();
        if (raw.isEmpty()) throw new IllegalArgumentException("ID cannot be empty");
        return Integer.parseInt(raw);
    }

    public static String parseProductName(String raw) {
        if (raw == null) throw new IllegalArgumentException("Name cannot be null");
        if (raw.trim().isEmpty()) return "";
        raw = raw.trim();
        if (raw.startsWith("\"") && raw.endsWith("\"")) raw = raw.substring(1, raw.length() - 1);
        raw = raw.replace("\"\"", "\"");
        return raw.trim();
    }

    public static String parseCategory(String raw) {
        if (raw == null) throw new IllegalArgumentException("Category cannot be null");
        raw = raw.trim();
        if (raw.isEmpty()) throw new IllegalArgumentException("Category cannot be empty");
        if (raw.startsWith("\"") && raw.endsWith("\"")) raw = raw.substring(1, raw.length() - 1);
        raw = raw.replace("\"\"", "\"");
        return raw.trim();
    }

    public static double parsePrice(String raw) {
        if (raw == null) throw new IllegalArgumentException("Price cannot be null");
        raw = raw.trim();
        if (raw.isEmpty()) throw new IllegalArgumentException("Price cannot be empty");
        if (raw.startsWith("\"") && raw.endsWith("\"")) raw = raw.substring(1, raw.length() - 1);
        raw = raw.trim();
        if (raw.isEmpty()) throw new IllegalArgumentException("Price cannot be empty");
        double value;
        try {
            value = Double.parseDouble(raw);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid price format");
        }
        if (value < 0) throw new IllegalArgumentException("cannot be negative");
        return value;
    }

    public static Product parseLine(String line) {
        List<String> fields = splitCsv(line);
        if (fields.size() != 4) throw new IllegalArgumentException("Expected 4 fields");
        int id = parseProductId(fields.get(0));
        String name = parseProductName(fields.get(1));
        String category = parseCategory(fields.get(2));
        double price = parsePrice(fields.get(3));
        return new Product(id, name, category, price);
    }

    public static List<Product> parseCsv(String filepath) throws IOException {
        List<Product> products = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                try {
                    products.add(parseLine(line));
                } catch (Exception e) {
                    continue;
                }
            }
        }
        return products;
    }

    private static List<String> splitCsv(String line) {
        List<String> result = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder cur = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    cur.append('"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                result.add(cur.toString());
                cur.setLength(0);
            } else {
                cur.append(c);
            }
        }
        result.add(cur.toString());
        return result;
    }
}
