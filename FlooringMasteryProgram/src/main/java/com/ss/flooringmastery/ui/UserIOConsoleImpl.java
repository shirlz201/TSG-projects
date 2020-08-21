/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ss.flooringmastery.ui;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 *
 * @author shirl
 */
public class UserIOConsoleImpl implements UserIO {

    private Scanner sc = new Scanner(System.in);

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public void printInt(int num) {
        System.out.println(num);
    }

    @Override
    public double readDouble(String prompt) {
        String userInput = readString(prompt);
        double result = Double.parseDouble(userInput);
        return result;
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        double result;
        do {
            result = readDouble(prompt);
        } while (result > min && result < max);
        return result;
    }

    @Override
    public float readFloat(String prompt) {
        String userInput = readString(prompt);
        float result = Float.parseFloat(userInput);
        return result;
    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        float result;
        do {
            result = readFloat(prompt);
        } while (result < min && result > max);
        return result;
    }

    @Override
    public int readInt(String prompt) {
        print(prompt);
        String userInput = sc.nextLine();
        return Integer.parseInt(userInput);
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        int answer = readInt(prompt);
        while (answer < min || answer > max) {
            print("Invalid option.");
            answer = readInt(prompt);
        }
        return answer;
    }

    @Override
    public long readLong(String prompt) {
        String userInput = readString(prompt);
        long result = Long.parseLong(userInput);
        return result;
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        long result;
        do {
            result = readLong(prompt);
        } while (result < min && result > max);

        return result;
    }

    @Override
    public String readString(String prompt) {
        print(prompt);
        return sc.nextLine();
    }

    @Override
    public BigDecimal readBigDecimal(String prompt) {

        print(prompt);
        String userInput = sc.nextLine();
        return new BigDecimal(userInput);
    }

    @Override
    public LocalDate readLocalDate(String prompt) {
        boolean valid = false;
        LocalDate result = null;
        do {
            String value = null;
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                value = readString(prompt);
                result = LocalDate.parse(value, formatter);
                valid = true;
            } catch (DateTimeParseException e) {
                System.out.printf("The value '%s' is not a valid date. (MM-DD-YYYY)", value);
            }
        } while (!valid);

        return result;
    }

    @Override
    public LocalDate readLocalDate(String prompt, LocalDate min, LocalDate max) {
        boolean valid = false;
        LocalDate result = null;
        do {
            result = readLocalDate(prompt);
            if (result.isAfter(min) && result.isBefore(max)) {
                valid = true;

            } else {
                System.out.printf("Please choose a date within bounds. (%s to %s)\n", min, max);
            }
        } while (!valid);

        return result;
    }

}
