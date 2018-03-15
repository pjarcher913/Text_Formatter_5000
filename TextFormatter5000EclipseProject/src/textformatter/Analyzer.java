package textformatter;

public class Analyzer
{
	private int wordCount;
	private int lineCount;
	private int blankLinesRemoved;
	private int avgWordsPerLine;
	private int avgLineLength;
	
	public int getWordCount() {return wordCount;}
	public int getLineCount() {return lineCount;}
	public int getBlankLinesRemoved() {return blankLinesRemoved;}
	public int getAvgWordsPerLine() {return avgWordsPerLine;}
	public int getAvgLineLength() {return avgLineLength;}
	
	// inputLines is an array of the original, unformatted text.  This is only needed to figure out how many blank
	// lines were removed.
	// outputLines is an array of the formatted text.
	// Each element in the arrays is one line of text.
	public void performAnalysis(String[] inputLines, String[] outputLines)
	{
		
	}
}
