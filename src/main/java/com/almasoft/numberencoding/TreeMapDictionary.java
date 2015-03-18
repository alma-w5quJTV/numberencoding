package com.almasoft.numberencoding;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.almasoft.numberencoding.model.Phone;
import com.almasoft.numberencoding.model.Word;

public class TreeMapDictionary implements Dictionary {
    
    public TreeMapDictionary(String dictionaryPath) {
        this.dictionaryPath = dictionaryPath;
    }
    
    public TreeMapDictionary(Map<Phone,List<Word>> d) {
        dictionary.putAll(d);
        dictionary.forEach((k,v) -> wordCount += v.size());
    }
    private Word dictionarySource;
    /**
     * Main hash function of the exercise
     */
    private HashFunction hashFunction = new HashFunction();
    /**
     * Path to source file of the dictionary, related to root of the project
     */
    private String dictionaryPath = null;
    
    private int wordCount = 0;
    
    /**
     * Map from {phone number} |--> Set{word}
     */
    private Map<Phone, List<Word>> dictionary = new TreeMap<>();
    
    @Override public void init() throws IOException{
        if(dictionaryPath != null){
            loadDictionaryFromFile();
        }
    }
    private void loadDictionaryFromFile() throws IOException{
        //from the task description it follows that this operation is correct, size of dictionary is bounded. 
        byte[] dictionaryContent = Files.readAllBytes(new File(dictionaryPath).toPath());
        //create one single object that will keep all dictionary
        dictionarySource = new Word(dictionaryContent);
        
        //fill the dictionary Map
        for (Iterator<Word> i = dictionarySource.iterateLines(Word.class); i.hasNext();) {
            Word word = i.next();
            wordCount ++;
            
            Phone numberKey = hashFunction.hash(word);
            
            List<Word> bucket = null;
            if((bucket = dictionary.get(numberKey)) == null){
                bucket = new LinkedList<Word>();
                dictionary.put(numberKey, bucket);
            }
            bucket.add(word);
        }
    }
    @Override
    public List<Word> getMappedWordsByNumber(Phone number) {
        return dictionary.get(number);
    }
    @Override
    public int getWordCount() {
        return wordCount;
    }
}
