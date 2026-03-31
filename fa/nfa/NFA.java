package fa.nfa;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class NFA implements NFAInterface {

    private String startState;
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
        if (states.containsKey(name)) {
            return false;
        }
        NFAState newState = new NFAState(name);
        states.put(name, newState);
        return true;
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
            startState = name;
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
        return Trace(s);
    }

    /**
     * Breadth first traversal of the machine
     * Can be modified for maxCopies, a potential solution is to return the maxCopies as negative when it isn't accepted (credit to Dean Cunningham)
     * @return boolean, true when s is accepted
     */
    private boolean Trace(String s) {
        Set<NFAState> copies = new LinkedHashSet<>();
        copies.add(getState(startState));
        copies.addAll(eClosure(getState(startState)));

        while (!s.isEmpty()) {
            char read = s.charAt(0);
            Set<NFAState> nextCopies = new LinkedHashSet<>();
            
            for (NFAState state : copies) {
                Set<NFAState> directTransitions = getToState(state, read);
                for(NFAState nextState : directTransitions) {
                    nextCopies.addAll(eClosure(nextState));
                }
            }

            s = s.substring(1);
            copies = nextCopies;
        }

        for (NFAState state : copies) {
            if (finalStates.contains(state)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Set<Character> getSigma() {
        return sigma;
    }

    @Override
    public NFAState getState(String name) {
        return states.get(name);
    }

    @Override
    public boolean isFinal(String name) {
        NFAState state = getState(name);

        if (state != null) {
            for (NFAState s : finalStates) {
                if (s.getName().equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isStart(String name) {
        NFAState state = getState(name);
        return state != null && getState(startState).getName().equals(name);
    }

    @Override
    public Set<NFAState> getToState(NFAState from, char onSymb) {
        return from.getTransitions(onSymb);
    }

    @Override
    public Set<NFAState> eClosure(NFAState s) { // DFS preformed
        Set<NFAState> closure = new LinkedHashSet<>();
        if (s == null) return closure;

        Stack<NFAState> stack = new Stack<>();
        stack.push(s);
        closure.add(s);

        while (!stack.isEmpty()) {
            NFAState current = stack.pop();
            for (NFAState next : current.getTransitions('e')) {
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
        Set<NFAState> currentStates = eClosure(getState(startState));
        int max = currentStates.size();
        for (char read : s.toCharArray()) {
            Set<NFAState> nextStates = new LinkedHashSet<>();
            for (NFAState state : currentStates) {
                nextStates.addAll(getToState(state, read));
                nextStates.addAll(eClosure(state));
            }
            currentStates = nextStates;
            if (currentStates.size() > max) {
                max = currentStates.size();
            }
        }
        return max;
    }

    @Override
    public boolean addTransition(String fromState, Set<String> toStates, char onSymb) {
        // check that onSymb is in the language
        if (!sigma.contains(onSymb) && onSymb != 'e') {
            return false;
        }

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
        for (NFAState state : states.values()) {
            // DFA must have exactly one transition for every symbol in the alphabet
            for (char symbol : sigma) {
                if (state.getTransitions(symbol).size() != 1) {
                    return false;
                }
            }
            // DFA cannot have epsilon transitions
            if (!state.getTransitions('e').isEmpty()) {
                return false;
            }
        }
        return true;
    }
    
}
