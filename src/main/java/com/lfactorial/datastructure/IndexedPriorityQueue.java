package com.lfactorial.datastructure;

import java.util.*;
import java.util.function.Function;

/**
 * Indexed Priority Queue is a Priority Queue where search of the key is also done in constant time. Removing of the random key is done is logarithmic time.
 * @param <T>
 */
public class IndexedPriorityQueue<T> {
    Comparator<T> comparator;
    Map<T, Integer> index;
    int capacity;
    List<T> arr;
    int size;

    Function<Integer, Integer> parent = i -> (i%2 == 0) ? (i/2) - 1 : i/2 ;
    Function<Integer, Integer> leftChild = i -> i*2 + 1;
    Function<Integer, Integer> rightChild = i -> i*2 + 2;


    public IndexedPriorityQueue(int capacity, Comparator<T> comparator) {
        this.comparator = comparator;
        this.capacity = capacity;
        this.arr = new ArrayList<>();
        this.index = new HashMap<>();
        this.init();
    }

    /**
     * Peek and return the top item in the queue in constant time
     * @return
     */
    public T peek() {
        if(size > 0) {
            return this.arr.get(0);
        }
        return null;
    }

    /**
     * Remove and returns the top item of the PriorityQueue in logarithmic time
     * @return
     */
    public T poll() {
        if(size <= 0 ){
            return null;
        }
        T ret = this.arr.get(0);
        remove(ret);
        return ret;
    }

    /**
     * Adds the given key in logarithmic time
     * @param key
     */
    public void offer(T key) {
        if(size >= capacity) {
            resize();
        }
        this.arr.set(size, key);
        this.index.put(key, size);
        ++size;
        swimUp(size-1);
    }

    /**
     * Checks if the given key is present in this PriorityQueue in constant time
     * @param key
     * @return
     */
    public boolean contains(T key) {
        return this.index.containsKey(key);
    }

    /**
     * Removes any key present PriorityQueue in logarithmic time
     * @param key
     */
    public void remove(T key) {
        if(!this.index.containsKey(key)) {
            return;
        }
        int idx = this.index.get(key);
        swap(idx, size-1);
        this.index.remove(key);
        --size;
        sinkDown(idx);

    }

    /**
     * Initializing the the ArrayList with null objects
     */
    private void init() {
        for(int i = 0; i < capacity; ++i) {
            this.arr.add(null);
        }
    }

    /**
     * Resizes and double the current array list.
     */
    private void resize() {
        List<T> temp = new ArrayList<>(this.arr);
        for(int i = 0; i < capacity; ++ i) {
            temp.add(null);
        }
        capacity = capacity*2;
        this.arr = temp;
    }

    /**
     * Follows and fix the path from i up to the 0 to retain the heap property
     * @param i
     */
    private void sinkDown(int i) {
        int leftChildIdx = leftChild.apply(i);
        int rightChildIdx = rightChild.apply(i);
        if(leftChildIdx >= this.size - 1) {
            return;
        }
        int childToSwap = leftChildIdx;
        if(rightChildIdx <= this.size - 1 &&  comparator.compare(this.arr.get(rightChildIdx), this.arr.get(leftChildIdx))< 0 ) {
            childToSwap = rightChildIdx;
        }
        if(comparator.compare(this.arr.get(i), this.arr.get(childToSwap)) > 0) {
            swap(i, childToSwap);
            sinkDown(childToSwap);
        }
    }

    /**
     * Follow and fix the path from i down to last level of the tree to retain the heap property
     * @param i
     */
    private void swimUp(int i) {
        while(i > 0 && comparator.compare(this.arr.get(i), this.arr.get(parent.apply(i))) < 0) {
            swap(i, parent.apply(i));
            i = parent.apply(i);
        }

    }

    private void swap(int i, int j) {
        T objI = this.arr.get(i);
        T objJ = this.arr.get(j);
        this.arr.set(i, objJ);
        this.arr.set(j, objI);
        this.index.put(objI, j);
        this.index.put(objJ, i);
    }
}
