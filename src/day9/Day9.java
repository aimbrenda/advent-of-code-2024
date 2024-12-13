package day9;

import day1.Day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Day9 {



    public static void main(String[] args) {
        var filePath = "/day9.txt";

        long sum = 0;

        String input = readLists(filePath);

        List<String> outputString = new ArrayList<>();
        int id = 0;
        for(int i =0; i< input.length(); i++){
            int value = Integer.parseInt(input, i, i+1, 10);
            for(int j =0; j<value; j++){
                if(i%2 == 0){
                    outputString.add(String.valueOf(id));
                } else {
                    outputString.add(".");
                }
            }

            if(i%2 == 0)
                id++;
        }

        int count = 0;
        for(int i=0; i< outputString.size();i++){
            if(outputString.get(i) != ".")
                count++;
        }
        System.out.println(count);
        int startingPoint = outputString.size()-1;
        for(int i=0; i< count;i++){
            if(outputString.get(i) == ".") {

                while (startingPoint >= count && outputString.get(startingPoint) == ".") {
                    startingPoint--;
                }
//str.substring(0, index) + ch + str.substring(index + 1);
                outputString.set(i,outputString.get(startingPoint));
                outputString.set(startingPoint, ".");

            }

        }


        for(int i=0; i< count;i++){
            sum += i * Long.parseLong(outputString.get(i));
        }
        System.out.println(outputString);
        System.out.println(outputString.get(count-1));
        System.out.println(sum);
    }



    private static String readLists(String filePath) {
        try (InputStream inputStream = Day1.class.getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;

            while ((line = reader.readLine()) != null) {
                return line.trim();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
