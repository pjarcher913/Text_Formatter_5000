package textformatter;

import java.util.ArrayList;

public class WordSegmenter
{
	public static String[] segmentWords(String[] lines)
	{
		ArrayList<String> wordsList = new ArrayList<String>();
		
		for(int i = 0; i < lines.length; i++)
		{
			String curLine = lines[i];
			boolean foundSpace = false;
			String word = "";
			
			for(int j = 0; j < curLine.length(); j++)
			{
				if(foundSpace)
				{
					if(!(curLine.charAt(j) == '\n' || curLine.charAt(j) == ' ' || curLine.charAt(j) == '\t' || curLine.charAt(j) == '\r'))
						foundSpace = false;
				}
				if(!foundSpace)
				{
					if(curLine.charAt(j) == '\n' || curLine.charAt(j) == ' ' || curLine.charAt(j) == '\t' || curLine.charAt(j) == '\r')
					{
						foundSpace = true;
						if(!word.isEmpty())
							wordsList.add(word);
						word = "";
					}
					else
					{
						word += curLine.charAt(j);
					}
				}
			}
			if(!word.isEmpty())
				wordsList.add(word);
		}
		String[] words = new String[wordsList.size()];
		for(int i = 0; i < words.length; i++)
			words[i] = wordsList.get(i);
		
		return words;
	}
}
