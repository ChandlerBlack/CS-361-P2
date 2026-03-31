package fa.nfa;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * Represents a Non-deterministic Finite Automaton (NFA).
 * This class implements the NFAInterface and provides methods to define the states, transitions, and behavior of an NFA.
 * Utilizes a map to store states and their transitions, allowing for efficient access and manipulation of the NFA's structure.
 * @author ChandlerBlack
 */
public class NFA implements NFAInterface {

    private String startState; // Label of the start state
    private Set<NFAState> finalStates; // Set of final states
    private Map<String, NFAState> states; // Map of state labels to NFAState objects
    private LinkedHashSet<Character> sigma; // Set of symbols in the NFA's alphabet

    /**
     * Constructor for NFA.
     * Initializes the data structures for states, final states, and the alphabet (sigma).
     * The NFA starts with no states, no final states, and an empty alphabet.
     */
    public NFA() {
        finalStates = new LinkedHashSet<>();
        states = new LinkedHashMap<>();
        sigma = new LinkedHashSet<>();
    }

    @Override
    public boolean addState(String name) {
        if (states.containsKey(name)) {
            return false; // State already exists
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
            return true; // State exists and is now a final state
        }
        return false;
    }

    @Override
    public boolean setStart(String name) {
        NFAState state = (NFAState) getState(name);
        if (state != null) {
            startState = name;
            return true; // State exists and is now the start state
        }
        return false;
    }

    @Override
    public void addSigma(char symbol) {
        sigma.add(symbol);
    }

    @Override
    public boolean accepts(String s) { // BFS preformed
        Set<NFAState> copies = new LinkedHashSet<>();
        copies.add(getState(startState));
        copies.addAll(eClosure(getState(startState)));

        for (char read : s.toCharArray()) {
            Set<NFAState> nextCopies = new LinkedHashSet<>();
            
            for (NFAState state : copies) {
                Set<NFAState> directTransitions = getToState(state, read);
                for(NFAState nextState : directTransitions) {
                    nextCopies.addAll(eClosure(nextState));
                }
            }

            copies = nextCopies;
        }

        for (NFAState state : copies) {
            if (finalStates.contains(state)) {
                return true; // At least one copy is in a final state, so the string is accepted
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
                    return true; // State exists and is a final state
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
        if (s == null) return closure; // Return empty set if the input state is null

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
                Set<NFAState> directTransitions = getToState(state, read);
                for (NFAState nextState : directTransitions) {
                    nextStates.addAll(eClosure(nextState));
                }
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
