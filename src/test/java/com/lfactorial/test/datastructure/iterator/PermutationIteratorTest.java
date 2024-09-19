package com.lfactorial.test.datastructure.iterator;

import com.lfactorial.datastructure.iterator.combinatorics.PermutationIterator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PermutationIteratorTest {

    @Test
    public void permutationIteratorTest(){
        Iterator permutationIterator = new PermutationIterator("abc");
        Iterator<String> expectedListIterator = List.of("abc", "acb", "bac", "bca", "cab", "cba").iterator();
        while (permutationIterator.hasNext()) {
            Assertions.assertEquals(permutationIterator.next(), expectedListIterator.next());
        }
        Assertions.assertFalse(expectedListIterator.hasNext());
    }
}
