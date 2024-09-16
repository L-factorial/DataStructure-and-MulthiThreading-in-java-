package com.lfactorial.concurrency.multithreadedbfs;

import java.util.*;

public class ParallelBfs {
    UndirectedGraph graph;
    Set<Integer> visited;
    Queue<Integer> queue;
    Object lock;
    Thread[] threads;
    int totalThreads;
    boolean done;
    Map<Long, Integer> threadTasks;
    Set<Long> waitingThreads;
    Map<Long, List<Integer>> threadTaskRecords;

    public ParallelBfs(UndirectedGraph graph){
        this.graph = graph;
    }

    private void init(){
        visited = new HashSet<>();
        this.queue = new LinkedList<>();
        this.queue.add(0);
        this.visited.add(0);
        this.lock = new Object();
        this.done = false;
        this.threadTasks = new HashMap<>();
        this.waitingThreads = new HashSet<>();
        this.totalThreads = Runtime.getRuntime().availableProcessors();
        this.threads = new Thread[totalThreads];
        this.threadTaskRecords = new HashMap<>();
    }

    private void taskForThread() {
        while (!done) {
            lockPhase();
            if(done) {
                break;
            }

            // Do some task here ....
            for(int i = 0; i < 1000000; ++i) {
                Math.pow(5.6, 200.5);
            }

            unlockPhase();
        }
    }

    private void lockPhase() {
        synchronized (lock) {
            while (queue.isEmpty()) {
                try {
                    waitingThreads.add(Thread.currentThread().getId());
                    if(waitingThreads.size() == totalThreads){
                        done = true;
                        lock.notifyAll();
                    }
                    if(done) {
                        return;
                    }
                    lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            int u = queue.poll();
            threadTasks.put(Thread.currentThread().getId(), u);
            threadTaskRecords.putIfAbsent(Thread.currentThread().getId(), new ArrayList<>());
            threadTaskRecords.get(Thread.currentThread().getId()).add(u);
        }
    }

    private void unlockPhase() {
        synchronized (lock) {
            for(Integer v : graph.adj(threadTasks.get(Thread.currentThread().getId()))) {
                if(visited.contains(v)) { continue; }
                visited.add(v);
                queue.add(v);
                lock.notifyAll();
            }
        }
    }

    public void runParallelBfs(){
        this.init();
        for(int i=0; i<totalThreads; i++){
            threads[i] = new Thread(this::taskForThread);
            threads[i].start();
        }
        for(int i=0; i<totalThreads; i++){
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void runSingleThreadBfs(){
        this.init();
        while(!queue.isEmpty()) {
            int u = queue.poll();
            for(int v : graph.adj(u)) {
                if(visited.contains(v)) { continue; }
                visited.add(v);
                queue.add(v);
            }
        }

    }

    public static void main(String[] args) {
        UndirectedGraph undirectedGraph = UndirectedGraph.randomGraph(100000, 23);
        ParallelBfs parallelBfs = new ParallelBfs(undirectedGraph);
        //System.out.println("Graph : " + undirectedGraph);
        long start = System.currentTimeMillis();
        parallelBfs.runParallelBfs();
        long end = System.currentTimeMillis();

        long start1 = System.currentTimeMillis();
        parallelBfs.runSingleThreadBfs();
        long end1 = System.currentTimeMillis();

        long timeMultiThread = end - start;
        long timeSingleThread = end1 - start1;

        System.out.println("Single Thread : " + timeSingleThread);
        System.out.println("Multi Thread : " + timeMultiThread);
    }
}
