package lf2.flap.views;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;

import lf2.flap.models.entity.Automaton;
import lf2.flap.models.entity.State;
import lf2.flap.models.entity.Transition;
import lf2.flap.views.menus.CanvasPopupMenu;

public class CanvasRegex extends JPanel {
	private Automaton automaton;
	private Point mousePoint, statePoint;
	private CanvasPopupMenu canvasPopupMenu;
	private List<StateFigure2> list;
	private List<LineFigure> listLine;

	public CanvasRegex(Automaton automaton) {
		this.automaton = automaton;
		this.canvasPopupMenu = new CanvasPopupMenu();
		list = new ArrayList<StateFigure2>();
		listLine = new ArrayList<LineFigure>();
		toAutomatom();
		this.init();
	}

	private void init() {
		this.add(this.canvasPopupMenu);
//		this.add(this.popupInputMenu);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		for (LineFigure lineFigure : listLine) {
			lineFigure.drawLine(g, lineFigure.getValue());
		}

		for (StateFigure2 stateFigure2 : list) {
			stateFigure2.draw(g);
		}
		
		if (mousePoint != null && statePoint != null)
			g.drawLine(mousePoint.x, mousePoint.y, statePoint.x, statePoint.y);

	}
	
	public void toAutomatom() {
		for (State s : this.automaton.getStates()) {
			if (s.isInit()) {
				StateFigure2 sf = new StateFigure2(automaton, s);
				sf.x = 70;
				sf.y = 70;
				sf.setLabel(s.getLabel());
				list.add(sf);
				for (Transition t : s.getOutTransitions()) {
					LineFigure lf = new LineFigure(t, new Point((int)sf.x,(int)sf.y)); 
					listLine.add(lf);
				}
			} else {	
				Random rnd = new Random();
				StateFigure2 sf = new StateFigure2(automaton, s);
				sf.x = rnd.nextInt(400) + 100;
				sf.y = rnd.nextInt(400) + 100;
				sf.setLabel(s.getLabel());
				list.add(sf);
				for (Transition t : s.getOutTransitions()) {
					LineFigure lf = new LineFigure(t, new Point((int)sf.x,(int)sf.y));
					listLine.add(lf);
				}
			}

		}
		
		for (StateFigure2 s : list) {
			for (Transition t : s.getOutTransitions()) {
				for (LineFigure l : listLine) {
					if (t.getValue().equals(l.getValue())) {
						for (StateFigure2 stateFigure2 : list) {
							if (t.getEndState().getLabel().equals(stateFigure2.getLabel())) {
								l.setF(new Point((int) stateFigure2.x, (int) stateFigure2.y));
 							}
						}
					}
				}
			}
		}
		
	}
	
	public StateFigure2 getStateFigureMouse(Point p) {
		StateFigure2 f = null, aux;

		for (State s : this.automaton.getStates()) {
			aux = new StateFigure2(automaton, s);
			if (aux.isMouseOver(p))
				f = aux;
		}

		return f;
	}
	
	public void setLinePoints(Point mousePoint, Point statePoint) {
		this.mousePoint = mousePoint;
		this.statePoint = statePoint;
	}
	
	public CanvasPopupMenu getCanvasPopupMenu() {
		return canvasPopupMenu;
	}
	
	
	public Automaton getAutomaton() {
		return automaton;
	}
	
	public void createNode(int x, int y) {
		this.createNode(new Point(x, y));
	}
	
	public void createNode(Point p) {
//		StateFigure2 figure = new StateFigure2();
//		figure.setX(p.x);
//		figure.setY(p.y);
//		figure.setLabel("q" + this.automaton.getStates().size());
//		this.automaton.getStates().add(figure);
	}
	
}
