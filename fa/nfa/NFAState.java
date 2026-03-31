package fa.nfa;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Represents a state in a Non-deterministic Finite Automaton (NFA).
 * @author ChandlerBlack, RhysJordan
 */
public class NFAState extends fa.State {

    // Map to store transitions: symbol -> next state
    private Map<Character, Set<NFAState>> transitions;

    /**
     * Constructor for NFAState.
     * Initializes the state with a name and an empty set of transitions.
     * @param name the label of the state
     */
    public NFAState(String name) {
        super(name);
        transitions = new LinkedHashMap<>();
    }

    /**
     * Adds a transition from this state to the next state on the given symbol.
     * @param symbol the input symbol for the transition
     * @param nextState the state to transition to on the given symbol
     */
    public void addTransition(char symbol, NFAState nextState) {
        if (!transitions.containsKey(symbol)) {
            transitions.put(symbol, new LinkedHashSet<>());
        }
        transitions.get(symbol).add(nextState);

    }

    /**
     * Retrieves the next state for a given symbol @param symbol
     * @return
     */
    public Set<NFAState> getTransitions(char symbol) {
        if (transitions.containsKey(symbol)) {
            return transitions.get(symbol);
        }
        else {
            return new LinkedHashSet<>();
        }
    }

    /**
     * Overrides the equals method to compare NFAState objects based on their names.
     * @param obj the object to compare with
     * @return true if the names of the states are equal, false otherwise
     */
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        } 

        NFAState state = (NFAState) obj;
        return this.getName().equals(state.getName());
    }
}
