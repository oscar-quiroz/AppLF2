package lf2.flap.models.entity;

import java.util.ArrayList;
import java.util.List;

public class Automaton {
	private List<State> states;
	private List<Transition> transitions;
	protected State initialState;
	private int incrementalID;

	public Automaton() {
		this.states = new ArrayList<State>();
		this.transitions = new ArrayList<Transition>();
		incrementalID = 0;
	}

	public void addState(State state) {
		this.states.add(state);
	}

	public void createTransition(String value, State start, State end) {
		Transition t = new Transition(incrementalID, value, start, end);
		start.getOutTransitions().add(t);
		end.getInTransitions().add(t);
		this.transitions.add(t);
		incrementalID++;
	}

	public void createTransition(String value, State state) {
		Transition t = new Transition(incrementalID, value, state);
		state.getOutTransitions().add(t);
		state.getInTransitions().add(t);
		this.transitions.add(t);
		incrementalID++;
	}

	public List<State> getStates() {
		return states;
	}

	public List<Transition> getTransitions() {
		return transitions;
	}
	
	public boolean isThereInitialState() {
		return initialState != null;
	}
	
	public State getInitialState() {
		return initialState;
	}

}