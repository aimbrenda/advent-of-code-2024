package day17;

import day1.Day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Day17 {



    public static void main(String[] args) {
        var filePath = "/day17.txt";

       Machine m = new Machine();

       readLists(filePath, m);

       m.A=8;
       m.B=2;
       m.C=0;
       //Collections.reverse(m.program);

       m.runProgram();
       m.output.stream().forEach(v -> System.out.print(v+","));
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
                    m.program =  Arrays.stream(line.trim().substring(9).split(",")).map(Integer::valueOf).collect(Collectors.toList());
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

        List<Integer> program = new ArrayList<>();
        List<Integer> output = new ArrayList<>();

        int mask = 7;
        int A;
        int B;
        int C;

        @Override
        public String toString() {
            return "Machine{" +
                    "A=" + A +
                    ", B=" + B +
                    ", C=" + C +
                    '}';
        }

        private int getOperand(int operand){
            if(operand>3 && operand < 7){
                switch (operand){
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

        private int runOperation(int instrIndex) {
            int operand = program.get(instrIndex+1);
            int instr = program.get(instrIndex);



            switch (instr) {
                case 0:
                     A = (int)(A/Math.pow(2, getOperand(operand)));
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
                    B = (int)(A/Math.pow(2, getOperand(operand)));
                    break;
                case 7:
                    C = (int)(A/Math.pow(2, getOperand(operand)));
                    break;
            }

            return instrIndex+2;
        }


        public void runProgram(){
            int i = 0;
            while (i < program.size()) {
                i = runOperation(i);
                System.out.println(this);
            }
        }
    }
}
