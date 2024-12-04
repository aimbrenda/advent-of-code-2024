package day4;

import day1.Day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day4Part2 {

    // find the X
    // search regex back and forward strings
    // translate verticals in new strings and apply step one again
    // translate diagolans to strings how? from last index of the row go down till the index is > then the limit
    // from (j = length -1; j>=0; j--)
    //      for (i = 0; i< lenghthigh; i++)
    //          if( j+i < length)
    //              string += value[i][j+i]

    public static void main(String[] args) {
        var filePath = "/day4.txt";
        var regex = "XMAS";
        Pattern stringPattern = Pattern.compile(regex);

        var reports = new ArrayList<String>();

        readLists(filePath, reports);

        var processingList = new ArrayList<>(reports);

        int findings = 0 ;


        for(int i =0; i< reports.size()-2; i++){
            Pattern pattern1 = Pattern.compile("M[A-Z]{1}S");
            Pattern pattern2 = Pattern.compile("S[A-Z]{1}M");
            Pattern pattern3 = Pattern.compile("S[A-Z]{1}S");
            Pattern pattern4 = Pattern.compile("M[A-Z]{1}M");


            var character = reports.get(i).toCharArray();
            for(int j = 0; j< reports.get(i).length()-2; j++) {
                // pattern1 M.S
                //          .A.
                //          M.S
                if (character[j] == 'M' && character[j+2] == 'S') {
                    if (reports.get(i + 1).toCharArray()[j + 1] == 'A' && reports.get(i + 2).toCharArray()[j] == 'M' && reports.get(i + 2).toCharArray()[j + 2] == 'S') {
                        findings++;
                    }
                }

                // pattern2 S.M
                //          .A.
                //          S.M
                if (character[j] == 'S' && character[j+2] == 'M') {
                    if (reports.get(i + 1).toCharArray()[j + 1] == 'A' && reports.get(i + 2).toCharArray()[j] == 'S' && reports.get(i + 2).toCharArray()[j + 2] == 'M') {
                        findings++;
                    }
                }

                // pattern3 S.S
                //          .A.
                //          M.M
                if (character[j] == 'S' && character[j+2] == 'S') {
                    if (reports.get(i + 1).toCharArray()[j + 1] == 'A' && reports.get(i + 2).toCharArray()[j] == 'M' && reports.get(i + 2).toCharArray()[j + 2] == 'M') {
                        findings++;
                    }
                }

                // pattern4 M.M
                //          .A.
                //          S.S
                if (character[j] == 'M' && character[j+2] == 'M') {
                    if (reports.get(i + 1).toCharArray()[j + 1] == 'A' && reports.get(i + 2).toCharArray()[j] == 'S' && reports.get(i + 2).toCharArray()[j + 2] == 'S') {
                        findings++;
                    }
                }
            }
        }

        System.out.println(findings);
    }

    private static void readLists(String filePath, List<String> list) {
        try (InputStream inputStream = Day1.class.getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                list.add((line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
