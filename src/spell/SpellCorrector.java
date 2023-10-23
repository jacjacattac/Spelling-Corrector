package spell;

import com.sun.source.tree.Tree;

import java.io.IOException;
//import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.util.TreeSet;
import java.util.Set;

public class SpellCorrector implements ISpellCorrector {
    Trie trie = new Trie();

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        File file = new File(dictionaryFileName);
        Scanner scanner = new Scanner(file);
        String word;
        while (scanner.hasNext()) {
            word = scanner.next();
            trie.add(word);
        }
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        inputWord = inputWord.toLowerCase();
        Set<String> edit = new TreeSet<>();
        String suggestion = null;
        if (trie.find(inputWord) != null) {
            return inputWord;
        } else {
            insertion(inputWord, edit);
            deletion(inputWord, edit);
            transposition(inputWord, edit);
            alteration(inputWord, edit);

            suggestion = suggestNewWord(edit);
            if (suggestion == null) {
                Set<String> edit2 = edit2(edit);
                suggestion = suggestNewWord(edit2);
                }
            }
            return suggestion;
        }

    public void insertion(String input, Set<String> edit1) {
        for (int i = 0; i < input.length() + 1; i++){
            for (char c = 'a'; c <= 'z'; c++){
                StringBuilder sBuilder = new StringBuilder(input);
                sBuilder.insert(i, c);
                String newWord = sBuilder.toString();
                edit1.add(newWord);
            }
        }
    }


    public void deletion(String input, Set<String> edit1) {
        for (int i = 0; i < input.length(); i++) {
                StringBuilder sBuilder = new StringBuilder(input);
                sBuilder.delete(i,i+1);
                String newWord = sBuilder.toString();
                edit1.add(newWord);
        }
    }

    public void alteration(String input, Set<String> edit1) {
        for (int i = 0; i < input.length(); i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                String s = String.valueOf(c);
                StringBuilder sBuilder = new StringBuilder(input);
                sBuilder.replace(i,i+1,s);
                String newWord = sBuilder.toString();
                edit1.add(newWord);
            }
        }
    }

    public void transposition(String input, Set<String> edit1) {
        // loop through the input word character by character
        // at each character, switch it with the character in after it
        for (int i = 0; i < input.length()-1; i++){
            StringBuilder builder = new StringBuilder(input);
            String c = String.valueOf(input.charAt(i));
            String d = String.valueOf(input.charAt(i+1));
            builder.replace(i, i+1, d);
            builder.replace(i+1, i+2, c);
            String newWord = builder.toString();
            edit1.add(newWord);
        }

    }

    public String suggestNewWord(Set<String> edit){
        int max = 0;
        String suggestion = null;
        for(String s: edit){ // loop through list
            if(trie.find(s) != null){ // if its not null that means theres a word
                INode node = trie.find(s); // set node to the string
                if(node.getValue() > max){ //check frequency of the word to get words with highest word count
                    max = node.getValue(); // update it is the word count is more than previous words
                    suggestion = s; // set suggested word to new string
                }
            }
        }
        return suggestion;
    }
    public Set<String> edit2(Set<String> edit){
        Set<String> edit2 = new TreeSet<>();
        for(String s : edit){
            insertion(s, edit2);
            deletion(s, edit2);
            transposition(s, edit2);
            alteration(s, edit2);
        }
        return edit2;
    }
}
