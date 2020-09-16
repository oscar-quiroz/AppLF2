package lf2.flap.views.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import lf2.flap.models.entity.Automaton;
import lf2.flap.models.entity.RegularExpresion;
import lf2.flap.views.CanvasRegex;
import lf2.flap.views.ViewConstants;

public class MenubarListener implements ActionListener {
	private static MenubarListener menubarListener = null;
	
	private MenubarListener() {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (MenubarCommands.valueOf(e.getActionCommand())) {
		case GENRT_AUTOM:
			String regex = JOptionPane.showInputDialog(null, "Introduzca la expresión regular");
			RegularExpresion r = new RegularExpresion(regex);
			JFrame frame = new JFrame("Test canvas");
			frame.setSize(ViewConstants.defaultSize);
			frame.setLocationRelativeTo(null);
			CanvasRegex c = new CanvasRegex(r.toAutomaton(regex));
			
			frame.add(c);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
			break;
		case GENRT_REGEX:
			System.out.println("Generacion de regex");
			break;
		
		
		default:
			System.out.println("El comando " + e.getActionCommand() + " no tiene función.");
			break;
		}
	}
	
	public static MenubarListener getInstance() {
		if(menubarListener == null)
			menubarListener = new MenubarListener();
			
		return menubarListener;
	}
}
