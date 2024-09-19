A trie map for character literals with functionality to generate suggestion list for incorrect words
Please look at the [test](https://github.com/L-factorial/DataStructure-and-MulthiThreading-in-java-/blob/main/src/test/java/com/lfactorial/test/datastructure/trie/TrieMapTest.java)

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
        suggestions = trieMap.typoSuggestion("zappl", 2);
        
        In the above example the the words closest to "zappl" in 2 edits distance are: 
        appl ( one edit : which is attained by deleting z)
        apple( two edit : which is attained by deleting z and inserting l at the end)
