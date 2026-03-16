package fa.nfa;

import java.util.LinkedHashSet;
import java.util.Set;

import fa.State;

public class NFA implements NFAInterface {

    private NFAState Startstate;
    private LinkedHashSet<NFAState> Finalstates;
    private LinkedHashSet<NFAState> States;
    private LinkedHashSet<Character> Sigma;

    public NFA() {
        Finalstates = new LinkedHashSet<>();
        States = new LinkedHashSet<>();
        Sigma = new LinkedHashSet<>();
    }

    @Override
    public boolean addState(String name) {
        NFAState newState = new NFAState(name);
        return States.add(newState);
    }

    @Override
    public boolean setFinal(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setFinal'");
    }

    @Override
    public boolean setStart(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setStart'");
    }

    @Override
    public void addSigma(char symbol) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addSigma'");
    }

    @Override
    public boolean accepts(String s) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'accepts'");
    }

    @Override
    public Set<Character> getSigma() {
        return Sigma;
    }

    @Override
    public State getState(String name) {
        for (NFAState state : States) {
            if (state.getName().equals(name)) {
                return state;
            }
        }
        return null; // State not found
    }

    @Override
    public boolean isFinal(String name) {
        NFAState state = (NFAState) getState(name);
        return state != null && Finalstates.contains(state);
    }

    @Override
    public boolean isStart(String name) {
        NFAState state = (NFAState) getState(name);
        return state != null && Startstate.equals(state);
    }

    @Override
    public Set<NFAState> getToState(NFAState from, char onSymb) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getToState'");
    }

    @Override
    public Set<NFAState> eClosure(NFAState s) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eClosure'");
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
