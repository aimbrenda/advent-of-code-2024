package day8;

import day1.Day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Day8Part2 {

    //1214
    //1215
    //1235


    public static void main(String[] args) {
        var filePath = "/day8.txt";


        Grid g = readLists(filePath);


        System.out.println(g.coordinates);
        Map<String, List<Coordinate>> groups = g.groupByValue();

        List<Coordinate> antinodes = new ArrayList<>();

        for(List<Coordinate> group : groups.values()){
            antinodes.addAll(findAntinodes(group, g.maxRow, g.maxCol));
        }


        System.out.println(new HashSet<>(antinodes).size());
    }



    private static Grid readLists(String filePath) {
        Map<Coordinate, String> m = new HashMap<>();
        int maxRow = 0;
        int maxCol = 0;
        try (InputStream inputStream = Day1.class.getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;


            while ((line = reader.readLine()) != null) {
                maxRow++;
                maxCol = line.length();
                List<String> values = line.trim().chars().mapToObj(c -> String.valueOf((char) c)).collect(Collectors.toList());
                System.out.println(values);

                for(int i =0; i< values.size(); i++){
                    if(!values.get(i).equals(".")){
                        m.put(new Coordinate(maxRow, i+1), values.get(i));
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Grid(m, maxCol, maxRow);
    }

    public static List<Coordinate> findAntinodes(List<Coordinate> nodes, int maxRow, int maxCol){

        List<Coordinate> antinodes = new ArrayList<>();

        for(int i=0; i< nodes.size()-1; i++){
            Coordinate a = nodes.get(i);
            for(int j = i+1; j<nodes.size(); j++){
                Coordinate b = nodes.get(j);

                Coordinate diff = b.subtract(a);



/*                antinodes.add(b.subtract(diff));
                antinodes.add(a.add(diff));*/

                for(int k=0; k<= (a.row)*10; k++){
                    antinodes.add(new Coordinate(a.row -  k*diff.row,a.col - k*diff.col));
                }


                for(int k=0; k<=((maxRow - b.row)+1)*10; k++){
                    antinodes.add(new Coordinate(b.row + k*diff.row,b.col +k*diff.col));
                }


            }
        }

        return  antinodes.stream().filter(c -> !c.isOutOfBound(maxRow, maxCol)).distinct().collect(Collectors.toList());

    }

    private static class Grid {
        Map<Coordinate, String> coordinates;
        int maxCol;
        int maxRow;

        public Grid(Map<Coordinate, String> coordinates, int maxCol, int maxRow) {
            this.coordinates = coordinates;
            this.maxCol = maxCol;
            this.maxRow = maxRow;
        }

        public Map<String, List<Coordinate>> groupByValue(){
            Map<String, List<Coordinate>> groups = new HashMap<>();

            for(Map.Entry<Coordinate, String>  entry:  coordinates.entrySet()){
                groups.computeIfAbsent(entry.getValue(), k -> new ArrayList<>()).add(entry.getKey());
            }

            return groups;
        }


    }

    private static class Coordinate {
        int row, col;

        public Coordinate(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public String toString() {
            return "Coordinate{" +
                    "row=" + row +
                    ", col=" + col +
                    '}';
        }

        public Coordinate add(Coordinate other){
            return new Coordinate(this.row +other.row, this.col+other.col);
        }

        public Coordinate subtract(Coordinate other){
            return new Coordinate(this.row - other.row, this.col - other.col);
        }

        public boolean isOutOfBound(int maxRow, int maxCol){
            return row<=0 || col <=0 || row >maxRow || col >maxCol;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Coordinate that = (Coordinate) o;

            if (row != that.row) return false;
            return col == that.col;
        }

        @Override
        public int hashCode() {
            int result = row;
            result = 31 * result + col;
            return result;
        }
    }

}
