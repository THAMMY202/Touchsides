package com.tshabalala;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

public class BookWordFinder {

    public static void main(String[] args) {

        // TODO code application logic here

        String book = "";
        HashMap<String, Integer> map = new HashMap<>();
        HashMap<String, Integer> map7letter = new HashMap<>();
        HashMap<String, Integer> mapScrabblescores = new HashMap<>();

        String frequentWord = "";
        String longWord7Chareter = "";
        String scrabble = "";

        int frequentWordCount = 0;
        int l7word = 0;
        int scrabbleWordScore = 0;

        Scanner input = new Scanner(System.in);
        String FILENAME;

        System.out.println("Please enter a file path ending with .txt  and press enter to start:");
        FILENAME = input.nextLine();

        try {

            //read the book line by line
            Stream<String> stream = Files.lines(Paths.get(FILENAME));
            String[] stringArray = stream.toArray(size -> new String[size]);
            for (String d : stringArray) {
                book += d;
            }


            String[] stringArraySplit = book.replaceAll("[^a-zA-Z ]", " ").toLowerCase().split("\\s+");

            for (String word : stringArraySplit) {

                Integer n = map.get(word);
                n = (n == null) ? 1 : ++n;
                map.put(word, n);

                if (word.length() == 7) {
                    map7letter.put(word, n);
                }

                // calculate and store the scores
                if (scrabbleWordScore(word) > 0) {
                    mapScrabblescores.put(word, scrabbleWordScore(word));
                }
            }

            Object[] objWords = map.entrySet().toArray();
            Arrays.sort(objWords, new Comparator() {
                public int compare(Object o1, Object o2) {

                    return ((Map.Entry<String, Integer>) o2).getValue()
                            .compareTo(((Map.Entry<String, Integer>) o1).getValue());
                }
            });

            for (Object e : objWords) {

                if (((Map.Entry<String, Integer>) e).getValue() > frequentWordCount) {

                    frequentWordCount = ((Map.Entry<String, Integer>) e).getValue();
                    frequentWord = ((Map.Entry<String, Integer>) e).getKey();

                }
            }


            Object[] objTo7letter = objWords;
            for (Object word7letter : objTo7letter) {
                String temp = ((Map.Entry<String, Integer>) word7letter).getKey();

                if (temp.length() == 7) {

                    Integer p = map7letter.get(temp);
                    p = (p == null) ? 1 : ++p;
                    map7letter.put(temp, p);

                }

            }

            Object[] sevenLetterSort = map7letter.entrySet().toArray();
            Arrays.sort(sevenLetterSort, new Comparator() {
                public int compare(Object o1, Object o2) {
                    return ((Map.Entry<String, Integer>) o2).getValue()
                            .compareTo(((Map.Entry<String, Integer>) o1).getValue());
                }
            });
            for (Object sevenLetter : sevenLetterSort) {
                if (((Map.Entry<String, Integer>) sevenLetter).getValue() > l7word) {
                    l7word = ((Map.Entry<String, Integer>) sevenLetter).getValue();
                    longWord7Chareter = ((Map.Entry<String, Integer>) sevenLetter).getKey();
                }
            }

            /// sort scrabble scores and get the highest scoring word
            Object[] scrabbleScoreSort = mapScrabblescores.entrySet().toArray();
            Arrays.sort(scrabbleScoreSort, new Comparator() {
                public int compare(Object o1, Object o2) {
                    return ((Map.Entry<String, Integer>) o2).getValue()
                            .compareTo(((Map.Entry<String, Integer>) o1).getValue());
                }
            });

            for (Object sword : scrabbleScoreSort) {

                if (((Map.Entry<String, Integer>) sword).getValue() > scrabbleWordScore) {
                    scrabbleWordScore = ((Map.Entry<String, Integer>) sword).getValue();
                    scrabble = ((Map.Entry<String, Integer>) sword).getKey();
                }

            }


            System.out.println("Most frequent word: " + frequentWord + " occurred " + frequentWordCount + " times");

            System.out.println("Most frequent 7-character word: " + longWord7Chareter + " occurred " + l7word + " times");

            System.out.println("Highest scoring word (s) : " + scrabble + " with a score of " + scrabbleWordScore);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("Exception " + e.toString());
            //e.printStackTrace();
        }

    }

    static int scrabbleWordScore(String scrabbleWord) {
        int score = 0;
        for (int i = 0; i < scrabbleWord.length(); i++) {
            char calculatedLetter = scrabbleWord.toUpperCase().charAt(i);
            switch (calculatedLetter) {
                case 'A':
                case 'E':
                case 'I':
                case 'L':
                case 'N':
                case 'O':
                case 'R':
                case 'S':
                case 'T':
                case 'U': //Jesus this is fugly
                    score += 1;
                    break;
                case 'D':
                case 'G':
                    score += 2;
                    break;
                case 'B':
                case 'C':
                case 'M':
                case 'P':
                    score += 3;
                    break;
                case 'F':
                case 'H':
                case 'V':
                case 'W':
                case 'Y':
                    score += 4;
                    break;
                case 'K':
                    score += 5;
                    break;
                case 'J':
                case 'X':
                    score += 8;
                    break;
                case 'Q':
                case 'Z':
                    score += 10;
                    break;
                default:
                    break;
            }
        }
        return score;
    }

}
