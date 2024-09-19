package com.lfactorial.test.datastructure.trie;

import com.lfactorial.datastructure.trie.TrieMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TrieMapTest {

    @Test
    public void trieMapTest(){
        TrieMap trieMap = new TrieMap();
        trieMap.put("apple", "1");
        trieMap.put("appl", "2");
        trieMap.put("apples", "3");
        trieMap.put("orange", "4");
        trieMap.put("orge", "5");
        trieMap.put("ange", "5");
        trieMap.put("angel", "5");
        trieMap.put("angelo", "5");
        trieMap.put("angetb", "5");


        Assertions.assertTrue(trieMap.get("orange").equals("4"));
        Set<String> results = new HashSet<>(trieMap.scanByPrefix("app"));
        Set<String> expected = new HashSet<>(List.of("apple", "appl", "apples"));
        Assertions.assertEquals(expected, results);

        trieMap.remove("apple");
        results = new HashSet<>(trieMap.scanByPrefix("app"));
        expected = new HashSet<>(List.of("appl", "apples"));
        Assertions.assertEquals(expected, results);

        Set<String> suggestions = trieMap.typoSuggestion("ormnge", 2);
        Set<String> expectedSuggestions = new HashSet<>(List.of("orange", "orge"));
        Assertions.assertTrue(suggestions.equals(expectedSuggestions));

        suggestions = trieMap.typoSuggestion("angeab", 2);
        expectedSuggestions = new HashSet<>(List.of("ange", "angelo", "angel", "angetb"));
        Assertions.assertEquals(suggestions, expectedSuggestions);

        trieMap.put("apple", "2");
        suggestions = trieMap.typoSuggestion("app", 2);
        expectedSuggestions = new HashSet<>(List.of("apple", "appl"));
        Assertions.assertEquals(suggestions, expectedSuggestions);

        suggestions = trieMap.typoSuggestion("zapp", 2);
        expectedSuggestions = new HashSet<>(List.of("appl"));
        Assertions.assertEquals(suggestions, expectedSuggestions);

        suggestions = trieMap.typoSuggestion("zappl", 2);
        expectedSuggestions = new HashSet<>(List.of("appl", "apple"));
        Assertions.assertEquals(suggestions, expectedSuggestions);

        suggestions = trieMap.typoSuggestion("tppl", 2);
        expectedSuggestions = new HashSet<>(List.of( "apple", "appl"));
        Assertions.assertEquals(suggestions, expectedSuggestions);

        suggestions = trieMap.typoSuggestion("apdpzle", 2);
        expectedSuggestions = new HashSet<>(List.of( "apple" ));
        Assertions.assertEquals(suggestions, expectedSuggestions);

    }
}
