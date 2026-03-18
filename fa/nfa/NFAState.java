package fa.nfa;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Represents a state in a Non-deterministic Finite Automaton (NFA).
 * @author ChandlerBlack
 */
public class NFAState extends fa.State {

    // Map to store transitions: symbol -> next state
    private Map<Character, Set<NFAState>> transitions;

    /**
     * Constructor for NFAState.
     * @param name
     */
    public NFAState(String name) {
        super(name);
        transitions = new LinkedHashMap<>();
    }

    /**
     * Adds a transition from this state to the next state on the given symbol.
     * @param symbol
     * @param nextState
     */
    public void addTransition(char symbol, NFAState nextState) {
        if (!transitions.containsKey(symbol)) {
            transitions.put(symbol, new LinkedHashSet<>());
        }
        transitions.get(symbol).add(nextState);

    }

    /**
     * Retrieves the next state for a given symbol.Set<NFASta@param symbol
     * @return
     */
    public Set<NFAState> getTransitions(char symbol) {
        return transitions.get(symbol);
    }
}
