import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordHasher {
    // Constants for the hash function and the hash table size
    private static final int C = 123;
    private static final int M = 997;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the file path: ");
        String filePath = scanner.nextLine();

        // Read the file content
        String fileContent;
        try {
            fileContent = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return;
        }

        // Initialize the hash table
        String[] hashTable = new String[M];
        // Create a regex pattern to find words
        Pattern wordPattern = Pattern.compile("[-'A-Za-z]+");
        Matcher matcher = wordPattern.matcher(fileContent);

        // Process each word found in the file
        while (matcher.find()) {
            String word = matcher.group();
            int hash = hashFunction(word);
            insertIntoHashTable(hashTable, word, hash);
        }

        // Display the hash table and answer the questions
        displayHashTable(hashTable);
        answerQuestions(hashTable);
    }

    // Hash function implementation
    private static int hashFunction(String word) {
        int h = 0;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            h = (h * C + (int) c) % M;
        }
        return h;
    }

    // Insert a word into the hash table
    private static void insertIntoHashTable(String[] hashTable, String word, int hash) {
        int i = hash;
        while (hashTable[i] != null) {
            if (hashTable[i].equals(word)) {
                return; // Discard duplicates
            }
            i = (i + 1) % M; // Circular list
        }
        hashTable[i] = word;
    }

    // Display the hash table
    private static void displayHashTable(String[] hashTable) {
        for (int i = 0; i < hashTable.length; i++) {
            if (hashTable[i] != null) {
                int hash = hashFunction(hashTable[i]);
                System.out.printf("Hash Address: %03d | Hashed Word: %s | Hash Value: %03d%n", i, hashTable[i], hash);
            }
        }
    }

    // Answer various questions based on the hash table
    private static void answerQuestions(String[] hashTable) {
        int nonEmptyAddresses = countNonEmptyAddresses(hashTable);
        double loadFactor = (double) nonEmptyAddresses / M;
        System.out.printf("a) Non-empty addresses: %d | Load factor: %.2f%n", nonEmptyAddresses, loadFactor);

        int longestEmptyArea = findLongestEmptyArea(hashTable);
        System.out.printf("b) Longest empty area: %d%n", longestEmptyArea);

        int longestCluster = findLongestCluster(hashTable);
        System.out.printf("c) Longest cluster: %d%n", longestCluster);

        int[] maxHashCollisionInfo = findMaxHashCollision(hashTable);
        System.out.printf("d) Hash value with the most collisions: %d | Number of collisions: %d%n", maxHashCollisionInfo[0], maxHashCollisionInfo[1]);

        String[] farthestWordInfo = findFarthestWordDistance(hashTable);
        System.out.printf("e) Farthest word: %s | Distance from actual hash value: %d%n", farthestWordInfo[0], Integer.parseInt(farthestWordInfo[1]));
    }

    // Count the non-empty addresses in the hash table
    private static int countNonEmptyAddresses(String[] hashTable) {
        int count = 0;
        for (String word : hashTable) {
            if (word != null) {
                count++;
            }
        }
        return count;
    }

    // Find the longest continuous empty area in the hash table
    private static int findLongestEmptyArea(String[] hashTable) {
        int longestEmptyArea = 0;
        int currentEmptyArea = 0;

        for (String word : hashTable) {
            if (word == null) {
                currentEmptyArea++;
            } else {
                longestEmptyArea = Math.max(longestEmptyArea, currentEmptyArea);
                currentEmptyArea = 0;
            }
        }

        return longestEmptyArea;
    }

    // Find the longest continuous cluster of non-empty addresses in the hash table
    private static int findLongestCluster(String[] hashTable) {
        int longestCluster = 0;
        int currentCluster = 0;

        for (String word : hashTable) {
            if (word != null) {
                currentCluster++;
            } else {
                longestCluster = Math.max(longestCluster, currentCluster);
                currentCluster = 0;
            }
        }

        return longestCluster;
    }

    // Find the hash value with the most collisions
    private static int[] findMaxHashCollision(String[] hashTable) {
        int[] hashCounts = new int[M];
        int maxCollision = 0;
        int maxCollisionHash = 0;

        for (String word : hashTable) {
            if (word != null) {
                int hash = hashFunction(word);
                hashCounts[hash]++;
                if (hashCounts[hash] > maxCollision) {
                    maxCollision = hashCounts[hash];
                    maxCollisionHash = hash;
                }
            }
        }

        return new int[]{maxCollisionHash, maxCollision};
    }

    // Find the word with the largest distance from its actual hash value
    private static String[] findFarthestWordDistance(String[] hashTable) {
        String farthestWord = null;
        int maxDistance = 0;

        for (int i = 0; i < hashTable.length; i++) {
            if (hashTable[i] != null) {
                int actualHash = hashFunction(hashTable[i]);
                int distance = (i - actualHash + M) % M;
                if (distance > maxDistance) {
                    maxDistance = distance;
                    farthestWord = hashTable[i];
                }
            }
        }

        return new String[]{farthestWord, Integer.toString(maxDistance)};
    }
}
