/*
This code was written by Patrick Archer.  It is used to analyze input strings and provide data to a user about said strings.
- Patrick Archer // 1208960164 // pjarcher // Patrick.Archer@asu.edu -
 */

package textformatter;    // may or may not need this; comment in/out as necessary
//import java.lang.*;

public class Analyzer
{
    //private static int inputLineCount = 0;

	private static int wordCount = 0;
	private static int outputLineCount = 0;
	private static int blankLinesRemoved = 0;
	private static int totalLineLength = 0;

	private static double avgWordsPerLine = 0;
	private static double avgLineLength = 0;

    //private static String inputLine;    // temp var to store current input String[] line
	private static String outputLine;   // temp var to store current output String[] line
	
	public int getWordCount() {return wordCount;}
	public int getLineCount() {return outputLineCount;}
	public int getBlankLinesRemoved() {return blankLinesRemoved;}
	public double getAvgWordsPerLine() {return avgWordsPerLine;}
	public double getAvgLineLength() {return avgLineLength;}
	
	// inputLines is an array of the original, unformatted text.  This is only needed to figure out how many blank
	// lines were removed.
	// outputLines is an array of the formatted text.
	// Each element in the arrays is one line of text.
	public void performAnalysis(String[] inputLines, String[] outputLines)
	{
		wordCount = 0;
		outputLineCount = 0;
		blankLinesRemoved = 0;
		avgWordsPerLine = 0;
		avgLineLength = 0;
		totalLineLength = 0;
	    //*************************************************
		/*  Deal with wordCount */
        //*************************************************
		for (int i = 0; i < outputLines.length && outputLines[i] != null; i++)  // "For each full string, do..."
        {
            outputLineCount++;    // used in lineCount function

            // inputLine = inputLines[i];
            outputLine = outputLines[i];
            boolean atFirstCell = true;     // used to determine if analytic "pointer" has moved off first char cell or not

            wordCount++;    // initially assume a word is being passed; in special cases, decrement wordCount if no words, etc.
            // Due to the way the method is set up, this increment is meant to account for losing the count on
            // the last word in the string.  If possible, adjust code so this is taken care of in an if statement, below.

            if (outputLine == null)	// error check for totally null entries passed
			{
                wordCount = wordCount - 1;
                // since wordCount is initially incremented, this undoes that, because there are no words in this case
			}
			else if (outputLine.equals(' ') || outputLine.equals(" ") || outputLine.equals("  "))
				// ** error catch for if entire string is simply a single space char; alter so doesn't only catch 1 or 2 space
				// chars, but rather up to 80 of them **

			    //********************************
			    // Do not need to worry about at this time, as this case is taken care of in the formatter.
			    //********************************
			{
				// One or more strings provided does not contain any text and is either blank or consists of only space characters.
				wordCount = wordCount - 1;	// do not change the count; same reason as for "if (outputLine == null)" case
			}
			else    // else, valid input string; good to go
			{
                atFirstCell = true;     // used to determine if moved off first char cell or not

				for (int j = 0; j < outputLine.length(); j++) // "For each char in the single string, do..."
				{
				    if (Character.isSpaceChar(outputLine.charAt(j)))   // char in string is a space
                    {
                        if (atFirstCell == true)   // if space @ beginning of string, do...
                        {
                            while (outputLine.charAt(j) == ' ' && j < outputLine.length())
                            // parse thru consecutive space chars until letter, number, or symbol found; cell location saved to var < j >
                            {
                                j++;
                            }
                            j = j - 1;  // adjusts j's value so the main for loop works correctly
                            // wordCount++;    // a word has been found
                            atFirstCell = false;    // we aren't at the first cell anymore (since/after j is incremented)
                        }
                        else if (atFirstCell == false && Character.isDefined(outputLine.charAt(j+1)))
                        // space is somewhere in middle of string, but not at beginning or end; we have found a word
                        {
                            while (outputLine.charAt(j) == ' ' && j < outputLine.length())
                            // parse thru consecutive space chars until letter, number, or symbol found; cell location saved to var < j >
                            {
                                j++;
                            }
                            j = j - 1;  // adjusts j's value so the main for loop works correctly
                            wordCount++;    // a word has been found
                        }
                        else    // space is @ the very end of the string (or multiple spaces are)
                        {
                            while (outputLine.charAt(j) == ' ' && j < outputLine.length())
                            // parse thru consecutive space chars until letter, number, or symbol found; cell location saved to var < j >
                            {
                                j++;
                            }
                            j = j - 1;  // adjusts j's value so the main for loop works correctly
                        }
                    }
                    else
                    {
                        atFirstCell = false;    // we aren't at the first cell anymore (letter/number/symbol discovered; analyze next char)
                    }
				}
			}

            // @ this point, the whole line has been parsed & the wordCount has been incremented accordingly.
            // Now must move on to next string and do the same thing.  Note that wordCount does not get reset.

        }
		
		//Word count is acting a little buggy.  Trying this out to see if it gives more accurate results.
		wordCount = WordSegmenter.segmentWords(outputLines).length;

        //*************************************************
        /*  Deal with lineCount */
        //*************************************************
        /*
        Line count is dealt with in the above function.  A counter is simply incremented each time a new line
        is starting to be analyzed.  The public variable "outputLineCount" is updated each time.
         */

        //*************************************************
        /*  Deal with blankLinesRemoved */
        //*************************************************
		for (int f = 0; f < inputLines.length && inputLines[f] != null; f++)
        {
            //inputLineCount++;
        	if(inputLines[f].trim().isEmpty())
        		blankLinesRemoved++;
        }

        //blankLinesRemoved = inputLineCount - outputLineCount;   // delta( outputLineCount , inputLineCount ) = numLinesRemoved

        //*************************************************
        /*  Deal with avgWordsPerLine   */
        //*************************************************
        avgWordsPerLine = (double) wordCount / (double) outputLineCount;

        //*************************************************
        /*  Deal with avgLineLength */
        //*************************************************
        String tempLine;
        for (int m = 0; m < outputLines.length && outputLines[m] != null; m++)  // "For each full string, do..."
        {
            boolean atStartOfString = true; // flag for if we are at the first char in the string
            tempLine = outputLines[m];
            for (int k = 0; k < tempLine.length(); k++)
            {
                if (atStartOfString == true)   // if currently analyzing char in the first cell in string[]
                {
                    if (tempLine.charAt(k) == ' ')   // if space char @ beginning of string
                    {
                        while (tempLine.charAt(k) == ' ' && k < tempLine.length())  // iterate to the first actual char (not a space)
                        {
                            k++;
                        }
                        k--;
                        totalLineLength--;
                    }
                /*
                else    // letter, number, or symbol at start; carry on like normal
                {
                    totalLineLength = outputLines[k].length() + totalLineLength;    // take string length of each string and update var
                }
                */
                    atStartOfString = false;
                }
                totalLineLength++; // = outputLines[k].length() + totalLineLength;    // take string length of each string and update var
            }
        }
        avgLineLength = (double) totalLineLength / (double) outputLineCount;    // calculate avg from total # of lines and total line length
	}
}