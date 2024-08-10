package com.example;

import com.example.config.DataSourceContextHolder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionTest {
    public static void main(String[] args) {

        String fileName = "largefile.txt";
        int numberOfLines = 100000;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 1; i <= numberOfLines; i++) {
                writer.write("This is line number " + i);
                writer.newLine(); // Thêm dòng mới sau mỗi dòng
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        }
    }


