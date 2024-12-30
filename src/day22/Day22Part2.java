package day22;

import day1.Day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Day22Part2 {



    public static void main(String[] args) {
        var filePath = "/day22.txt";

        List<Long> input = readLists(filePath);


        Map<String, Integer> sequences = new HashMap<>();

        for (Long v : input) {
            Set<String> firstSequences = new HashSet<>();
            long secret= v;

            int minus1 = 0, minus2 = 0, minus3 = 0, delta =0;
            String key = "";

            for(int i=0; i<2000;i++){
                long tmpsecret = calculateNextNumber(secret);

                if(i>0){
                    minus3 =minus2;
                    minus2 = minus1;
                    minus1 = delta;
                    int price = (int)(tmpsecret%10);
                    delta = price - (int)(secret%10);
                    if (i > 3) {
                        key = minus3+","+minus2+","+minus1+","+delta;

                        if (!firstSequences.contains(key)) {
                            firstSequences.add(key);
                            if(sequences.containsKey(key)){
                                sequences.put(key, sequences.get(key) + price);
                            }else{
                                sequences.put(key, price);
                            }
                        }
                    }
                }

                secret = tmpsecret;
            }


        }


        System.out.println(Collections.max(sequences.values()));

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
