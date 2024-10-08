package com.lfactorial.datastructure.iterator.UnionSet;

import java.util.Iterator;
import java.util.List;

/**
 * Given the two sorted list(may contains duplicate), this iterator will help find the
 * next item in sorted order from the union of the given two list
 *
 */
public class UnionSetIterator<T extends Comparable<T>> implements Iterator<T> {
    private static class PeekingIterator<T extends Comparable<T>> {
        Iterator<T> itr;
        T nextItem;
        public PeekingIterator(Iterator<T> itr) {
            this.itr = itr;
            move();
        }

        boolean hasNext(){
           return nextItem != null;
        }
        void move() {
            nextItem = null;
            if(itr.hasNext()) {
                nextItem = itr.next();
            }
        }
        T peek() {
            return nextItem;
        }
    }

    PeekingIterator<T> peekingIterator1;
    PeekingIterator<T> peekingIterator2;
    T nextItem;
    T currItem;
    T prevItem;
    public UnionSetIterator(List<T> list1, List<T> list2) {
        peekingIterator1 = new PeekingIterator<>(list1.iterator());
        peekingIterator2 = new PeekingIterator<>(list2.iterator());
        process();
    }
    @Override
    public boolean hasNext() {
        return currItem != null;
    }

    @Override
    public T next() {
        if(hasNext()) {
            T ret = currItem;
            prevItem = currItem;
            currItem = null;
            process();
            return ret;
        }
        return null;
    }

    private void process() {
        while(peekingIterator1.hasNext() && peekingIterator2.hasNext() ) {
            T item1 = peekingIterator1.peek();
            T item2 = peekingIterator2.peek();

            T item = item1;
            PeekingIterator<T> currItr = peekingIterator1;

            if (item2.compareTo(item1) < 0) {
                item = item2;
                currItr = peekingIterator2;
            }

            if(prevItem == null || prevItem.compareTo(item) != 0) {
                currItem = item;
                currItr.move();
                return;
            }
            else {
                currItr.move();
            }
        }
        while(peekingIterator1.hasNext()) {
            T item = peekingIterator1.peek();
            if(prevItem == null || prevItem.compareTo(item) != 0) {
                currItem = item;
                peekingIterator1.move();
                return;
            }
            else {
                peekingIterator1.move();
            }
        }

        while(peekingIterator2.hasNext()) {
            T item = peekingIterator2.peek();
            if(prevItem == null || prevItem.compareTo(item) != 0) {
                currItem = item;
                peekingIterator2.move();
                return;
            }
            else {
                peekingIterator2.move();
            }
        }
    }

    public static void main(String []args) {
        List<Integer> list1 = List.of(1, 6, 11, 21, 90);
        List<Integer> list2 = List.of(0,2,3,3,6,6,13,21, 35, 92);

        UnionSetIterator<Integer> unionSetIterator = new UnionSetIterator<>(list1, list2);

        while(unionSetIterator.hasNext()){
            System.out.print(unionSetIterator.next() + "   ");
        }
    }

}
