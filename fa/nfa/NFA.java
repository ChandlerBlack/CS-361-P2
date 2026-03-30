package fa.nfa;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import fa.State;

public class NFA implements NFAInterface {

    private NFAState startState;
    private Set<NFAState> finalStates;
    private Map<String, NFAState> states;
    private LinkedHashSet<Character> sigma;

    public NFA() {
        finalStates = new LinkedHashSet<>();
        states = new LinkedHashMap<>();
        sigma = new LinkedHashSet<>();
    }

    @Override
    public boolean addState(String name) {
        NFAState newState = new NFAState(name);
        return states.put(name, newState) == null;
    }

    @Override
    public boolean setFinal(String name) {
        NFAState state = (NFAState) getState(name);
        if (state != null) {
            finalStates.add(state);
            return true;
        }
        return false;
    }

    @Override
    public boolean setStart(String name) {
        NFAState state = (NFAState) getState(name);
        if (state != null) {
            startState = state;
            return true;
        }
        return false;
    }

    @Override
    public void addSigma(char symbol) {
        sigma.add(symbol);
    }

    @Override
    public boolean accepts(String s) { // BFS preformed
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'accepts'");
    }

    @Override
    public Set<Character> getSigma() {
        return sigma;
    }

    @Override
    public State getState(String name) {
        return states.get(name);
    }

    @Override
    public boolean isFinal(String name) {
        NFAState state = (NFAState) getState(name);
        return state != null && finalStates.contains(state);
    }

    @Override
    public boolean isStart(String name) {
        NFAState state = (NFAState) getState(name);
        return state != null && startState.equals(state);
    }

    @Override
    public Set<NFAState> getToState(NFAState from, char onSymb) {
        return from.getTransitions(onSymb);
    }

    @Override
    public Set<NFAState> eClosure(NFAState s) { // DFS preformed
        Set<NFAState> closure = new LinkedHashSet<>();
        Stack<NFAState> stack = new Stack<>();
        stack.push(s);
        while (!stack.isEmpty()) {
            stack.pop().getTransitions('e');
            for (NFAState next : s.getTransitions('e')) {
                if (!closure.contains(next)) {
                    closure.add(next);
                    stack.push(next);
                }
            }
        }
        return closure;
    }

    @Override
    public int maxCopies(String s) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'maxCopies'");
    }

    @Override
    public boolean addTransition(String fromState, Set<String> toStates, char onSymb) {
        NFAState from = (NFAState) getState(fromState);
        if (from == null) {
            return false; // From state does not exist
        }
        for (String toStateName : toStates) {
            NFAState to = (NFAState) getState(toStateName);
            if (to == null) {
                return false; // To state does not exist
            }
            from.addTransition(onSymb, to);
        }
        return true;
    }

    @Override
    public boolean isDFA() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isDFA'");
    }
    
}
