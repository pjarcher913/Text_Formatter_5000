package textformatter;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GUI
{
	//These are all of the different elements of the gui.
	private JFrame window;
	private static JTextPane inputTextPane;
	private static JTextPane outputTextPane;
	private JScrollPane inputScrPane;
	private JScrollPane outputScrPane;
	
	private JButton loadButton;
	private JButton saveButton;
	private JButton formatButton;
	
	private ButtonGroup group;
	private JRadioButton leftJustifyButton;
	private JRadioButton rightJustifyButton;
	
	private static JLabel wordCountLabel;
	private static JLabel lineCountLabel;
	private static JLabel blankLinesLabel;
	private static JLabel avgWordsLabel;
	private static JLabel avgLineLabel;
	
	private JPanel inputPanel;
	private JPanel optionsPanel;
	private JPanel outputPanel;
	private JPanel analysisPanel;
	
	private static int justificationFlag;
	private static boolean fileLoaded;
	private static boolean fileFormatted;
	
	public GUI()
	{
		justificationFlag = 0;
		fileLoaded = false;
		fileFormatted = false;
	}
	
	private static class LoadAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
			JFileChooser chooser = new JFileChooser();
			chooser.setFileFilter(filter);
			chooser.showOpenDialog(null);
			File f = chooser.getSelectedFile();
			if(f == null)	//No file was selected
				return;
			String filename = f.getAbsolutePath();
			
			//Double check that the file is indeed a .txt file.
			if(!filename.endsWith("txt"))
			{
				//Open error box
				JOptionPane.showMessageDialog(inputTextPane, "Please choose a file with the .txt extension.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			try
			{
				FileReader reader = new FileReader(filename);
				BufferedReader br = new BufferedReader(reader);
				inputTextPane.read(br, null);
				br.close();
				fileLoaded = true;
			}
			catch(Exception ex)
			{
				JOptionPane.showMessageDialog(null, ex);
			}
		}
	}
	
	private static class JustificationAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			JRadioButton button = (JRadioButton) e.getSource();
			if(button.getText().equals("Left Justify"))
				justificationFlag = 0;
			else
				justificationFlag = 1;
		}
	}
	
	private static class FormatAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(!fileLoaded)
			{
				JOptionPane.showMessageDialog(inputTextPane, "Please open a file first!", "Not so fast!", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			
			//Build the inputArray
			String text = inputTextPane.getText();
			int lineCount = 1;
			for(int i = 0; i < text.length(); i++)
			{
				if(text.charAt(i) == '\n')
					lineCount++;
			}
			
			String[] inputArray = new String[lineCount];
			for(int i = 0; i < lineCount; i++)
				inputArray[i] = "";
			int curLocation = 0;
			for(int i = 0; i < lineCount; i++)
			{
				while(curLocation < text.length() && text.charAt(curLocation) != '\n')
				{
					inputArray[i] += text.charAt(curLocation);
					curLocation++;
				}
				++curLocation;
			}
			//Chop off last char of the strings
			for(int i = 0; i < lineCount; i++)
			{
				if(inputArray[i].length() > 0)
					inputArray[i] = inputArray[i].substring(0, inputArray[i].length() - 1);
			}

			//NEED OTHER FILES DONE
			/*
			Formatter form = new Formatter();
			String[] outputArray = form.format(inputArray, justificationFlag);
			Analyzer ana = new Analyzer();
			ana.performAnalysis(inputArray, outputArray);
			
			wordCountLabel.setText("Word count: " + Integer.toString(ana.getWordCount()));
			lineCountLabel.setText("Lines: " + Integer.toString(ana.getLineCount()));
			blankLinesLabel.setText("Blank lines removed: " + Integer.toString(ana.getBlankLinesRemoved()));
			avgWordsLabel.setText("Average words per line: " + Double.toString(ana.getAvgWordsPerLine()));
			avgLineLabel.setText("Average line length: " + Double.toString(ana.getAvgLineLength()));
			
			//Build output text string
			text = "";
			for(int i = 0; i < outputArray.length; i++)
			{
				text += outputArray[i];
				if(i != outputArray.length - 1)
					text += "\n";
			}
			outputTextPane.setText(text);
			*/
			
			fileFormatted = true;
		}
	}
	
	private static class SaveAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(!fileLoaded)
			{
				JOptionPane.showMessageDialog(inputTextPane, "Please open a file first!", "Not so fast!", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			else if(!fileFormatted)
			{
				JOptionPane.showMessageDialog(inputTextPane, "Please click the Format button first!", "Not so fast!", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
			JFileChooser chooser = new JFileChooser();
			chooser.setFileFilter(filter);
			chooser.showSaveDialog(null);
			File f = chooser.getSelectedFile();
			if(f == null)
				return;
			String filename = f.getAbsolutePath();
			System.out.println(filename);
			
			//Double check that the file is indeed a .txt file.
			if(!filename.endsWith("txt"))
			{
				//Open error box
				JOptionPane.showMessageDialog(inputTextPane, "Please end the filename with the .txt extension.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			try
			{
				PrintWriter writer = new PrintWriter(filename);
				writer.print(outputTextPane.getText());
				writer.close();
			}
			catch(Exception ex)
			{
				JOptionPane.showMessageDialog(null, ex);
			}
		}
	}
	
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
		JustificationAction justListener = new JustificationAction();
		leftJustifyButton.addActionListener(justListener);
		rightJustifyButton.addActionListener(justListener);
		group = new ButtonGroup();
		group.add(leftJustifyButton);
		group.add(rightJustifyButton);
		leftJustifyButton.setSelected(true);
		formatButton = new JButton("Format");
		formatButton.addActionListener(new FormatAction());
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
		outputTextPane.setText("");
		outputScrPane = new JScrollPane(outputTextPane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		outputPanel.add(outputScrPane);
		
		//analysis panel setup
		analysisPanel = new JPanel();
		BoxLayout analysisLayout = new BoxLayout(analysisPanel, BoxLayout.Y_AXIS);
		analysisPanel.setLayout(analysisLayout);
		saveButton = new JButton("Save File");
		saveButton.addActionListener(new SaveAction());
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