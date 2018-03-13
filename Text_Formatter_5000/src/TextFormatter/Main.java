package TextFormatter;

import TextFormatter.GUI;

public class Main {

	public static void main(String[] args)
	{
		GUI gui = new GUI();
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				gui.createAndShowGUI();
			}
		});
	}

}
