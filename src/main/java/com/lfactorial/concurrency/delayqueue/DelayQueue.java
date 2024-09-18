package com.lfactorial.concurrency.delayqueue;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.function.Consumer;

public class DelayQueue {

    public static class DelayedItem {
        public String val;
        public Long time;
        public DelayedItem(String val, long time) {
            this.val = val;
            this.time = System.currentTimeMillis()+ time;
        }
    }
    PriorityQueue<DelayedItem> pq;
    final Object lock;
    int totalThreads;
    Thread thread;
    boolean running;
    Consumer<String> dataDropAction;

    public DelayQueue(Consumer<String> dataDropAction) {
        Comparator<DelayedItem> comparator = Comparator.comparing(di -> di.time);
        this.pq = new PriorityQueue<>(100, comparator);
        this.dataDropAction = dataDropAction;
        lock = new Object();
        this.totalThreads = 5;
        try {
            this.init();
            running = true;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(DelayedItem item) {
        synchronized (lock) {
            this.pq.offer(item);
            lock.notifyAll();
        }

    }
    public void stop() {
        synchronized (lock) {
            this.running = false;
            lock.notifyAll();
        }
    }

    private void init() throws InterruptedException {
        thread = new Thread(() -> {
            while(running) {
                synchronized(lock) {
                    DelayedItem item = pq.peek();
                    if (pq.isEmpty()) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else if (item.time <= System.currentTimeMillis()) {
                        DelayedItem delayedItem = pq.poll();
                        dataDropAction.accept(delayedItem.val);
                        System.out.println(item.val);
                    } else {
                        try {
                            lock.wait(item.time - System.currentTimeMillis());
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });
        thread.start();

    }
    public static void main(String[] args) throws InterruptedException {
        DelayQueue delayQueue = new DelayQueue(i->{});
        delayQueue.add(new DelayedItem("1", 6000));
        delayQueue.add(new DelayedItem("2", 5000));
        delayQueue.add(new DelayedItem("3", 4000));
        delayQueue.add(new DelayedItem("4", 3000));
        delayQueue.add(new DelayedItem("5", 2000));


        Thread.sleep(7000);
        delayQueue.stop();

    }
}
