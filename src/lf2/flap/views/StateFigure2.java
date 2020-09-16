package lf2.flap.views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import lf2.flap.models.entity.Automaton;
import lf2.flap.models.entity.State;
import lf2.flap.models.entity.Transition;

public class StateFigure2 extends State {
	public static int RADIUS = 20;
	protected float x;
	protected float y;
	private boolean isSelected = false;;

	public StateFigure2(Automaton automaton, State s) {
		super(automaton);
		this.inTransitions = s.getInTransitions();
		this.outTransitions = s.getOutTransitions();
		this.isFinal = s.isFinal(); 
		this.isInit = s.isInit();
		this.label = s.getLabel();
	}

	public void draw(Graphics g) {
		g.setColor(ViewConstants.stateColor);

		if (isSelected)
			g.setColor(ViewConstants.selectedStateColor);

		g.fillOval((int) x - RADIUS, (int) y - RADIUS, RADIUS * 2, RADIUS * 2);
		g.setColor(Color.BLACK);
		g.drawOval((int) x - RADIUS, (int) y - RADIUS, RADIUS * 2, RADIUS * 2);
		g.drawString(this.label, (int) x - 5, (int) y + 5);

		if (this.isFinal) {
			g.drawOval((int) x - RADIUS + 3, (int) y - RADIUS + 3, RADIUS * 2 - 6, RADIUS * 2 - 6);
		}

		if (this.isInit) {
			g.drawPolygon(new int[] { (int) (x - RADIUS * 2), (int) (x - RADIUS), (int) (x - RADIUS * 2) },
					new int[] { (int) (y - RADIUS), (int) y, (int) (y + RADIUS) }, 3);
		}
	}

	public void drawLine(Graphics g, StateFigure2 f, String value) {
		if (f == this) {
			g.drawArc((int) (x - RADIUS / 2), (int) (y - RADIUS * 5 / 2), RADIUS, RADIUS * 2, 320, 260);
			g.drawString(value, (int) (x), (int) (y - RADIUS * 5 / 2));
			g.drawPolyline(
					new int[] { (int) (x - RADIUS * 9 / 10), (int) (x - RADIUS * 2 / 5), (int) (x - RADIUS / 5) },
					new int[] { (int) (y - RADIUS * 5 / 4), (int) (y - RADIUS * 9 / 10), (int) (y - RADIUS * 7 / 5) },
					3);
		} else {
			double relRadius = 1 - RADIUS / getPosition().distance(f.getPosition());
			double relArrow = 1 - (RADIUS * 1.5) / getPosition().distance(f.getPosition());
			double slope = -(this.x - f.x) / (this.y - f.y);

			g.drawLine((int) this.x, (int) this.y, (int) (x + (f.x - x) * relRadius),
					(int) (y + (f.y - y) * relRadius));

			int xm = (int) (x + (f.x - x) * relArrow);
			int ym = (int) (y + (f.y - y) * relArrow);

			int x1, y1, x2, y2, arrowWidth = 5;

			if (slope <= 1 && slope >= -1) {
				x1 = xm + arrowWidth;
				y1 = (int) (slope * (x1 - xm) + ym);
				x2 = xm - arrowWidth;
				y2 = (int) (slope * (x2 - xm) + ym);
			} else {
				y1 = ym + arrowWidth;
				x1 = (int) ((y1 - ym) / slope + xm);
				y2 = ym - arrowWidth;
				x2 = (int) ((y2 - ym) / slope + xm);
			}

			g.drawPolyline(new int[] { x1, (int) (x + (f.x - x) * relRadius), x2 },
					new int[] { y1, (int) (y + (f.y - y) * relRadius), y2 }, 3);
			g.drawString(value, (int) (x + (f.x - x) * relRadius / 2), (int) (y + (f.y - y) * relRadius / 2));
		}
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isMouseOver(Point p) {
		return Math.hypot(Math.abs(p.x - this.x), Math.abs(p.y - this.y)) <= RADIUS;
	}

	public void setPosition(Point p) {
		this.x = p.x;
		this.y = p.y;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public Point getPosition() {
		return new Point((int) this.x, (int) this.y);
	}
}
