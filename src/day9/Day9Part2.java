package day9;

import day1.Day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Day9Part2 {

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

        System.out.println(id);


        id--;
        while(id > 0) {
            int pos = outputString.indexOf(Integer.toString(id));
            int size = outputString.lastIndexOf(Integer.toString(id)) - pos + 1;
            int free = 0;
            for(int i = 0; i < pos; i++) {
                if(outputString.get(i).equals("."))
                    free++;
                else
                    free = 0;
                if(free == size) {
                    for (int k = 0; k < size; k++) {
                        outputString.remove(i - size + k + 1);
                        outputString.add(i - size + 1, Integer.toString(id));
                        outputString.remove(pos);
                        outputString.add(pos + size - 1, ".");
                    }
                    break;
                }
            }
            id--;
        }

        long score = 0;
        for(int i = 0; i < outputString.size(); i++)
            if (!outputString.get(i).equals("."))
                score += i * Long.parseLong(outputString.get(i));
        System.out.println(score);

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
