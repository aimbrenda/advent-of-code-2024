package day14;

import day1.Day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day14Part2 {



    public static void main(String[] args) {
        var filePath = "/day14.txt";


        List<Game> games = new ArrayList<>();
        Map<Integer, Integer> robots= new HashMap<>();
        readLists(filePath, games);

        System.out.println(games.size());


        long sum = 0;


            for (int i = 1; i < 6753; i++) {

                HashMap<String, Integer>  map = new HashMap<>();
                HashMap<Long, List<Integer>>  mapOc = new HashMap<>();
                for(long k =0; k< 101; k++){
                    mapOc.put(k,new ArrayList<>());
                }

                for(Game g : games) {
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

                    map.put(g.x + " " + g.y, 1);

                    mapOc.get(g.x).add(Integer.valueOf(String.valueOf(g.x)));

                }

                for (Long l:
                     mapOc.keySet()) {
                    if(mapOc.get(l).size() >32 && i == 6752){
                        System.out.println("Iteration " + i + "\n");

                        for(int n = 0 ; n< 103; n++){
                            for(int m = 0 ; m< 101; m++){
                                if(map.containsKey(m + " " +n)){
                                    System.out.print("#");
                                } else {
                                    System.out.print(".");
                                }
                            }
                            System.out.print("\n");
                        }
                        break;
                    }


                }



            }

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
