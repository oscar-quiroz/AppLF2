package test_model;

import javax.swing.JFrame;

import lf2.flap.models.entity.Automaton;
import lf2.flap.models.entity.State;
import lf2.flap.views.Canvas;
import lf2.flap.views.CanvasRegex;
import lf2.flap.views.ViewConstants;

public class TestCanvas {

//	public static void main(String[] args) {
//		JFrame frame = new JFrame("Test canvas");
//		frame.setSize(ViewConstants.defaultSize);
//		frame.setLocationRelativeTo(null);
//		
//		
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
//		automaton.createTransition("b", s, s2);
//		automaton.createTransition("c", s, s3);
//		
//		CanvasRegex c = new CanvasRegex(automaton);
////		c.setAutomaton(automaton);
//		
//		frame.add(c);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setVisible(true);
//	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Test canvas");
		frame.setSize(ViewConstants.defaultSize);
		frame.setLocationRelativeTo(null);
		
		
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
		
		automaton.createTransition("-1", s1, s2);
		automaton.createTransition("0", s1);
		automaton.createTransition("1", s1, s3);
		automaton.createTransition("2", s2, s4);
		automaton.createTransition("3", s3, s4);
		automaton.createTransition("4", s4, s2);
		automaton.createTransition("5", s4, s1);
		
		CanvasRegex c = new CanvasRegex(automaton);
//		c.setAutomaton(automaton);
		
		frame.add(c);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
