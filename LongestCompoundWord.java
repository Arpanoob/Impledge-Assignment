import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class LongestCompoundWord {


        public static void main(String[] args) throws FileNotFoundException {
            long startTime = System.currentTimeMillis(); // Record the start time

            File inputFile =  new File("words for problem.txt");

            Trie trie = new Trie();
            LinkedList<Pair<String>> queue = new LinkedList<Pair<String>>();
            HashSet<String> compoundWords = new HashSet<String>();

            Scanner fileScanner = new Scanner(inputFile);

            String word;
            List<Integer> suffixIndices;

            while (fileScanner.hasNext()) {
                word = fileScanner.next();
                suffixIndices = trie.getSuffixesStartIndices(word);

                for (int index : suffixIndices) {
                    if (index >= word.length())
                        break;
                    queue.add(new Pair<String>(word, word.substring(index)));
                }

                trie.insert(word);
            }

            Pair<String> pair;
            int maxLength = 0;
            String longestCompound = "";
            String secondLongestCompound = "";

            while (!queue.isEmpty()) {
                pair = queue.removeFirst();
                word = pair.second();
                suffixIndices = trie.getSuffixesStartIndices(word);

                if (suffixIndices.isEmpty()) {
                    continue;
                }

                for (int index : suffixIndices) {
                    if (index > word.length()) {
                        break;
                    }

                    if (index == word.length()) {
                        if (pair.first().length() > maxLength) {
                            secondLongestCompound = longestCompound;
                            maxLength = pair.first().length();
                            longestCompound = pair.first();
                        }

                        compoundWords.add(pair.first());
                    } else {
                        queue.add(new Pair<String>(pair.first(), word.substring(index)));
                    }
                }
            }

            long endTime = System.currentTimeMillis(); // Record the end time
            long executionTime = endTime - startTime; // Calculate execution time in milliseconds

            System.out.println("Longest Compound Word: " + longestCompound);
            System.out.println("Second Longest Compound Word: " + secondLongestCompound);
            System.out.println("Time taken to process the input file: " + executionTime + " milliseconds");
        }
    }
