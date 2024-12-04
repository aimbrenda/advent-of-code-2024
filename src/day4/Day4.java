package day4;

import day1.Day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day4 {

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


        // diagonals
        int stringSize = reports.get(0).length();
        for(int i = stringSize -1; i>=0; i--){
            StringBuilder diagonal =  new StringBuilder();
            for (int j = 0; j<reports.size(); j++){
                if(i+j < stringSize) {
                    //System.out.println("Processing " + j +" " + i + " " + reports.get(j) + "   " + reports.get(j).toCharArray()[i+j]);
                    diagonal.append(reports.get(j).toCharArray()[i+j]);
                }
            }
            processingList.add(diagonal.toString());
        }

        for(int i = 1; i<reports.size(); i++){
            StringBuilder diagonal =  new StringBuilder();
            int index = 0;
            for (int j = i; j<reports.size(); j++){
                //System.out.println(i + " " +reports.get(j));
                diagonal.append(reports.get(j).toCharArray()[index]);
                index++;
            }
            processingList.add(diagonal.toString());
        }


        for(int i = stringSize -1; i>=0; i--){
            StringBuilder diagonal =  new StringBuilder();
            for (int j = 0; j<reports.size(); j++){
                if(i-j >=0) {
                    //System.out.println("Processing " + j +" " + i + " " + reports.get(j) + "   " + reports.get(j).toCharArray()[i-j]);
                    diagonal.append(reports.get(j).toCharArray()[i-j]);
                }
            }

            processingList.add(diagonal.toString());
        }

        for(int i = 1; i<reports.size(); i++){
            StringBuilder diagonal =  new StringBuilder();
            int index = stringSize - 1;
            for (int j = i; j<reports.size(); j++){
                //System.out.println(i + " " +reports.get(j));
                diagonal.append(reports.get(j).toCharArray()[index]);
                index--;
            }
            processingList.add(diagonal.toString());
        }


        // verticals

        for(int i = 0; i< stringSize; i ++){
            StringBuilder vertical =  new StringBuilder();
            for (int j =0; j < reports.size(); j++) {
                vertical.append(reports.get(j).toCharArray()[i]);
            }
            processingList.add(vertical.toString());
        }

        for(String s : processingList) {
            Matcher m = stringPattern.matcher(s);
            if(m.find()) {
                do {
                    findings += 1;
                } while(m.find(m.start()+1));
            }

            var reverseString = new StringBuilder(s).reverse().toString();

            //System.out.println(reverseString);
            m = stringPattern.matcher(reverseString);
            if(m.find()) {
                do {
                    findings += 1;
                } while(m.find(m.start()+1));
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
