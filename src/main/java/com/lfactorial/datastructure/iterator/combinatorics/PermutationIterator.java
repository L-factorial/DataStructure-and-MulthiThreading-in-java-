package com.lfactorial.datastructure.iterator.combinatorics;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;

public class PermutationIterator implements Iterator<String> {
    private static class PermutationState {
        String currString;
        Set<Integer> visited;

        PermutationState(String currString, Set<Integer> visited) {
            this.currString = currString;
            this.visited = visited;

        }
    }

    private final String inputString;
    private final Stack<PermutationState> stack;
    private String nextString;

    public PermutationIterator(String inputString){
        this.inputString = inputString;
        this.stack = new Stack<>();
        this.stack.push(new PermutationState("",new HashSet<>()));
        this.processNext();
    }

    public boolean hasNext() {
        return nextString != null;
    }

    public String next() {
        if(!hasNext()) {
            return  null;
        }
        String ret = nextString;
        nextString = null;
        processNext();
        return ret;
    }

    private void processNext() {
        while(!stack.empty()) {
            PermutationState currState = stack.pop();
            if(currState.currString.length() == inputString.length()) {
                nextString = currState.currString;
                break;
            }
            for(int i = inputString.length()-1; i >= 0; --i) {
                if(!currState.visited.contains(i)) {
                    Set<Integer> newVisitedState = new HashSet<>(currState.visited);
                    newVisitedState.add(i);
                    stack.push(new PermutationState(currState.currString+inputString.charAt(i), newVisitedState));
                }
            }
        }
    }

}
