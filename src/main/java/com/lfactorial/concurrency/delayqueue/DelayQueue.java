package com.lfactorial.concurrency.delayqueue;
import java.util.Comparator;
import java.util.PriorityQueue;

public class DelayQueue {

    public static class DelayedItem {
        public String val;
        public Long time;
        public DelayedItem(String val, long time) {
            this.val = val;
            this.time = time;
        }
    }
    PriorityQueue<DelayedItem> pq;
    final Object lock;
    int totalThreads;
    Thread thread;
    boolean running;

    public DelayQueue() {
        Comparator<DelayedItem> comparator = Comparator.comparing(di -> di.time);
        this.pq = new PriorityQueue<>(100, comparator);
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
                        pq.poll();
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
        DelayQueue delayQueue = new DelayQueue();
        delayQueue.add(new DelayedItem("1", System.currentTimeMillis()+6000));
        delayQueue.add(new DelayedItem("2", System.currentTimeMillis()+5000));
        delayQueue.add(new DelayedItem("3", System.currentTimeMillis()+4000));
        delayQueue.add(new DelayedItem("4", System.currentTimeMillis()+3000));
        delayQueue.add(new DelayedItem("5", System.currentTimeMillis()+2000));


        Thread.sleep(7000);
        delayQueue.stop();

    }
}
