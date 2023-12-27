// Import necessary libraries
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

// Main class for the shortest path problem
public class GraphShortestPath {
    public static void main(String[] args) {
        // Create a scanner object for user input
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the graph text file path: ");
        String graphFilePath = scanner.nextLine();

        Graph graph;
        try {
            // Attempt to read the graph from the given file path
            graph = Graph.fromFile(graphFilePath);
        } catch (IOException e) {
            // Print an error message and exit if there was a problem reading the file
            System.err.println("Error reading graph file: " + e.getMessage());
            return;
        }

        // Prompt the user for the start and destination nodes
        System.out.print("Enter the start node: ");
        int startNode = scanner.nextInt();
        System.out.print("Enter the destination node: ");
        int destinationNode = scanner.nextInt();

        // Find the shortest path between the given nodes using Dijkstra's algorithm
        graph.findShortestPath(startNode, destinationNode);
    }
}

// Class representing a graph with an adjacency matrix
class Graph {
    private int[][] adjacencyMatrix;

    // Private constructor that initializes the adjacency matrix
    private Graph(int[][] adjacencyMatrix) {
        this.adjacencyMatrix = adjacencyMatrix;
    }

    // Static method to create a Graph object from a file
    public static Graph fromFile(String filePath) throws IOException {
        // Use a BufferedReader to read the file
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Read the size of the graph from the first line
            int size = Integer.parseInt(reader.readLine());
            int[][] adjacencyMatrix = new int[size][size];

            // Read the adjacency matrix from the remaining lines
            for (int i = 0; i < size; i++) {
                String[] parts = reader.readLine().split(" ");
                int j = 0;
                // Parse integers from the line, ignoring empty strings
                for (String part : parts) {
                    if (!part.isEmpty()) {
                        adjacencyMatrix[i][j++] = Integer.parseInt(part);
                    }
                }
            }

            // Return a new Graph object with the parsed adjacency matrix
            return new Graph(adjacencyMatrix);
        }
    }

    // Method to find the shortest path between two nodes using Dijkstra's algorithm
    public void findShortestPath(int startNode, int destinationNode) {
        int n = adjacencyMatrix.length;
        int[] distances = new int[n];
        int[] previous = new int[n];
        boolean[] visited = new boolean[n];
        // Initialize distances to max value and previous nodes to -1
        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(previous, -1);
        distances[startNode] = 0;

        // Create a priority queue to store nodes and their distances
        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        queue.offer(new int[]{startNode, 0});

        // Process nodes in the queue
        while (!queue.isEmpty()) {
            int currentNode = queue.poll()[0];
            if (visited[currentNode]) {
                continue;
            }
            visited[currentNode] = true;

            // Iterate over neighbors and update their distances if necessary
            for (int neighbor = 0; neighbor < n; neighbor++) {
                if (adjacencyMatrix[currentNode][neighbor] == 0) {
                    continue;
                }
                int newDistance = distances[currentNode] + adjacencyMatrix[currentNode][neighbor];
                if (newDistance < distances[neighbor]) {
                    distances[neighbor] = newDistance;
                    previous[neighbor] = currentNode;
                    queue.offer(new int[]{neighbor, newDistance});
                }
            }
        }
        // Print the shortest path length and the actual path
        System.out.printf("Shortest path length: %d%n", distances[destinationNode]);
        System.out.print("Path: ");
        printPath(previous, destinationNode);
        System.out.println();
    }

    // Recursive method to print the path from the source to the given node
    private void printPath(int[] previous, int node) {
        if (previous[node] != -1) {
            printPath(previous, previous[node]);
            System.out.print(" -> ");
        }
        System.out.print(node);
    }
}
