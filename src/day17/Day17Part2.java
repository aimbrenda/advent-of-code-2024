package day17;

import day1.Day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day17Part2 {



    public static void main(String[] args) {
        var filePath = "/day17.txt";

       final Machine m = new Machine();

       readLists(filePath, m);

        List<Long> reversedProgram = new ArrayList<>(m.program);
        Collections.reverse(reversedProgram);

        List<Long> candidates = List.of(0L);

        for (Long instruction : reversedProgram) {
            List<Long> newCandidates = new ArrayList<>();

            for (Long candidate : candidates) {
                long shifted = candidate << 3;

                for (long attempt = shifted; attempt <= shifted + 8; attempt++) {
                    Machine copy = new Machine();
                    copy.B=m.B;
                    copy.C=m.C;
                    copy.program = new ArrayList<>(m.program);

                    copy.A = attempt;

                    copy.runProgram();
                    if (copy.output.get(0).equals(instruction)) {
                        newCandidates.add(attempt);
                    }
                }
            }
            candidates = newCandidates;
        }

        System.out.println(candidates);



           /*LongStream.range(Long.valueOf(String.valueOf(Integer.MAX_VALUE))+1, Long.MAX_VALUE).parallel().forEach( l->{
           m.A=l;
           m.B=0;
           m.C=0;
           //Collections.reverse(m.program);

           m.runProgram();
           //m.output.stream().forEach(v -> System.out.print(v+","));
           if(m.output.stream().map(a -> Integer.valueOf(String.valueOf(a))).collect(Collectors.toList()).equals(m.program)){
               System.out.println(m);
           }
       });*/

    }




    private static List<List<String>> readLists(String filePath, Machine m) {
        try (InputStream inputStream = Day1.class.getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            int lineN = 0;
            List<List<String>> input = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                System.out.println(line);

                if(lineN==0){
                    m.A = Integer.valueOf(line.substring(12));
                } else if(lineN==1){
                    m.B = Integer.valueOf(line.substring(12));
                } else if(lineN==2){
                    m.C = Integer.valueOf(line.substring(12));
                } else if(lineN==4){
                    m.program =  Arrays.stream(line.trim().substring(9).split(",")).map(Long::valueOf).collect(Collectors.toList());
                }

                lineN++;
            }

            return input;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }



    private static class Machine {

        List<Long> program = new ArrayList<>();
        List<Long> output = new ArrayList<>();

        long mask = 7;
        long A;
        long B;
        long C;

        @Override
        public String toString() {
            return "Machine{" +
                    "A=" + A +
                    ", B=" + B +
                    ", C=" + C +
                    '}';
        }

        private long getOperand(long operand){
            if(operand>3 && operand < 7){
                switch (Integer.valueOf(String.valueOf(operand))){
                    case 4:
                        return  A;
                    case 5:
                        return B;
                    case 6:
                        return C;
                }
            }

            return operand;

        }

        private long runOperation(long instrIndex) {
            long operand = program.get(Integer.valueOf(String.valueOf(instrIndex))+1);
            long instr = program.get(Integer.valueOf(String.valueOf(instrIndex)));



            switch (Integer.valueOf(String.valueOf(instr))) {
                case 0:
                     A = (long)(A/Math.pow(2, getOperand(operand)));
                    break;
                case 1:
                    B = B ^ operand;
                    break;
                case 2:
                    B = (getOperand(operand)%8) & mask;
                    break;
                case 3:
                    if(A != 0){
                        return operand;
                    }
                    break;
                case 4:
                    B = B ^ C;
                    break;
                case 5:
                    output.add(getOperand(operand) % 8);
                    break;
                case 6:
                    B = (long)(A/Math.pow(2, getOperand(operand)));
                    break;
                case 7:
                    C = (long)(A/Math.pow(2, getOperand(operand)));
                    break;
            }

            return instrIndex+2;
        }


        public void runProgram(){
            long i = 0;
            while (i < program.size() && output.size()<program.size()) {
                i = runOperation(i);
                //System.out.println(this);
            }
        }
    }
}
