/*
This code was written by Patrick Archer.  It is used to analyze input strings and provide data to a user about said strings.
- Patrick Archer // 1208960164 // pjarcher // Patrick.Archer@asu.edu -
 */

//package textformatter;    // may or may not need this; comment in/out as necessary
import java.lang.*;

public class Analyzer
{
	private static int wordCount = 0;
	private static int lineCount = 0;
	private static int blankLinesRemoved = 0;
	private static float avgWordsPerLine = 0;
	private static float avgLineLength = 0;

	private static String passedLine;   // used for testing purposes; remove
	
	public int getWordCount() {return wordCount;}
	public int getLineCount() {return lineCount;}
	public int getBlankLinesRemoved() {return blankLinesRemoved;}
	public float getAvgWordsPerLine() {return avgWordsPerLine;}
	public float getAvgLineLength() {return avgLineLength;}
	
	// inputLines is an array of the original, unformatted text.  This is only needed to figure out how many blank
	// lines were removed.
	// outputLines is an array of the formatted text.
	// Each element in the arrays is one line of text.
	public static void performAnalysis(String[] inputLines, String[] outputLines)
	{
	    //*************************************************
		/*  Deal with wordCount */
        //*************************************************
		for (int i = 0; i < outputLines.length && outputLines[i] != null; i++)  // "For each full string, do..."
        {
            lineCount++;    // used in lineCount function

            passedLine = outputLines[i];
            boolean atFirstCell = true;     // used to determine if analytic "pointer" has moved off first char cell or not

            wordCount++;    // initially assume a word is being passed; in special cases, decrement wordCount if no words, etc.
            // Due to the way the method is set up, this increment is meant to account for losing the count on
            // the last word in the string.  If possible, adjust code so this is taken care of in an if statement, below.

            if (passedLine == null)	// error check for totally null entries passed
			{
                wordCount = wordCount - 1;
                // since wordCount is initially incremented, this undoes that, because there are no words in this case
			}
			else if (passedLine.equals(' ') || passedLine.equals(" ") || passedLine.equals("  "))
				// ** error catch for if entire string is simply a single space char; alter so doesn't only catch 1 or 2 space
				// chars, but rather up to 80 of them **
			{
				//*****************
				// *** ?Delete warning message? ***
				//*****************
				System.out.println("\nWARNING: One or more strings provided does not contain any text" +
                                " and is either blank or consists of only space characters.\n");
				wordCount = wordCount - 1;	// do not change the count; same reason as for "if (passedLine == null)" case
			}
			else    // else, valid input string; good to go
			{
                atFirstCell = true;     // used to determine if moved off first char cell or not

				for (int j = 0; j < passedLine.length(); j++) // "For each char in the single string, do..."
				{
				    if (Character.isSpaceChar(passedLine.charAt(j)))   // char in string is a space
                    {
                        if (atFirstCell == true)   // if space @ beginning of string, do...
                        {
                            while (passedLine.charAt(j) == ' ' && j < passedLine.length())
                            // parse thru consecutive space chars until letter, number, or symbol found; cell location saved to var < j >
                            {
                                j++;
                            }
                            j = j - 1;  // adjusts j's value so the main for loop works correctly
                            wordCount++;    // a word has been found
                            atFirstCell = false;    // we aren't at the first cell anymore (since/after j is incremented)
                        }
                        else if (atFirstCell == false && Character.isDefined(passedLine.charAt(j+1)))
                        // space is somewhere in middle of string, but not at beginning or end; we have found a word
                        {
                            while (passedLine.charAt(j) == ' ' && j < passedLine.length())
                            // parse thru consecutive space chars until letter, number, or symbol found; cell location saved to var < j >
                            {
                                j++;
                            }
                            j = j - 1;  // adjusts j's value so the main for loop works correctly
                            wordCount++;    // a word has been found
                        }
                        else    // space is @ the very end of the string (or multiple spaces are)
                        {
                            while (passedLine.charAt(j) == ' ' && j < passedLine.length())
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

        //*************************************************
        /*  Deal with lineCount */
        //*************************************************
        /*
        Line count is dealt with in the above function.  A counter is simply incremented each time a new line
        is starting to be analyzed.  The public variable "lineCount" is updated each time.
         */

        //*************************************************
        /*  Deal with blankLinesRemoved */
        //*************************************************
        // ...

        //*************************************************
        /*  Deal with avgWordsPerLine   */
        //*************************************************
        // ...

        //*************************************************
        /*  Deal with avgLineLength */
        //*************************************************
        // ...
	}
}
