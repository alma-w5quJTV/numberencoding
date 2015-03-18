package com.almasoft.numberencoding;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.almasoft.numberencoding.model.Phone;

public class SimpleDictionaryTest {
    @Test public void testLoadDictionary() throws IOException{
        Dictionary d = new TreeMapDictionary("src/test/resources/small-dictionary.txt");
        d.init();
        Assert.assertEquals(23, d.getWordCount());
        
        d = new TreeMapDictionary("doc/dictionary.txt");
        d.init();
        Assert.assertEquals(73113, d.getWordCount());
    }
    
    
    @Test public void testEncode(){
        
        Dictionary dictionary = new TestFixture().createSmallDictionary();
        
        Assert.assertEquals(null, dictionary.getMappedWordsByNumber(new Phone("112")));
        Assert.assertEquals(1, dictionary.getMappedWordsByNumber(new Phone("4-82")).size());
        Assert.assertEquals(1, dictionary.getMappedWordsByNumber(new Phone("4-8/2")).size());
    }
}
