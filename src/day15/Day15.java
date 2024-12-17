package day15;

import day1.Day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day15 {



    public static void main(String[] args) {
        var filePath = "/day15.txt";

        List<List<String>> wharehouse = new ArrayList<>();
        List<String> moves = new ArrayList<>();


        readLists(filePath, wharehouse, moves);

        System.out.println(wharehouse);
        System.out.println(moves);

        int x=24;
        int y=26;

        System.out.println(wharehouse.get(y).get(x));
        for(String s : moves){
            System.out.println(s);
            if(move(x,y, s, wharehouse)){
                switch (s){
                    case "^":
                        if(y>0)
                            y--;
                        break;
                    case ">":
                        if(x<wharehouse.get(0).size()-1)
                            x++;
                        break;
                    case "v":
                        if(y<wharehouse.size()-1)
                            y++;
                        break;
                    case "<":
                        if(x>0)
                            x--;
                        break;
                }
            }
        }

        long sum = 0;
        for(int i =0;i<wharehouse.size();i++){
            for(int j=0;j<wharehouse.get(0).size();j++){
                System.out.print(wharehouse.get(i).get(j));
                if(wharehouse.get(i).get(j).equals("O")){
                    sum += 100*i + j;
                }
            }
            System.out.print("\n");
        }


        System.out.println(sum);
    }


    private static boolean move(int x, int y, String direction, List<List<String>> w){
        int originalX = x;
        int originalY = y;
        switch (direction){
            case "^":
                if(y>0)
                    y--;
                break;
            case ">":
                if(x<w.get(0).size()-1)
                    x++;
                break;
            case "v":
                if(y<w.size()-1)
                    y++;
                break;
            case "<":
                if(x>0)
                    x--;
                break;
        }

        System.out.println(x + " " + y);
        String v = w.get(y).get(x);
        if(v.equals("#")){
            return false;
        } else if(v.equals(".") || move(x, y, direction, w)) {
            w.get(y).set(x, w.get(originalY).get(originalX));
            w.get(originalY).set(originalX, ".");
            return true;
        }

        return false;
    }



    private static void readLists(String filePath, List<List<String>> w, List<String> moves) {

        String line;
        try (InputStream inputStream = Day1.class.getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            while ((line = reader.readLine()) != null) {
                if(line.isBlank()){
                    break;
                }

                List<String> values = line.trim().chars().mapToObj(c -> String.valueOf((char) c)).collect(Collectors.toList());
                w.add(values);
            }

            while ((line = reader.readLine()) != null) {
                List<String> values = line.trim().chars().mapToObj(c -> String.valueOf((char) c)).collect(Collectors.toList());
                moves.addAll(values);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    private static class Game {

        private long vx;
        private long vy;
        private long x;
        private long y;


        public Game() {
        }

        @Override
        public String toString() {
            return "Game{" +
                    "vx=" + vx +
                    ", vy=" + vy +
                    ", x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
}
