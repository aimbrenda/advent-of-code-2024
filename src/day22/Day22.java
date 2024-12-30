package day22;

import day1.Day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Day22 {



    public static void main(String[] args) {
        var filePath = "/day22.txt";

        List<Long> input = readLists(filePath);



        System.out.println(input.size());
        long sum = 0;
        for (Long v :
                input) {

            long secret= v;
            for(int i=0; i<2000;i++){
                secret = calculateNextNumber(secret);
            }
            System.out.println(secret);
            sum += secret;
        }
        System.out.println(sum);
    }

    public static long calculateNextNumber(long value){
        long secretNumber = value;

        long output = secretNumber*64; //step1 A
        output = output^secretNumber; //step1 B
        secretNumber = output;
        output = secretNumber % 16777216; //step1 C
        secretNumber = output;


        output = secretNumber / 32; //step2 A
        output = output^secretNumber; //step2 B
        secretNumber = output; //step2 C
        output = secretNumber % 16777216; //step1 D
        secretNumber = output;

        output = secretNumber *2048; //step3 A
        output = output^secretNumber; //step3 B
        secretNumber = output; //step3 C
        output = secretNumber % 16777216; //step3 D
        secretNumber = output;

        return secretNumber;
    }





    private static List<Long> readLists(String filePath) {
        List<Long> map = new ArrayList<>();
        try (InputStream inputStream = Day1.class.getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;

            while ((line = reader.readLine()) != null) {
                map.add(Long.valueOf(line));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }
}
