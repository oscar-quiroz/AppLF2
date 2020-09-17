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
	
	/**
	 * Convierte una expresión regular en un autómata
	 * @param regex expresión regular a convertir
	 * @return a autómata
	 */
	public Automaton toAutomaton(String regex) {
		Automaton a = new Automaton();
		List<State> aux = new ArrayList<State>();
		String value = "q";
		int count = 0;
		
		if (regex == null || regex.isEmpty()) {
			return null;
		}
		
		for (int i = 0; i < regex.length(); i++) { //Recorre la regex
			if (regex.charAt(i) == '*') { //Verifica si es bucle
				if (regex.charAt(i-1) == ')') { //Verifica si en la anterior posición hay un )
					a.createTransition("x", a.getTransitions().get(a.getTransitions().size()-1).getEndState(), //Coge el último estado
						a.getTransitions().get(searchPositionParenthesis2(regex, i)-  //Busca la transición después del ( y coge el estado
								countSymbols(regex, searchPositionParenthesis2(regex, i))).getStartState()); //de inicial
				} else { //Si no hay un ) antes, crea un bucle normal
					a.createTransition(regex.substring(i-1, i), aux.get(aux.size()-1));
				}
			} else if (regex.charAt(i) == '+') { //Si hay un +
				State s1 = new State(automaton, value + count);
				count++;
				a.addState(s1);
				aux.add(s1);
				State saux = a.getTransitions().get(a.getTransitions().size()-1).getEndState(); //Crea un estado y lo une
				int y = i-1;
				int c = 0;
				while (!isSymbol(regex.charAt(y))) { //Cuenta las concatenaciones antes del +
					c++;
					y--;
					if (y <= 0) 
						break;
				}
				a.createTransition(regex.substring(i+1, i+2), a.getTransitions().get(a.getTransitions().size()-c).getStartState(), s1);
				//Crea las transiciones antes del más
				int x = i + 2;
				
				if (x < regex.length())
				while (!isSymbol(regex.charAt(x))) { //Crea las concatenaciones depués del más hasta que haya un bucle o ()
					State s3 = new State(automaton, value + count);
					count++;
					a.createTransition(regex.substring(x, x+1), aux.get(aux.size()-1), s3);
					a.addState(s3);
					aux.add(s3);
					x++;
					if (x >= regex.length()) //Sale porque sí :p
						break;
				}
				State s2 = new State(automaton, value + count); //estado para las transiones vacías
				count++;
				a.addState(s2);
				a.createTransition("λ", aux.get(aux.size()-1),	s2);
				aux.add(s2);
				a.createTransition("λ", saux, s2);
				i = x;
			} else { // Si no es un + o *
				if (regex.charAt(i) != '(' && regex.charAt(i) != ')') { //Si tampoco es un ( y )
					if (aux.isEmpty()) { //Si es la primera concatenacion
						State s1 = new State(automaton, value + count);
						s1.setInit(true);
						count++;
						a.addState(s1);
						aux.add(s1);
						if (i+1 < regex.length()) {
							if (regex.charAt(i+1) != '*') { //Si en la siguiente posición no hay un bucle
								State s2 = new State(automaton, value + count);
								count++;
								a.addState(s2);
								aux.add(s2);
								a.createTransition(regex.substring(i, i+1), s1, s2);
							} else { // Si en la siguiente posición hay un bucle
								a.createTransition(regex.substring(i, i+1), aux.get(aux.size()-1));
								count++;
							}
						} else {
							State s2 = new State(automaton, value + count);
							count++;
							a.addState(s2);
							aux.add(s2);
							a.createTransition(regex.substring(i, i+1), s1, s2);
						}
					} else { //si la posición es un ( o )
						if ((i+1) < regex.length()) { //Si no es el último elemento
							if (regex.charAt(i+1) != '*') {// si la siguiente posición no es bucle
								State s3 = new State(automaton, value + count);
								count++;
								a.addState(s3);
								a.createTransition(regex.substring(i, i+1), aux.get(aux.size()-1), s3);
								aux.add(s3);
							}
						} else { //Si es el último elemento
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
