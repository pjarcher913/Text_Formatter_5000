package textformatter;

import java.util.LinkedList;

public class Formatter
{
	// input is an array of strings where each string is a line from the original input file
	// flag will determine whether to do left or right justification.
	// Let's say flag == 0 is left justification and flag == 1 is right justification
	public String[] format(String[] input, int flag)
	{
		LinkedList<String> formList = new LinkedList<String>(); //Used to store an unknown quantity of lines
		String formLine = "";	//current formatted line
		int wordStart = -1; //variable that tells if and where in the input String a word starts
		int wordEnd = -1; //variable that shows the position of the end of a string
		if(flag == 0){ //left justified
			for(int i = 0; i < input.length; i++){//traverses the input array
				String currentLine = input[i];
				for(int j = 0; j < currentLine.length()-1; j++){//traverses the input string at position i
						char curChar = currentLine.charAt(j);
						char nextChar = currentLine.charAt(j+1);
						if(curChar != ' '){
							if(wordStart == -1){
								wordStart = j;
							}
						}
						if((j+1) == currentLine.length()-1){
							if(nextChar != ' '){
								if(wordStart == -1){
									wordStart = j+1;
								}
								wordEnd = j+1;
							}
							else{
								wordEnd = j;
							}
						}
						if(curChar == ' ' || curChar == '\t'){
							wordEnd = j-1;
						}
						if(wordStart != -1 && wordEnd != -1){
							String newWord = "";
							int newWordLength = wordEnd - wordStart +1;
							int newLineLength = formLine.length() + newWordLength + 1;
							if(formLine.length() == 0){
								for(int k = wordStart; k <= wordEnd; k++){
									newWord += currentLine.charAt(k);
								}
								formLine += newWord;
							}
							else{
								if((newLineLength) < 80){
									newWord += ' ';
									for(int k = wordStart; k <= wordEnd; k++){
										newWord += currentLine.charAt(k);
									}
									formLine += newWord;
								}
								else{
									if((newLineLength) > 80){
										formList.add(formLine);
										formLine = "";
										for(int k = wordStart; k <= wordEnd; k++){
											newWord += currentLine.charAt(k);
										}
										formLine += newWord;
										
									}
									if(newLineLength == 80){
										newWord += ' ';
										for(int k = wordStart; k <= wordEnd; k++){
											newWord += currentLine.charAt(k);
										}
										formLine += newWord;
										formList.add(formLine);
										formLine = "";
									}
								}
								
							}
							wordStart = -1;
							wordEnd = -1;
						}
						
					}
					
					
				}
				if(formLine != ""){
					formList.add(formLine);
				}
				
			}
			if(flag == 1){
				String tempLine = "";
				for(int i = 0; i < input.length; i++){//traverses the input array
					String currentLine = input[i];
					for(int j = 0; j < currentLine.length()-1; j++){//traverses the input string at position i
							char curChar = currentLine.charAt(j);
							char nextChar = currentLine.charAt(j+1);
							if(curChar != ' '){
								if(wordStart == -1){
									wordStart = j;
								}
							}
							if((j+1) == currentLine.length()-1){
								if(nextChar != ' '){
									if(wordStart == -1){
										wordStart = j+1;
									}
									wordEnd = j+1;
								}
								else{
									wordEnd = j;
								}
							}
							if(curChar == ' ' || curChar == '\t'){
								wordEnd = j-1;
							}
							if(wordStart != -1 && wordEnd != -1){
								String newWord = "";
								int newWordLength = wordEnd - wordStart +1;
								int newLineLength = tempLine.length() + newWordLength + 1;
								if(tempLine.length() == 0){
									for(int k = wordStart; k <= wordEnd; k++){
										newWord += currentLine.charAt(k);
									}
									tempLine += newWord;
								}
								else{
									if((newLineLength) < 80){
										newWord += ' ';
										for(int k = wordStart; k <= wordEnd; k++){
											newWord += currentLine.charAt(k);
										}
										tempLine += newWord;
									}
									else{
										if((newLineLength) > 80){
											int spaces = 80 - tempLine.length();
											for(int z = 0; z < spaces; z++){
												formLine += " ";
											}
											formLine += tempLine;
											formList.add(formLine);
											formLine = "";
											tempLine = "";
											for(int k = wordStart; k <= wordEnd; k++){
												newWord += currentLine.charAt(k);
											}
											tempLine += newWord;
											
										}
										if(newLineLength == 80){
											newWord += ' ';
											for(int k = wordStart; k <= wordEnd; k++){
												newWord += currentLine.charAt(k);
											}
											tempLine += newWord;
											formLine += tempLine;
											formList.add(formLine);
											tempLine = "";
											formLine = "";
										}
									}
									
								}
								wordStart = -1;
								wordEnd = -1;
							}
							
						}
						
						
					}
					if(tempLine != ""){
						int spaces = 80 - tempLine.length();
						for(int z = 0; z < spaces; z++){
							formLine += " ";
						}
						formLine += tempLine;
						formList.add(formLine);
					}
					
				}
				
				String[] formText = new String[formList.size()];
				for(int n = 0; n < formList.size(); n++){
					formText[n] = formList.get(n);
				}
				return formText;
		}
	
}
