package day7;

import day1.Day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day7 {



    public static void main(String[] args) {
        var filePath = "/day7.txt";


        Map<Long,List<Long>> operations = new HashMap<>();

        List<Long> results = new ArrayList<>();
        List<List<Long>> addendums = new ArrayList<>();

        readLists(filePath, results, addendums);

        List<String> combinations = new LinkedList<>();

        for(List<Long> l : addendums) {
            StringBuilder a = new StringBuilder();
            for(Long i: l){
                a.append("1");
            }

            a.replace(0,1,"");

            combinations.add(a.toString());
        }

        //System.out.println(combinations);

        long sum = 0;

        // for every equation
        for(int i =0; i< results.size(); i++){
            boolean correct = false;
            int n = 0;

            // try all the combination increasing a value from 0 to the max given the radix
            while (n <= Long.parseLong(combinations.get(i),2)){
                // create string in the desired base
                String nBase2 = Long.toString(n,2);
                int length = combinations.get(i).length() - nBase2.length();

                StringBuilder buildPadding = new StringBuilder();
                for(int padding = 0; padding< length; padding++){
                    buildPadding.append("0");
                }

                buildPadding.append(nBase2);

                String input = buildPadding.toString();
                //System.out.println(input);
                long tmpResult = addendums.get(i).get(0);
                for(int j = 1; j< addendums.get(i).size(); j++){
                    switch (input.toCharArray()[j-1]){
                        case '1':
                            tmpResult = tmpResult * addendums.get(i).get(j);
                            break;
                        case '0':
                            tmpResult = tmpResult + addendums.get(i).get(j);
                            break;
                    }
                }

                //System.out.println(addendums.get(i));
                //System.out.println(tmpResult + " " + results.get(i));
                if(tmpResult == results.get(i)) {
                    correct = true;
                    break;
                }

                n++;
            }

            if (correct){
                sum += results.get(i);
            }

            System.out.println(i + " " +sum + " " +  results.get(i));
        }

        System.out.println(sum);
    }



    private static void readLists(String filePath, List<Long> results, List<List<Long>> addendums) {
        try (InputStream inputStream = Day1.class.getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] splitValue = line.split(":");
                long result = Long.parseLong(splitValue[0].trim());

                List<Long> values = new LinkedList<>();
                var addendum = Arrays.stream(splitValue[1].trim().split(" ")).map(v -> Long.parseLong(v.trim())).toList();

                results.add(result);
                addendums.add(addendum);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
