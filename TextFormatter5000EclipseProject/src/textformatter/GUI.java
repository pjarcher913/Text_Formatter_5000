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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;


/*
 * TODO: implement listener for spacing buttons
 */

public class GUI
{
	private static String[] outputArray;
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
	private JRadioButton fullJustifyButton;
	
	private ButtonGroup spacingGroup;
	private JRadioButton singleSpaceButton;
	private JRadioButton doubleSpaceButton;
	
	private static JSlider lineSlider;
	
	private static JLabel wordCountLabel;
	private static JLabel lineCountLabel;
	private static JLabel blankLinesLabel;
	private static JLabel avgWordsLabel;
	private static JLabel avgLineLabel;
	private static JLabel spacesAddedLabel;
	private static JLabel sliderLabel;
	
	private JPanel inputPanel;
	private JPanel justifyPanel;
	private JPanel spacingPanel;
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
				inputTextPane.setPreferredSize(new Dimension(575,150));
				br.close();
				fileLoaded = true;
			}
			catch(Exception ex)
			{
				JOptionPane.showMessageDialog(inputTextPane, "Could not open the file.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private static class SliderAction implements ChangeListener
	{
		public void stateChanged(ChangeEvent e)
		{
			JSlider slider = (JSlider) e.getSource();
			int v = slider.getValue();
			sliderLabel.setText("Characters per line: " + Integer.toString(v));
		}
	}
	
	private static class JustificationAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			JRadioButton button = (JRadioButton) e.getSource();
			if(button.getText().equals("Left Justify"))
				justificationFlag = 0;
			else if(button.getText().equals("Right Justify"))
				justificationFlag = 1;
			//else
			//	justificationFlag = 2;
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
			
			//Replace /t with spaces.  Should be done in formatter, but will be more convenient to do it here.
			text = text.replace('\t', ' ');
			
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
				if(inputArray[i].length() > 0 && i != lineCount - 1)
					inputArray[i] = inputArray[i].substring(0, inputArray[i].length() - 1);
			}
			
			//TEST WORDSEGMENTER
			/*
			String[] words = WordSegmenter.segmentWords(inputArray);
			for(int i = 0; i < words.length; i++)
			{
				System.out.print(words[i] + " ");
			}
			*/
			//END TEST WORDSEGMENTER

			//NEED OTHER FILES DONE
			
			Formatter form = new Formatter();
			//outputArray = form.format(inputArray, justificationFlag);
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
			outputTextPane.setPreferredSize(new Dimension(575,150));
			
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
				for(int i = 0; i < outputArray.length; i ++)
				{
					writer.println(outputArray[i]);
				}
				writer.close();
			}
			catch(Exception ex)
			{
				JOptionPane.showMessageDialog(inputTextPane, "Could not save file to the desired location.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public void createAndShowGUI()
	{
		window = new JFrame("Text Formatter 5001");
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
		justifyPanel = new JPanel();
		
		BoxLayout optionsLayout = new BoxLayout(optionsPanel, BoxLayout.Y_AXIS);
		optionsPanel.setLayout(optionsLayout);
		
		BoxLayout justifyLayout = new BoxLayout(justifyPanel, BoxLayout.X_AXIS);
		justifyPanel.setLayout(justifyLayout);
		
		sliderLabel = new JLabel("Characters per line: 80");
		
		lineSlider = new JSlider(JSlider.HORIZONTAL, 20, 100, 80);
		lineSlider.setMinorTickSpacing(1);
		lineSlider.setMajorTickSpacing(5);
		lineSlider.setPaintTicks(true);
		lineSlider.setPaintLabels(true);
		lineSlider.addChangeListener(new SliderAction());
		
		leftJustifyButton = new JRadioButton("Left Justify");
		rightJustifyButton = new JRadioButton("Right Justify");
		fullJustifyButton = new JRadioButton("Full Justify");
		
		JustificationAction justListener = new JustificationAction();
		
		leftJustifyButton.addActionListener(justListener);
		rightJustifyButton.addActionListener(justListener);
		fullJustifyButton.addActionListener(justListener);
		
		group = new ButtonGroup();
		group.add(leftJustifyButton);
		group.add(rightJustifyButton);
		group.add(fullJustifyButton);
		leftJustifyButton.setSelected(true);
		
		singleSpaceButton = new JRadioButton("Single spaced");
		doubleSpaceButton = new JRadioButton("Double spaced");
		
		spacingGroup = new ButtonGroup();
		spacingGroup.add(singleSpaceButton);
		spacingGroup.add(doubleSpaceButton);
		singleSpaceButton.setSelected(true);
		
		spacingPanel = new JPanel();
		BoxLayout spacingLayout = new BoxLayout(spacingPanel, BoxLayout.X_AXIS);
		spacingPanel.setLayout(spacingLayout);
		spacingPanel.add(singleSpaceButton);
		spacingPanel.add(doubleSpaceButton);
		
		formatButton = new JButton("Format");
		formatButton.addActionListener(new FormatAction());
		
		justifyPanel.add(leftJustifyButton);
		justifyPanel.add(rightJustifyButton);
		justifyPanel.add(fullJustifyButton);
		optionsPanel.add(sliderLabel);
		optionsPanel.add(lineSlider);
		optionsPanel.add(justifyPanel);
		optionsPanel.add(spacingPanel);
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
		
		JPanel container = new JPanel();
		JPanel leftPanel = new JPanel();
		JPanel rightPanel = new JPanel();
		
		BoxLayout containerLayout = new BoxLayout(container, BoxLayout.X_AXIS);
		BoxLayout leftLayout = new BoxLayout(leftPanel, BoxLayout.Y_AXIS);
		BoxLayout rightLayout = new BoxLayout(rightPanel, BoxLayout.Y_AXIS);
		
		leftPanel.setLayout(leftLayout);
		rightPanel.setLayout(rightLayout);
		container.setLayout(containerLayout);
		
		analysisPanel.setLayout(analysisLayout);
		saveButton = new JButton("Save File");
		saveButton.addActionListener(new SaveAction());
		wordCountLabel = new JLabel("Word count: 0");
		lineCountLabel = new JLabel("Lines: 0");
		blankLinesLabel = new JLabel("Blank lines removed: 0");
		avgWordsLabel = new JLabel("Average words per line: 0");
		avgLineLabel = new JLabel("Average line length: 0");
		spacesAddedLabel = new JLabel("Spaces added: 0");
		
		leftPanel.add(wordCountLabel);
		leftPanel.add(lineCountLabel);
		leftPanel.add(blankLinesLabel);
		rightPanel.add(avgWordsLabel);
		rightPanel.add(avgLineLabel);
		rightPanel.add(spacesAddedLabel);
		
		container.add(leftPanel);
		container.add(rightPanel);
		
		analysisPanel.add(container);
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