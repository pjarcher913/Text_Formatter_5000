package textformatter;

import java.util.ArrayList;

public class FullyJustifiedFormatter
{
	private int spacesAdded;
	
	public FullyJustifiedFormatter()
	{spacesAdded = 0;}
	
	public String[] format(String[] input, int spacing, int lineLength)
	{
		spacesAdded = 0;
		ArrayList<String> resultList = new ArrayList<String>();
		ArrayList<String> wordsInLine = new ArrayList<String>();
		wordsInLine.clear();
		
		String[] words = WordSegmenter.segmentWords(input);
		System.out.println(words.length);
		for(int i = 0; i < words.length; i++)
			System.out.print(words[i] + " ");
		
		//Handle case where words is empty here
		if(words.length == 0)
		{
			String[] result = new String[1];
			result[0] = "";
			return result;
		}
		
		for(int i = 0; i < words.length; i++)
		{
			wordsInLine.add(words[i]);
			
			int tmpLength = getLineLengthWithOneSpace(wordsInLine);
			if(tmpLength > lineLength || i == words.length - 1)
			{
				String strToReinsert = "";
				if(wordsInLine.size() != 1 && i != words.length - 1)
					strToReinsert = wordsInLine.remove(wordsInLine.size() - 1);
				if(i == words.length - 1 && wordsInLine.size() != 1 && tmpLength > lineLength)
				{
					i--;
					wordsInLine.remove(wordsInLine.size() - 1);
				}
				
				//Build the line without extra spaces
				String line = "";
				for(int j = 0; j < wordsInLine.size() - 1; j++)
				{
					line += wordsInLine.get(j) + " ";
				}
				line += wordsInLine.get(wordsInLine.size() - 1);
				
				//Determine how many spaces we will need to add.
				int lengthWithoutExtraSpaces = getLineLengthWithOneSpace(wordsInLine);
				int spacesToAdd = lineLength - lengthWithoutExtraSpaces;
				spacesAdded += spacesToAdd;
				
				if(line.contains(" "))
				{
					//Add spaces such that they are evenly distributed on the left and right side of the line
					int leftWord = 1;
					int rightWord = wordsInLine.size();
					boolean turn = true;
					for(int j = 0; j < spacesToAdd; j++)
					{	
						//left side's turn
						if(turn)
						{
							//Count leftWord amount of space clusters to the right
							int spacesFound = 0;
							int iter = 0;
							while(spacesFound != leftWord)
							{
								char curChar = line.charAt(iter);
								if(curChar == ' ')
								{
									spacesFound++;
									iter++;
									curChar = line.charAt(iter);
									while(curChar == ' ')
									{
										iter++;
										curChar = line.charAt(iter);
									}
									iter--;	
								}
								iter++;
								if(iter >= line.length())
									iter = 0;
							}
						
							//Add a space to the left of iter
							line = line.substring(0, iter) + " " + line.substring(iter, line.length());
						
							leftWord++;
							if(leftWord > wordsInLine.size())
								leftWord = 1;
							turn = !turn;
						}
						//right side's turn
						else
						{
							int spacesFound = 0;
							int iter = line.length() - 1;
							//Count rightWord amount of space clusters to the left
							while(spacesFound != rightWord)
							{
								char curChar = line.charAt(iter);
								if(curChar == ' ')
								{
									spacesFound++;
									iter--;
									curChar = line.charAt(iter);
									while(curChar == ' ')
									{
										iter--;
										curChar = line.charAt(iter);
									}
									iter++;
								}
								iter--;
								if(iter < 0)
									iter = line.length() - 1;
							}
						
							//Add a space to the right of iter
							line = line.substring(0, iter + 1) + " " + line.substring(iter + 1, line.length());
						
							rightWord++;
							if(rightWord <= 0)
								rightWord = wordsInLine.size();
							turn = !turn;
						}
					}
				}
				
				//Done formatting this line.  Add it to the list.
				resultList.add(line);
				
				if(spacing == 1)
					resultList.add(new String(""));
				
				wordsInLine.clear();
				if(!strToReinsert.isEmpty())
					wordsInLine.add(strToReinsert);
			}
			
		}
		
		if(spacing == 1)
			resultList.remove(resultList.size() - 1);
		
		//Build the array of lines.
		String[] result = new String[resultList.size()];
		for(int i = 0; i < resultList.size(); i++)
			result[i] = resultList.get(i);
		
		return result;
	}
	
	private int getLineLengthWithOneSpace(ArrayList<String> list)
	{
		int lineLength = 0;
		for(int i = 0; i < list.size(); i++)
		{
			lineLength += list.get(i).length() + 1;
		}
		lineLength -= 1;
		
		return lineLength;
	}
	
	public int getSpacesAdded() {return spacesAdded;}
}
