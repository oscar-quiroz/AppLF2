package test_model;

import javax.swing.JFrame;

import lf2.flap.models.entity.RegularExpresion;
import lf2.flap.views.CanvasRegex;
import lf2.flap.views.ViewConstants;

public class TestCanvas {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Test canvas");
		frame.setSize(ViewConstants.defaultSize);
		frame.setLocationRelativeTo(null);
		
		RegularExpresion regex = new RegularExpresion("");
		
		CanvasRegex c = new CanvasRegex(regex.toAutomaton("a*(b+c)d"));
		
		frame.add(c);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
