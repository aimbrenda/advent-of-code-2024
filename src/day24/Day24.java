package day24;

import day1.Day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Day24 {



    public static void main(String[] args) {
        var filePath = "/day24.txt";

        Map<String,Integer> registers = new HashMap<>();
        LinkedList<Operation> operations = new LinkedList<>();
        List<Operation> waitingOperations = new ArrayList<>();

        readLists(filePath, registers, operations);

        System.out.println(registers);
        System.out.println(operations);

        while (!operations.isEmpty()){
            int i = 0;
            for(; i< operations.size();i++){
                Operation o = operations.get(i);
                if(registers.containsKey(o.first) && registers.containsKey(o.second)){
                    break;
                }
            }

            Operation o = operations.remove(i);
            registers.put(o.result, o.evaluate(registers));
        }

        ArrayList<String> sortedKeys
                = new ArrayList<String>(registers.keySet().stream().filter(v-> v.startsWith("z")).toList());

        Collections.sort(sortedKeys);
        Collections.reverse(sortedKeys);

        StringBuilder num = new StringBuilder();
        for (String s: sortedKeys){
            num.append(registers.get(s));
        }

        String finalString = num.toString();

        System.out.println(finalString);
        System.out.println(Long.valueOf(finalString,2));


        //System.out.println(registers.entrySet().stream().filter(v -> v.getKey().startsWith("z")).collect(Collectors.toSet()));

    }





    private static void readLists(String filePath, Map<String,Integer> registers, LinkedList<Operation> operations) {
        try (InputStream inputStream = Day1.class.getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;

            int phase = 1;
            while ((line = reader.readLine()) != null) {
                if(line.isBlank()){
                    phase = 2;
                }else {

                    if (phase == 1) {
                        String[] v = line.split(": ");
                        registers.put(v[0], Integer.valueOf(v[1]));
                    } else if (phase == 2) {
                        String[] v = line.split(" ");
                        Operation o = new Operation(v[0], v[2], v[1], v[4]);
                        operations.add(o);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static class Operation {
        private String first;
        private String second;
        private String operand;
        private String result;

        public Operation(String first, String second, String operand, String result) {
            this.first = first;
            this.second = second;
            this.operand = operand;
            this.result = result;
        }


        public int evaluate(Map<String, Integer> values){
            int res = -1;

            switch (this.operand){
                case "AND" -> res = values.get(first) & values.get(second);
                case "OR" -> res = values.get(first) | values.get(second);
                case "XOR" -> res = values.get(first) ^ values.get(second);
            }

            return res;
        }
    }
}
