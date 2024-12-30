package day15;

import day1.Day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day15Part2 {



    public static void main(String[] args) {
        var filePath = "/day15.txt";

        List<List<String>> smallW = new ArrayList<>();
        List<List<String>> wharehouse = new ArrayList<>();
        List<String> moves = new ArrayList<>();


        readLists(filePath, smallW, moves);

        for(int i =0;i<smallW.size();i++){
            wharehouse.add(new ArrayList<>());
            for(int j=0;j<smallW.get(0).size();j++){
                if(smallW.get(i).get(j).equals("#")){
                    wharehouse.get(i).add("#");
                    wharehouse.get(i).add("#");
                } else if(smallW.get(i).get(j).equals("O")){
                    wharehouse.get(i).add("[");
                    wharehouse.get(i).add("]");
                } else if(smallW.get(i).get(j).equals(".")){
                    wharehouse.get(i).add(".");
                    wharehouse.get(i).add(".");
                } else if(smallW.get(i).get(j).equals("@")){
                    wharehouse.get(i).add("@");
                    wharehouse.get(i).add(".");
                }
            }
        }

        System.out.println(wharehouse);
        System.out.println(moves);
        int x=8;
        int y=4;

        for(int i =0;i<wharehouse.size();i++){
            for(int j=0;j<wharehouse.get(0).size();j++){
                System.out.print(wharehouse.get(i).get(j));
                if(wharehouse.get(i).get(j).equals("@")){
                    x = j;
                    y = i;
                }
            }
            System.out.print("\n");
        }



        System.out.println(wharehouse.get(y).get(x));
        for(String s : moves){
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
            /*for(int i =0;i<wharehouse.size();i++){
                for(int j=0;j<wharehouse.get(0).size();j++){
                    System.out.print(wharehouse.get(i).get(j));
                }
                System.out.print("\n");
            }*/
        }

        long sum = 0;
        for(int i =0;i<wharehouse.size();i++){
            for(int j=0;j<wharehouse.get(0).size();j++){
                System.out.print(wharehouse.get(i).get(j));
                if(wharehouse.get(i).get(j).equals("[")){
                    sum += 100*i + j;
                }
            }
            System.out.print("\n");
        }


        System.out.println(sum);
    }


    private static boolean move(int x, int y, String direction, List<List<String>> w){
        for(int i =0;i<w.size();i++){
                for(int j=0;j<w.get(0).size();j++){
                    System.out.print(w.get(i).get(j));
                }
                System.out.print("\n");
        }
        System.out.println("Move " + direction);
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

        String v = w.get(y).get(x);
        if(v.equals("#")){
            return false;
        } else if((v.equals("[") || v.equals("]")) && (direction.equals("^") | direction.equals("v")) ){
            if(canMoveBox(x,y,direction,w,true)){
                moveBox(x,y,direction,w,true);
                w.get(y).set(x, w.get(originalY).get(originalX));
                w.get(originalY).set(originalX, ".");
                return true;
            } else {
                return false;
            }
        } else if(v.equals(".") || move(x, y, direction, w)) {
            w.get(y).set(x, w.get(originalY).get(originalX));
            w.get(originalY).set(originalX, ".");
            return true;
        }

        return false;
    }


    private static boolean canMoveBox(int x, int y, String direction, List<List<String>> w, boolean checkbrother){
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



        boolean canOtherside = false;
        if(w.get(originalY).get(originalX).equals("[") && checkbrother){
            canOtherside = canMoveBox(originalX+1,originalY, direction,w, false);
        } else if(w.get(originalY).get(originalX).equals("]") && checkbrother){
            canOtherside = canMoveBox(originalX-1,originalY, direction,w, false);
        }

        if(checkbrother && !canOtherside){
            return false;
        } else if(w.get(y).get(x).equals("#")){
            return false;
        } else if(w.get(y).get(x).equals("[") || w.get(y).get(x).equals("]") ){
            return canMoveBox(x,y,direction,w,true);
        }  else if(canOtherside || !checkbrother){
            return true;
        }

        return false;
    }


    private static void moveBox(int x, int y, String direction, List<List<String>> w, boolean checkbrother){
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

        if(w.get(originalY).get(originalX).equals("[") && checkbrother){
            moveBox(originalX+1,originalY, direction,w, false);
        } else if(w.get(originalY).get(originalX).equals("]") && checkbrother){
            moveBox(originalX-1,originalY, direction,w, false);
        }

        if(w.get(y).get(x).equals("[") || w.get(y).get(x).equals("]") ){
            moveBox(x,y,direction,w,true);
        } else if(w.get(y).get(x).equals("#")){
            return;
        }
        w.get(y).set(x, w.get(originalY).get(originalX));
        w.get(originalY).set(originalX, ".");
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
