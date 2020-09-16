package lf2.flap.models.entity;

public class Transition {
	private int id;
	private String value;
	private State startState, endState;
	
	public Transition(int id, String value, State start, State end) {
		this.id = id;
		this.value = value;
		this.startState = start;
		this.endState = end;
	}
	
	public Transition(int id, String value, State state) {
		this.id = id;
		this.value = value;
		this.startState = state;
		this.endState = state;
	}
	
	public boolean isRecursive() {
		return this.endState == this.startState;
	}
	
	public String getValue() {
		return value;
	}
	
	public State getStartState() {
		return startState;
	}
	
	public State getEndState() {
		return endState;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public int getId() {
		return id;
	}
}
