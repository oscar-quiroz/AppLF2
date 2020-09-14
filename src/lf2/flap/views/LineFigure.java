package lf2.flap.views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import lf2.flap.models.entity.Automaton;
import lf2.flap.models.entity.State;
import lf2.flap.models.entity.Transition;

public class LineFigure extends Transition {
	
	public static int RADIUS = 20;
	protected Point a;
	protected Point f;
//	private boolean isSelected = false;

	public LineFigure(Transition t, Point a) {
		super(t.getValue(), t.getStartState(), t.getEndState());
		this.a = a;
	}

	public void drawLine(Graphics g, String value) {
		if (this.isRecursive()) {
			g.drawArc((int) (a.x - RADIUS / 2), (int) (a.y - RADIUS * 5 / 2), RADIUS, RADIUS * 2, 320, 260);
			g.drawString(value, (int) (a.x), (int) (a.y - RADIUS * 5 / 2));
			g.drawPolyline(
					new int[] { (int) (a.x - RADIUS * 9 / 10), (int) (a.x - RADIUS * 2 / 5), (int) (a.x - RADIUS / 5) },
					new int[] { (int) (a.y - RADIUS * 5 / 4), (int) (a.y - RADIUS * 9 / 10), (int) (a.y - RADIUS * 7 / 5) },
					3);
		} else {
			double relRadius = 1 - RADIUS / getPosition(a.x, a.y).distance(getPosition(f.x, f.y));
			double relArrow = 1 - (RADIUS * 1.5) / getPosition(a.x, a.y).distance(getPosition(f.x, f.y));
			double slope = -(a.x - f.x) / (a.y - f.y);

			g.drawLine((int) a.x, (int) a.y, (int) (a.x + (f.x - a.x) * relRadius),
					(int) (a.y + (f.y - a.y) * relRadius));

			int xm = (int) (a.x + (f.x - a.x) * relArrow);
			int ym = (int) (a.y + (f.y - a.y) * relArrow);

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

			g.drawPolyline(new int[] { x1, (int) (a.x + (f.x - a.x) * relRadius), x2 },
					new int[] { y1, (int) (a.y + (f.y - a.y) * relRadius), y2 }, 3);
			g.drawString(value, (int) (a.x + (f.x - a.x) * relRadius / 2), (int) (a.y + (f.y - a.y) * relRadius / 2));
		}
	}
	
	public void setF(Point f) {
		this.f = f;
	}

//	public void setX(int x) {
//		this.x = x;
//	}
//
//	public void setY(int y) {
//		this.y = y;
//	}
//
//	public boolean isMouseOver(Point p) {
//		return Math.hypot(Math.abs(p.x - this.x), Math.abs(p.y - this.y)) <= RADIUS;
//	}
//
//	public void setPosition(Point p) {
//		this.x = p.x;
//		this.y = p.y;
//	}
//
//	public void setSelected(boolean isSelected) {
//		this.isSelected = isSelected;
//	}
//
//	public boolean isSelected() {
//		return isSelected;
//	}

	public Point getPosition(int x, int y) {
		return new Point((int) x, (int) y);
	}
}
