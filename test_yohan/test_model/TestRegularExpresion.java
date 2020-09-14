package test_model;

import lf2.flap.models.entity.Automaton;
import lf2.flap.models.entity.RegularExpresion;
import lf2.flap.models.entity.State;

public class TestRegularExpresion {

//	public static void main(String[] args) {
//		Automaton automaton = new Automaton();
//		
//		State s = new State(automaton, "q0");
//		s.setInit(true);
//		State s1 = new State(automaton, "q1");
//		State s2 = new State(automaton, "q2");
//		State s3 = new State(automaton, "q3");
//		
//		automaton.addState(s);
//		automaton.addState(s1);
//		automaton.addState(s2);
//		automaton.addState(s3);
//		
//		automaton.createTransition("a", s, s1);
//		automaton.createTransition("a", s, s2);
//		automaton.createTransition("a", s, s3);
//		
//		RegularExpresion regex = new RegularExpresion(automaton);
//		
//		System.out.println(regex.solve(automaton.getInitialState()));
//		
//		
//	}
	
	public static void main(String[] args) {
		Automaton automaton = new Automaton();
		
		State s1 = new State(automaton, "1");
		s1.setInit(true);
		State s2 = new State(automaton, "2");
		State s3 = new State(automaton, "3");
		State s4 = new State(automaton, "4");
		
		automaton.addState(s1);
		automaton.addState(s2);
		automaton.addState(s3);
		automaton.addState(s4);
		
		automaton.createTransition("0", s1, s2);
		automaton.createTransition("1", s1, s3);
		automaton.createTransition("1", s2, s4);
		automaton.createTransition("0", s3, s4);
		automaton.createTransition("1", s4, s2);
		automaton.createTransition("0", s4, s1);
		
		RegularExpresion regex = new RegularExpresion(automaton);
		
		System.out.println(regex.solve(automaton.getInitialState()));
		System.out.println(regex.getRegex());
		
		
	}

}
