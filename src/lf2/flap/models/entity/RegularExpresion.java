package lf2.flap.models.entity;

import java.util.ArrayList;
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
	
	/**
	 * Convierte una expresión regular en un autómata
	 * (encontrando el parantesis de apertura para un parentesis de cierre)
	 * @param regex
	 * @return
	 */
	public Automaton toAutomaton(String regex) {
		Automaton a = new Automaton();
		List<State> aux = new ArrayList<State>();
		String value = "q";
		int count = 0;
		
		if (regex == null || regex.isEmpty()) {
			System.out.println("Null");
			return null;
		}
		
		for (int i = 0; i < regex.length(); i++) {
			if (regex.charAt(i) == '*') {
				if (regex.charAt(i-1) == ')') {
					a.createTransition("x", a.getTransitions().get(a.getTransitions().size()-1).getEndState(),
							a.getTransitions().get(searchPositionParenthesis2(regex, i)- 
									countSymbols(regex, searchPositionParenthesis2(regex, i))).getStartState()); 
							
				} else {
					a.createTransition(regex.substring(i-1, i), aux.get(aux.size()-1));
				}
				
			} else if (regex.charAt(i) == '+') {
				State s1 = new State(automaton, value + count);
				count++;
				a.addState(s1);
				aux.add(s1);
				State saux = a.getTransitions().get(a.getTransitions().size()-1).getEndState();
				int y = i-1;
				int c = 0;
				while (!isSymbol(regex.charAt(y))) {
					c++;
					y--;
					if (y <= 0) 
						break;
				}
				a.createTransition(regex.substring(i+1, i+2), a.getTransitions().get(a.getTransitions().size()-c).getStartState(), 
						s1);
				
				int x = i + 2;
				if (x < regex.length())
				while (!isSymbol(regex.charAt(x))) {
					State s3 = new State(automaton, value + count);
					count++;
					a.createTransition(regex.substring(x, x+1), aux.get(aux.size()-1), s3);
					a.addState(s3);
					aux.add(s3);
					x++;
					if (x >= regex.length()) {
						break;
					}
				}
				State s2 = new State(automaton, value + count);
				count++;
				a.addState(s2);
				a.createTransition("λ", aux.get(aux.size()-1),	s2);
				aux.add(s2);
				a.createTransition("λ", saux, s2);
				i = x;
			} else {
				if (regex.charAt(i) != '(' && regex.charAt(i) != ')') {
					if (aux.isEmpty()) {
						if (regex.charAt(i+1) != '*') {
							State s1 = new State(automaton, value + count);
							s1.setInit(true);
							count++;
							State s2 = new State(automaton, value + count);
							count++;
							a.addState(s1);
							a.addState(s2);
							aux.add(s1);
							aux.add(s2);
							a.createTransition(regex.substring(i, i+1), s1, s2);
						} else {
							State s1 = new State(automaton, value + count);
							s1.setInit(true);
							a.addState(s1);
							aux.add(s1);
							a.createTransition(regex.substring(i, i+1), aux.get(aux.size()-1));
						}
					} else {
						if ((i+1) < regex.length()) {
							if (regex.charAt(i+1) != '*') {
								State s3 = new State(automaton, value + count);
								count++;
								a.addState(s3);
								a.createTransition(regex.substring(i, i+1), aux.get(aux.size()-1), s3);
								aux.add(s3);
								
							}
						} else {
							State s3 = new State(automaton, value + count);
							count++;
							a.addState(s3);
							a.createTransition(regex.substring(i, i+1), aux.get(aux.size()-1), s3);
							aux.add(s3);
						}
					}
				}
			}
			
		}
		a.getTransitions().get(a.getTransitions().size()-1).getEndState().isFinal = true;
		return a;
	}
	
	private int searchPositionParenthesis2(String regex, int pos) {
		int j = pos-2;
		int cont = 0;
		
		while(j>=0 && cont >=0) {
			if(regex.charAt(j)==')') {
				cont ++;				
			}
			else if(regex.charAt(j)=='(') {
				cont--;			
			}
			j--;
		}
		
		return j+1;
	}
	
	/*
	 * Encontrar la posición del parentesis de apertura 
	 * Contar la cantidad de simbolos antes de la posición del parentesis de apetura
	 * Restar la posición del parentesis con la cantidad de simbolos que han pasado
	 * (Complete)
	 */
	
	private int countSymbols(String regex, int pos) {
		int count = 0;
		for (int i = (pos-1); i >= 0; i--) {
			if (regex.charAt(i) == '(' || regex.charAt(i) == ')' || regex.charAt(i) == '*' || regex.charAt(i) == '+') {
				count++;
			}
		}
		return count;
	}
	
	private boolean isSymbol(char c) {
		return (c == '(' || c == ')' || c == '*' || c == '+');
	}
	
}
