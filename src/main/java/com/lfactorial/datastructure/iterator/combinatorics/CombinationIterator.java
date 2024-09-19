package com.lfactorial.datastructure.iterator.combinatorics;

import java.util.Iterator;
import java.util.Stack;

/**
 * Iterator class to compute the next lexicographical combination item
 */
public class CombinationIterator implements Iterator<String> {

    public static class State{
        String curr;
        int idx;
        public State(String curr, int idx) {
            this.idx = idx;
            this.curr = curr;
        }
    }

    String str;
    int comboLen;
    Stack<State> stack;
    State currState;

    /**
     *
     * @param characters input string
     * @param combinationLength size of combination string to be computed
     */
    public CombinationIterator(String characters, int combinationLength) {
        str = characters;
        comboLen = combinationLength;
        this.stack = new Stack<>();
        this.stack.push(new State("", 0));
        processNext();
    }

    @Override
    public String next() {
        String ret = currState.curr;
        currState = null; // Resetting the current state as null. If there are more items to be iterated the processNext() call will set the next value
        processNext();
        return ret;
    }

    @Override
    public boolean hasNext() {
        return currState != null;
    }

    private void processNext() {
        while(!stack.empty()) {
            State state = stack.pop();
            if(state.curr.length() == comboLen) {
                currState = state;
                break;
            }
            else if(state.idx >= str.length()) {
                //Do not push anything in the stack
            }
            else {
                stack.push(new State(state.curr, state.idx + 1)); // Skip the item at current index and move to the next index
                stack.push(new State(state.curr+str.charAt(state.idx), state.idx + 1)); // Keep the item at current index and move to the next index
            }
        }
    }
}
