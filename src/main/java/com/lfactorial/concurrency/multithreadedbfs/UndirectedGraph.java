package com.lfactorial.concurrency.multithreadedbfs;

import java.util.*;

public class UndirectedGraph {
    Map<Integer, List<Integer>> adjacencyList;

    public UndirectedGraph() {
        this.adjacencyList = new HashMap<>();
    }

    public void add(Integer v1, Integer v2) {
        adjacencyList.putIfAbsent(v1, new ArrayList<>());
        adjacencyList.putIfAbsent(v2, new ArrayList<>());
        adjacencyList.get(v1).add(v2);
        adjacencyList.get(v2).add(v1);
    }

    public Set<Integer> vertices(){
        return adjacencyList.keySet();
    }
    public List<Integer> adj(Integer v){
        return adjacencyList.get(v);
    }
    @Override
    public String toString() {
        return adjacencyList.toString();
    }

    public static UndirectedGraph randomGraph(int totalVertices, int totalEdges) {
        UndirectedGraph undirectedGraph = new UndirectedGraph();
        for (int i = 0; i < totalVertices; i++) {
            for(int j = 0; j < totalEdges; j++) {
                undirectedGraph.add(i, (i+j*2)%totalVertices);
            }
        }
        return undirectedGraph;
    }
}
