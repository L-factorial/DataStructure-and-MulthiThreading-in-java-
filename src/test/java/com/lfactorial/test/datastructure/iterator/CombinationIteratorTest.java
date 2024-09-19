package com.lfactorial.test.datastructure.iterator;

import com.lfactorial.datastructure.iterator.combinatorics.CombinationIterator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

public class CombinationIteratorTest {

    @Test
    public void testCombinationIterator(){
        Iterator<String> combinationIterator = new CombinationIterator("abcd", 2);
        Iterator<String> expectedIterator = List.of("ab","ac","ad","bc","bd","cd").iterator();
        while(combinationIterator.hasNext()) {
            Assertions.assertEquals(combinationIterator.next(), expectedIterator.next());
        }
        Assertions.assertFalse(expectedIterator.hasNext());
    }
}
