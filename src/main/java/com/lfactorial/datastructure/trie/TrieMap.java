package com.lfactorial.datastructure.trie;

import java.util.*;

public class TrieMap {
    public static class TrieNode{
        Map<Character, TrieNode> childrenMap;
        String value;
        public TrieNode(){
            this.childrenMap = new TreeMap<>();
            this.value = null;
        }
    }

    TrieNode root;
    int count;


    public TrieMap(){
        root = new TrieNode();
        this.count = 0;
    }

    public  void put(String key, String value) {
        TrieNode curr = root;
        for(int i = 0; i < key.length(); ++i) {
            char c = key.charAt(i);
            if(!curr.childrenMap.containsKey(c)) {
                curr.childrenMap.put(c, new TrieNode());
            }
            curr = curr.childrenMap.get(c);
        }
        curr.value = value;
        ++count;
    }

    public String get(String key) {
        TrieNode node  = prefixNode(key);
        if(node == null) {
            return null;
        }
        return node.value;
    }

    public  boolean containsKey(String key) {
        TrieNode node  = prefixNode(key);
        if(node == null) {
            return false;
        }
        return node.value != null;
    }

    public  void remove(String key) {
        TrieNode curr = root;
        Stack<TrieNode> stack = new Stack<>();
        for(int i = 0; i < key.length(); ++i) {
            stack.push(curr);
            curr = curr.childrenMap.getOrDefault(key.charAt(i), null);
            if(curr == null) {
                return;
            }
        }
        int i = key.length()-1;
        curr.value = null;
        while(!stack.empty()) {
            char c = key.charAt(i);
            curr = stack.pop();
            if(curr.childrenMap.get(c).childrenMap.isEmpty()) {
                curr.childrenMap.remove(c);
            }

            --i;
        }
        --count;
    }

    public synchronized List<String> scanByPrefix(String prefix) {
        List<String> results = new ArrayList<>();
        TrieNode curr = prefixNode(prefix);
        if(curr == null) {
            return results;
        }
        scanFrom(curr, prefix, results);
        return results;

    }

    public  int size(){
        return count;
    }

    public Set<String> typoSuggestion(String word, int allowedEdit) {
        Set<String> results = new HashSet<>();
        collectSuggestion(root, word, "", 0, 0, allowedEdit, results, "");
        return results;
    }

    private void collectSuggestion(TrieNode node, String word, String currWord, int idx, int currentEdit, int allowedEdit, Set<String> results, String history) {
        System.out.println(String.format("Current :%s edits : %d  \n History : %s", currWord, currentEdit, history));
        if(node == null) {
            return;
        }
        if(currentEdit > allowedEdit) {
            return;
        }
        if(idx >= word.length()) {
            if(node.value != null) {
                results.add(currWord);
            }
            //return; // We should not return from here, idx can be exhausted but we might want to add more chars until we have a room to edit.
        }


        //Delete current char if the idx is in the range
        if(idx < word.length()) {
            collectSuggestion(node, word, currWord,idx+1, currentEdit + 1, allowedEdit, results, history +String.format("-- deleted %c at %d", word.charAt(idx), idx));

        }

        char c = idx < word.length() ?word.charAt(idx) : ' ';

        for(Map.Entry<Character, TrieNode> entry : node.childrenMap.entrySet()) {
            char c1 = entry.getKey();
            TrieNode childNode = entry.getValue();

            //Insert valid chars
            collectSuggestion(childNode, word, currWord+c1, idx, currentEdit+1, allowedEdit, results, history +String.format("-- inserted %c at %d", c1, idx));
            if(idx >= word.length()) { // If idx is out of range, replacement of char is not needed
                continue;
            }

            // replace
            if(c1 != c) {
                collectSuggestion(childNode, word, currWord + c1, idx + 1, currentEdit + 1, allowedEdit, results, history + String.format(" -- replaced %c at %d with %c", c, idx, c1));
            }
        }


        //Just follow the path in the trie based on the character in the string without any edit
        collectSuggestion(node.childrenMap.getOrDefault(c, null), word, currWord+c, idx+1, currentEdit, allowedEdit, results, history);


    }

    private void scanFrom(TrieNode curr, String prefix, List<String> results) {
        if(curr == null) {
            return;
        }
        if(curr.value != null) {
            results.add(prefix);
        }
        for(char c : curr.childrenMap.keySet()) {
            scanFrom(curr.childrenMap.get(c), prefix+c, results);
        }

    }
    private TrieNode prefixNode(String prefix) {
        TrieNode curr = root;
        for(int i = 0; i < prefix.length(); ++i) {
            curr = curr.childrenMap.getOrDefault(prefix.charAt(i), null);
            if(curr == null) {
                return null;
            }
        }
        return curr;
    }


}
