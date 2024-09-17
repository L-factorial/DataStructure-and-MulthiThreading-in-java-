package com.lfactorial.test.datastructure;

import com.lfactorial.datastructure.IndexedPriorityQueue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

public class IndexedPriorityQueueTest {

    @Test
    public void PriorityQueueIntegerMinHeapTest() {
        Comparator<Integer> comparator = Comparator.comparingInt(i -> i);
        IndexedPriorityQueue<Integer> minHeap = new IndexedPriorityQueue<>(10, comparator);

        minHeap.offer(25);
        Assertions.assertTrue(minHeap.contains(25));
        Assertions.assertTrue(minHeap.peek() == 25);

        minHeap.offer(31);
        Assertions.assertTrue(minHeap.contains(31));
        Assertions.assertTrue(minHeap.peek() == 25);

        minHeap.offer(41);
        Assertions.assertTrue(minHeap.contains(41));
        Assertions.assertTrue(minHeap.peek() == 25);

        minHeap.offer(10);
        Assertions.assertTrue(minHeap.contains(10));
        Assertions.assertTrue(minHeap.peek() == 10);

        int top = minHeap.poll();
        Assertions.assertTrue(top == 10);
        Assertions.assertFalse(minHeap.contains(top));
        Assertions.assertTrue(minHeap.peek() == 25);

        minHeap.offer(5);
        Assertions.assertTrue(minHeap.contains(5));
        Assertions.assertTrue(minHeap.peek() == 5);

        top = minHeap.poll();
        Assertions.assertTrue(top == 5);
        Assertions.assertFalse(minHeap.contains(top));
        Assertions.assertTrue(minHeap.peek() == 25);
    }

    @Test
    public void PriorityQueueIntegerMaxHeapTest() {
        Comparator<Integer> comparator = (i1, i2) -> i2-i1;
        IndexedPriorityQueue<Integer> maxHeap = new IndexedPriorityQueue<>(10, comparator);

        maxHeap.offer(25);
        Assertions.assertTrue(maxHeap.contains(25));
        Assertions.assertTrue(maxHeap.peek() == 25);

        maxHeap.offer(31);
        Assertions.assertTrue(maxHeap.contains(31));
        Assertions.assertTrue(maxHeap.peek() == 31);

        maxHeap.offer(41);
        Assertions.assertTrue(maxHeap.contains(41));
        Assertions.assertTrue(maxHeap.peek() == 41);

        maxHeap.offer(10);
        Assertions.assertTrue(maxHeap.contains(10));
        Assertions.assertTrue(maxHeap.peek() == 41);

        int top = maxHeap.poll();
        Assertions.assertTrue(top == 41);
        Assertions.assertFalse(maxHeap.contains(top));
        Assertions.assertTrue(maxHeap.peek() == 31);

        maxHeap.offer(5);
        Assertions.assertTrue(maxHeap.contains(5));
        Assertions.assertTrue(maxHeap.peek() == 31);

        top = maxHeap.poll();
        Assertions.assertTrue(top == 31);
        Assertions.assertFalse(maxHeap.contains(top));
        Assertions.assertTrue(maxHeap.peek() == 25);
    }

}
