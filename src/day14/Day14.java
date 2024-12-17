package day14;

import day1.Day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Day14 {



    public static void main(String[] args) {
        var filePath = "/day14.txt";


        List<Game> games = new ArrayList<>();
        Map<Integer, Integer> robots= new HashMap<>();
        readLists(filePath, games);

        System.out.println(games.size());


        long sum = 0;
        for(Game g : games) {


            for (int i = 0; i < 100; i++) {
                if (g.x + g.vx > 100) {
                    g.x = g.x + g.vx - 101;
                } else if (g.x + g.vx < 0) {
                    g.x = g.x + g.vx + 101;
                } else {
                    g.x += g.vx;
                }

                if (g.y + g.vy > 102) {
                    g.y = g.y + g.vy - 103;
                } else if (g.y + g.vy < 0) {
                    g.y = g.y + g.vy + 103;
                } else {
                    g.y += g.vy;
                }

            }

            if(g.x != 50 && g.y != 51){

                if(g.x<50 && g.y<51){
                    if(robots.containsKey(1)){
                        robots.put(1, robots.get(1)+1);
                    } else {
                        robots.put(1, 1);
                    }
                } else if(g.x>50 && g.y<51){
                    if(robots.containsKey(2)){
                        robots.put(2, robots.get(2)+1);
                    } else {
                        robots.put(2, 1);
                    }
                } else if(g.x<50 && g.y>51){
                    if(robots.containsKey(3)){
                        robots.put(3, robots.get(3)+1);
                    } else {
                        robots.put(3, 1);
                    }
                } else {
                    if(robots.containsKey(4)){
                        robots.put(4, robots.get(4)+1);
                    } else {
                        robots.put(4, 1);
                    }
                }



            }
        }

        System.out.println(robots);

        if(robots.size() > 0){
            sum = 1;
        }

        for (Integer i : robots.keySet()){
            sum *= robots.get(i);
        }


        System.out.println(sum);

        int j =1;
        int total = 1;
        for(int i =0; i<102; i++){
            j = j+2;
            total+=2;
        }

        System.out.println(j-2);
        System.out.println(total);
    }



    private static void readLists(String filePath, List<Game> games) {

        String line;
        try (InputStream inputStream = Day1.class.getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            while ((line = reader.readLine()) != null) {
                Game g = new Game();
                String[] data = line.strip().split(" ");

                g.y = Integer.valueOf(data[0].split(",")[1]);
                g.x = Integer.valueOf(data[0].split(",")[0].substring(2));

                g.vy = Integer.valueOf(data[1].split(",")[1]);
                g.vx = Integer.valueOf(data[1].split(",")[0].substring(2));

                games.add(g);
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
