package com.lfactorial.concurrency.cachewithttl.lru;

import com.lfactorial.concurrency.delayqueue.DelayQueue;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

class LRUCache {

    public class ListNode{
        String val;
        String key;
        ListNode next;
        ListNode prev;
        ListNode(String key, String val) {
            this.val = val;
            this.key = key;
        }
    }

    int capacity;
    int size;
    ListNode head;
    ListNode tail;
    Map<String, ListNode> map;

    DelayQueue delayQueue;
    public LRUCache(int capacity) {
        this.capacity = capacity;
        head = new ListNode("","");
        tail = new ListNode("","");
        head.prev = tail;
        tail.next = head;
        this.map = new HashMap<>();
        Consumer<String> dataDropAction = this::remove;
        this.delayQueue = new DelayQueue(dataDropAction);
    }


    public synchronized String get(String key) {
        if(!map.containsKey(key)) {
            return null;
        }
        ListNode curr = map.get(key);
        delete(curr);
        addToFront(curr);
        return curr.val;
    }

    public synchronized void put(String key, String value) {
        if(map.containsKey(key)) {
            get(key);
            map.get(key).val = value;
            return;
        }
        if(size == capacity) {
            --size;
            map.remove(tail.next.key);
            delete(tail.next);
        }
        ListNode newNode = new ListNode(key, value);
        map.put(key, newNode);
        addToFront(newNode);
        ++size;
    }

    public synchronized  void remove(String key) {
        if(map.containsKey(key)) {
            --size;
            delete(map.get(key));
            map.remove(key);
            System.out.println("Deleting key "+ key);
        }
    }

    /**
     * This put operation in LRU cache enforces the item to live only for ttl milliseconds. The item will be dropped after the ttl window ellapses
     * @param key
     * @param value
     * @param ttl
     */
    public synchronized void put(String key, String value, long ttl) {
        put(key, value);
        this.delayQueue.add(new DelayQueue.DelayedItem(key, ttl));
    }

    public synchronized boolean isEmpty() {
        return size == 0;
    }
    public synchronized void clearTtl(){
        this.delayQueue.stop();
    }

    private void delete(ListNode node) {
        ListNode prev = node.prev;
        ListNode next = node.next;
        next.prev = prev;
        prev.next = next;

    }
    private void addToFront(ListNode node) {
        ListNode currHead = head.prev;
        head.prev = node;
        node.prev = currHead;
        currHead.next = node;
        node.next = head;
    }

    public static void main(String[] args) throws InterruptedException {
        LRUCache lruCache = new LRUCache(10);
        lruCache.put("1","1", 1000);
        lruCache.put("2","1", 6000);

        lruCache.put("3","1", 2000);

        lruCache.put("4","1", 3000);

        while(!lruCache.isEmpty()){}

        lruCache.clearTtl();

    }
}