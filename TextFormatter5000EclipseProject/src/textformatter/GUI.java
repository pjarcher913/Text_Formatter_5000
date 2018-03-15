package textformatter;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.*;

public class GUI
{
	//These are all of the different elements of the gui.
	private JFrame window;
	private static JTextPane inputTextPane;
	private JTextPane outputTextPane;
	private JScrollPane inputScrPane;
	private JScrollPane outputScrPane;
	
	private JButton loadButton;
	private JButton saveButton;
	private JButton formatButton;
	
	private ButtonGroup group;
	private JRadioButton leftJustifyButton;
	private JRadioButton rightJustifyButton;
	
	private JLabel wordCountLabel;
	private JLabel lineCountLabel;
	private JLabel blankLinesLabel;
	private JLabel avgWordsLabel;
	private JLabel avgLineLabel;
	
	private JPanel inputPanel;
	private JPanel optionsPanel;
	private JPanel outputPanel;
	private JPanel analysisPanel;
	
	static class LoadAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			JFileChooser chooser = new JFileChooser();
			chooser.showOpenDialog(null);
			File f = chooser.getSelectedFile();
			String filename = f.getAbsolutePath();
			
			try
			{
				FileReader reader = new FileReader(filename);
				BufferedReader br = new BufferedReader(reader);
				inputTextPane.read(br, null);
				br.close();
			}
			catch(Exception ex)
			{
				JOptionPane.showMessageDialog(null, ex);
			}
		}
	}
	
	//TODO: Implement action listeners for Format, Save, and Justify buttons
	
	public void createAndShowGUI()
	{
		window = new JFrame("Text Formatter 5000");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Input panel setup
		inputPanel = new JPanel();
		
		BoxLayout inputLayout = new BoxLayout(inputPanel, BoxLayout.Y_AXIS);
		inputPanel.setLayout(inputLayout);
		
		loadButton = new JButton();
		loadButton.setText("Open file");
		loadButton.addActionListener(new LoadAction());
		inputTextPane = new JTextPane();
		inputTextPane.setEditable(false);
		inputTextPane.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		inputTextPane.setPreferredSize(new Dimension(575,150));
		inputScrPane = new JScrollPane(inputTextPane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		inputPanel.add(loadButton);
		inputPanel.add(inputScrPane);
		
		//options panel setup
		optionsPanel = new JPanel();
		
		BoxLayout optionsLayout = new BoxLayout(optionsPanel, BoxLayout.X_AXIS);
		optionsPanel.setLayout(optionsLayout);
		
		leftJustifyButton = new JRadioButton("Left Justify");
		rightJustifyButton = new JRadioButton("Right Justify");
		group = new ButtonGroup();
		group.add(leftJustifyButton);
		group.add(rightJustifyButton);
		leftJustifyButton.setSelected(true);
		formatButton = new JButton("Format");
		optionsPanel.add(leftJustifyButton);
		optionsPanel.add(rightJustifyButton);
		optionsPanel.add(formatButton);
		
		//output panel setup
		outputPanel = new JPanel();
		BoxLayout outputLayout = new BoxLayout(outputPanel, BoxLayout.Y_AXIS);
		outputPanel.setLayout(outputLayout);
		outputTextPane = new JTextPane();
		outputTextPane.setEditable(false);
		outputTextPane.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		outputTextPane.setPreferredSize(new Dimension(575,150));
		outputScrPane = new JScrollPane(outputTextPane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		outputPanel.add(outputScrPane);
		
		//analysis panel setup
		analysisPanel = new JPanel();
		BoxLayout analysisLayout = new BoxLayout(analysisPanel, BoxLayout.Y_AXIS);
		analysisPanel.setLayout(analysisLayout);
		saveButton = new JButton("Save File");
		wordCountLabel = new JLabel("Word count: 0");
		lineCountLabel = new JLabel("Lines: 0");
		blankLinesLabel = new JLabel("Blank lines removed: 0");
		avgWordsLabel = new JLabel("Average words per line: 0");
		avgLineLabel = new JLabel("Average line length: 0");
		analysisPanel.add(wordCountLabel);
		analysisPanel.add(lineCountLabel);
		analysisPanel.add(blankLinesLabel);
		analysisPanel.add(avgWordsLabel);
		analysisPanel.add(avgLineLabel);
		analysisPanel.add(saveButton);
		
		//Set up window
		BoxLayout windowLayout = new BoxLayout(window.getContentPane(), BoxLayout.Y_AXIS);
		window.getContentPane().setLayout(windowLayout);
				
		window.getContentPane().add(inputPanel);
		window.getContentPane().add(optionsPanel);
		window.getContentPane().add(outputPanel);
		window.getContentPane().add(analysisPanel);
		window.pack();
		window.setVisible(true);
	}
	
}