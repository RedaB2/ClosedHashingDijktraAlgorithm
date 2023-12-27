Name: Reda Boutayeb
Homework 5 - Closed Hashing - Dijktra's Algorithm

This repository contains two Java programs, GraphShortestPath and WordHasher. These programs are designed to demonstrate
the implementation of graph shortest path finding and hash table operations, respectively.

1. WordHasher

    WordHasher is a Java program that reads a text file and inserts all words found in the file into a hash table using
    a custom hash function. The program then displays information about the hash table, such as the load factor, longest
    empty area, longest cluster, and more.

    Usage:

    Run the program.
    Enter the path to the text file containing the words when prompted.

    Input format:

    The input file should contain a plain text document with words separated by spaces or punctuation marks.
    The program uses a regular expression to extract words from the file, ignoring any non-alphabetic characters
    (except for hyphens and apostrophes, which are allowed within words).

2. GraphShortestPath

    GraphShortestPath is a Java program that reads a graph from a text file and then finds the shortest path between
    two specified nodes using Dijkstra's algorithm. The graph is represented by an adjacency matrix.

    Usage:

    Run the program.
    Enter the path to the text file containing the graph, as well as the start and destination nodes when prompted.

    Input format:

    The input file should contain the graph in the following format:

    The first line contains a single integer, representing the number of nodes in the graph.
    The subsequent lines contain the adjacency matrix, with each value separated by a space. A value of 0 indicates
    that there is no edge between the corresponding nodes.

    4
    0 10 20 0
    10 0 0 50
    20 0 0 20
    0 50 20 0