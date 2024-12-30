package day24;

import day1.Day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Day24Part2 {



    public static void main(String[] args) {
        var filePath = "/day24.txt";

        Map<String,Integer> registers = new HashMap<>();
        LinkedList<Operation> operations = new LinkedList<>();

        readLists(filePath, registers, operations);


        List<String> res = swap(operations);
        Collections.sort(res);

        System.out.println(res);


    }

    private static String searchResultFrom(String a, String b, String operator, LinkedList<Operation> operations) {
        for (Operation op : operations) {
            if(((op.first.equals(a) && op.second.equals(b)) ||
                    (op.first.equals(b) && op.second.equals(a)))
                    && op.operand.equals(operator)){
                return op.result;
            }
        }
        return null;
    }


    private static List<String> swap(LinkedList<Operation> operations) {
        List<String> swapped = new ArrayList<>();
        String c0 = null;
        for (int i = 0; i < 45; i++) {
            String n = String.format("%02d", i);
            String xorResult;
            String andResult;
            String dependencyAndResult;
            String dependencyXorResult = null;
            String dependencyOrResult = null;

            xorResult = searchResultFrom("x" + n, "y" + n, "XOR", operations);
            andResult = searchResultFrom("x" + n, "y" + n, "AND", operations);

            if (c0 != null) {
                dependencyAndResult = searchResultFrom(c0, xorResult, "AND", operations);
                if (dependencyAndResult == null) {
                    String temp = andResult;
                    andResult = xorResult;
                    xorResult = temp;
                    swapped.add(xorResult);
                    swapped.add(andResult);
                    dependencyAndResult = searchResultFrom(c0, xorResult, "AND", operations);
                }

                dependencyXorResult = searchResultFrom(c0, xorResult, "XOR", operations);

                if (xorResult.startsWith("z")) {
                    String temp = xorResult;
                    xorResult = dependencyXorResult;
                    dependencyXorResult = temp;
                    swapped.add(xorResult);
                    swapped.add(dependencyXorResult);
                }

                if (andResult.startsWith("z")) {
                    String temp = andResult;
                    andResult = dependencyXorResult;
                    dependencyXorResult = temp;
                    swapped.add(andResult);
                    swapped.add(dependencyXorResult);
                }

                if (dependencyAndResult.startsWith("z")) {
                    String temp = dependencyAndResult;
                    dependencyAndResult = dependencyXorResult;
                    dependencyXorResult = temp;
                    swapped.add(dependencyAndResult);
                    swapped.add(dependencyXorResult);
                }

                dependencyOrResult = searchResultFrom(dependencyAndResult, andResult, "OR", operations);
            }

            if (dependencyOrResult != null && dependencyOrResult.startsWith("z") && !dependencyOrResult.equals("z45")) {
                String temp = dependencyOrResult;
                dependencyOrResult = dependencyXorResult;
                dependencyXorResult = temp;
                swapped.add(dependencyOrResult);
                swapped.add(dependencyXorResult);
            }

            if (c0 != null) {
                c0 = dependencyOrResult;
            } else {
                c0 = andResult;
            }
        }

        return swapped;
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
