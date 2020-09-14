package lf2.flap.models.entity;

import java.util.List;

public class RegularExpresion {
	private Automaton automaton;
	private String regex;
	
	public RegularExpresion(String regex) {
		this.regex = regex;
		this.automaton = new Automaton();
	}
	
	public RegularExpresion(Automaton automaton) {
		this.automaton = automaton;
		this.regex = "";
	}
	
	public Automaton getAutomaton() {
		return getAutomaton(this.regex);
	}
	
	public String getRegex() {
		return getRegex(this.automaton);
	}
	
	public static Automaton getAutomaton(String regex) {
		Automaton automaton= new Automaton();
		return automaton;
	}
	
	public static String getRegex(Automaton automaton) {
		String regex = exam(automaton.initialState);

		return regex;
	}

	private static String exam(State auxs) {
		String regex = "";
		List<Transition> auxol = auxs.getOutTransitions();

		if (auxol.isEmpty())
			return regex;

		for (Transition t : auxol) {
			if (t.isRecursive()) {
				regex += t.getValue() + "*";
				
				if(auxol.size() > 1)
					regex +="(";
			}else {
				regex += t.getValue() + exam(t.getEndState()) + "+";
			}
		}

		if (regex.charAt(regex.length() - 1) == '+')
			regex = regex.substring(0, regex.length() - 1);
		else if (regex.charAt(regex.length() - 1) == '(')
			regex = regex.substring(0, regex.length() - 1);

		if(auxol.size() > 1)
			regex +=")";

		return regex;
	}
	
	public String solve(State ini) {
		String out = "";
		
		List<Transition> auxol = ini.getOutTransitions();

		if (auxol.isEmpty())
			return out;
		
//		for (Transition t : auxol) {
//			if 
//			out += t.getEndState() + "+";
//		}
		
		for (int i = 0; i < auxol.size(); i++) {
			if ((i+1) == auxol.size()) {
				out += auxol.get(i).getValue();
			} else {
				out += auxol.get(i).getValue() + "+";
			}
		}
		
		
		
		return out;
		
	}
	
	public Automaton toAutomaton(String regex) {
		Automaton a = new Automaton();
		String value = "q";
		int count = 0;
		
		if (regex == null || regex.isEmpty()) {
			return null;
		}
		
		for (int i = 0; i < regex.length(); i++) {
			if (regex.charAt(i) == '.') {
				State s1 = new State(automaton, value + count);
				count++;
				State s2 = new State(automaton, value + count);
				count++;
				a.addState(s1);
				a.addState(s2);
				a.createTransition(regex.substring(i-1, i), s1, s2);
			}
		}
		
		return null;
	}
}
