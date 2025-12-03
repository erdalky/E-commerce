package com.student_work;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

public class ProductManager {

    public static void main(String[] args) {
        String csvPath = "src/main/resources/amazon-product-data.csv";
        String outputPath = "src/main/resources/search_results.txt";
        try {
            List<Product> products = CsvParser.parseCsv(csvPath);
            RedBlackTree tree = new RedBlackTree();
            for (Product p : products) tree.insert(p);

            int[] idsToSearch = {1001, 1002, 9999};
            PrintWriter out = new PrintWriter(new FileWriter(outputPath));

            for (int id : idsToSearch) {
                Product p = tree.search(id);
                if (p != null) {
                    out.println(formatProduct(p));
                    out.println();
                } else {
                    out.println("Product ID: " + id + " not found.");
                    out.println();
                }
            }

            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String formatProduct(Product p) {
        return "Product ID: " + p.getId() + "\n"
                + "Name: " + p.getName() + "\n"
                + "Category: " + p.getCategory() + "\n"
                + String.format("Price: $%.2f", p.getPrice());
    }

}
