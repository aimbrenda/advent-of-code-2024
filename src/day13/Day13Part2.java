package day13;

import day1.Day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class Day13Part2 {



    public static void main(String[] args) {
        var filePath = "/day13.txt";

        List<Game> games = new LinkedList<>();

        readLists(filePath, games);

        System.out.println(games);

        long sum = 0;

        int n =1;
        for(Game g : games) {
            double i = (g.y*g.bX - g.x*g.bY) / (g.aY*g.bX - g.bY*g.aX);

            if(i>=0){
                double b = (g.x-i*g.aX)/g.bX;
                if(b>=0 && b%1 == 0){
                    if(g.x == (i*g.aX+b*g.bX) &&  g.y == (i*g.aY+b*g.bY)){
                        System.out.println(n);
                        sum+= 3*i+b;
                    }
                }
            }

            n++;

            /*if(a%1 == 0){
                double b = (g.x-a*g.aX)/g.bX;

                if(b%1 == 0 ){
                    System.out.println(a + " " +b);

                    if(a <= 100 && b<= 100)
                        sum+= 3*a+b;


                }
            }*/
        }

        System.out.println(sum);
    }



    private static void readLists(String filePath, List<Game> games) {

        String line;
        try (InputStream inputStream = Day1.class.getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            while ((line = reader.readLine()) != null) {
                if(line.contains("Button A:")){
                    Game g = new Game();
                    String s = line.substring(10);
                    g.aX = Integer.valueOf(s.split(",")[0].split("\\+")[1]);
                    g.aY = Integer.valueOf(s.split(",")[1].trim().split("\\+")[1]);
                    games.add(g);
                } else if (line.contains("Button B:")){
                    Game g = games.get(games.size()-1);
                    String s = line.substring(10);
                    g.bX = Integer.valueOf(s.split(",")[0].split("\\+")[1]);
                    g.bY = Integer.valueOf(s.split(",")[1].trim().split("\\+")[1]);
                } else if(line.contains("Prize: ")){
                    Game g = games.get(games.size()-1);
                    String s = line.substring(7);
                    g.x = Integer.valueOf(s.split(",")[0].split("=")[1]) + 10000000000000L;
                    g.y = Integer.valueOf(s.split(",")[1].trim().split("=")[1]) + 10000000000000L;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    private static class Game {
        private long aX;
        private long aY;
        private long bX;
        private long bY;
        private long x;
        private long y;

        public Game(int aX, int aY, int bX, int bY, int x, int y) {
            this.aX = aX;
            this.aY = aY;
            this.bX = bX;
            this.bY = bY;
            this.x = x;
            this.y = y;
        }

        public Game() {
        }

        @Override
        public String toString() {
            return "Game{" +
                    "aX=" + aX +
                    ", aY=" + aY +
                    ", bX=" + bX +
                    ", bY=" + bY +
                    ", x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
}
