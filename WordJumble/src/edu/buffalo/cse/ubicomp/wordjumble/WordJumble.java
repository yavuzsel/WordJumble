package edu.buffalo.cse.ubicomp.wordjumble;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;

public class WordJumble {

	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while(true){
			System.out.print("Enter a text: ");
			try {
				String str = br.readLine();
				if(str.equalsIgnoreCase("exit"))
					return;
				HashSet<String> resultList = WordJumbleFinder.getWords(str);
				for(Iterator<String> it = resultList.iterator(); it.hasNext();){
					System.out.println(it.next());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static class WordJumbleFinder {
		private static HashSet<String> getWords(String str){
			return getWords("", str);
		}

		private static HashSet<String> getWords(String prefix, String str){
			HashSet<String> resultList = new HashSet<String>();
			if(str.length() == 0)
				return resultList;
			String wordCandidate = "";
			for(int i=0; i<str.length(); ++i){
				wordCandidate = prefix + str.charAt(i);
				if(Dictionary.getInstance().isInDictionary(wordCandidate))
					resultList.add(wordCandidate);
				StringBuilder sb = new StringBuilder(str);
				sb.deleteCharAt(i);
				resultList.addAll(getWords(wordCandidate, sb.toString()));
			}
			return resultList;
		}

		private static class Dictionary {

			// eager instantiation
			private static Dictionary _INSTANCE = new Dictionary();

			private HashSet<String> dictionary;

			private Dictionary(){
				loadDictionary();
			}

			private void loadDictionary(){
				dictionary = new HashSet<String>();
				try {
					BufferedReader br = new BufferedReader(new FileReader("assets/3esl.txt"));
					for(String line; (line = br.readLine()) != null; ) {
						if(!dictionary.contains(line))
							dictionary.add(line);
					}
					br.close();
				} catch(FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			public static Dictionary getInstance(){
				return _INSTANCE;
			}

			public boolean isInDictionary(String word){
				return dictionary.contains(word);
			}
		}

	}
}
