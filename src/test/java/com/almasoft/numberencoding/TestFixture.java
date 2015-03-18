package com.almasoft.numberencoding;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.almasoft.numberencoding.model.Phone;
import com.almasoft.numberencoding.model.Word;

public class TestFixture {
    public Dictionary createSmallDictionary(){
        Map<Phone,List<Word>> d = new TreeMap<>();
        HashFunction f = new HashFunction();
        String[] wdictionary = new String[]
            {"an",
            "blau",
            "Bo\"",
            "Boot",
            "bo\"s",
            "da",
            "Fee",
            "fern",
            "Fest",
            "fort",
            "je",
            "jemand",
            "mir",
            "Mix",
            "Mixer",
            "Name",
            "neu",
            "o\"d",
            "Ort",
            "so",
            "Tor",
            "Torf",
            "Was-ser"};
        
        for (String w : wdictionary) {
            Word word = new Word(w);
           
            List<Word> b = d.get(f.hash(word));
            
            if(b == null){
                b = new LinkedList<>();
                d.put(f.hash(word), b);
            }
            b.add(word);
        }
        return new TreeMapDictionary(d);
    }
    public static void main(String[] args) {
        System.out.println(new HashFunction().hash("Was-ser"));
    }
}
