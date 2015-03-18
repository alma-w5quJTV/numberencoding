package com.almasoft.numberencoding.model;

import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

import com.almasoft.numberencoding.model.Word;

public class WordTest {
    @Test public void testWordInterator(){
        Iterator<Word> i = new Word("windows word \r\n eee").iterateLines(Word.class);
        
        Assert.assertTrue(i.hasNext());
        Assert.assertEquals("windows word ", i.next().asString());
        
        Assert.assertTrue(i.hasNext());
        Assert.assertEquals(" eee", i.next().asString());
        
        Assert.assertFalse(i.hasNext());
    }
    @Test public void emptyWordInterator(){
        Iterator<Word> i = new Word("").iterateLines(Word.class);
        
        Assert.assertFalse(i.hasNext());
    }
    @Test public void justNLInterator(){
        Iterator<Word> i = new Word("\n").iterateLines(Word.class);
        
        Assert.assertTrue(i.hasNext());
        Assert.assertEquals("", i.next().asString());
    }
    @Test public void justNLAndSpaceInterator(){
        Iterator<Word> i = new Word("\n ").iterateLines(Word.class);
        
        Assert.assertTrue(i.hasNext());
        Assert.assertEquals("", i.next().asString());
        
        Assert.assertTrue(i.hasNext());
        Assert.assertEquals(" ", i.next().asString());
    }
    @Test public void justWinAndLinuxNLAndSpaceInterator(){
        Iterator<Word> i = new Word("A\r\n ").iterateLines(Word.class);
        
        Assert.assertTrue(i.hasNext());
        Assert.assertEquals("A", i.next().asString());
        
        Assert.assertTrue(i.hasNext());
        Assert.assertEquals(" ", i.next().asString());
        
        i = new Word("A\n ").iterateLines(Word.class);
        
        Assert.assertTrue(i.hasNext());
        Assert.assertEquals("A", i.next().asString());
        
        Assert.assertTrue(i.hasNext());
        Assert.assertEquals(" ", i.next().asString());
    }
    
    @Test public void borderLimitsInterator(){
        Iterator<Word> i = new Word("\r\n ").iterateLines(Word.class);
        
        Assert.assertTrue(i.hasNext());
        Assert.assertEquals("", i.next().asString());
        
        Assert.assertTrue(i.hasNext());
        Assert.assertEquals(" ", i.next().asString());
        
        i = new Word("A\n").iterateLines(Word.class);
        
        Assert.assertTrue(i.hasNext());
        Assert.assertEquals("A", i.next().asString());
        
    }
    
    @Test public void projection(){
        Word w = new Word("Some long word");
        
        Assert.assertEquals("Some long word", w.projection(0, w.length(), Word.class).asString());
        Assert.assertEquals("ome long word", w.projection(1, w.length() - 1, Word.class).asString());
        
        Word projection = w.projection(1, w.length() - 1, Word.class).projection(1, w.length() - 2, Word.class);
        Assert.assertEquals("me long word", projection.asString());
        Assert.assertEquals("me long word".length(), projection.length());
        
        Assert.assertEquals("long", projection.projection(3, 4, Word.class).asString());
        Assert.assertEquals("long".length(), projection.projection(3, 4, Word.class).length());
    }
    @Test public void takeAndTail(){
        Word w = new Word("Some long word");
        
        Assert.assertEquals("ome long word", w.tail().asString());
        
        Word projection = w.tail().tail();
        Assert.assertEquals("me long word", projection.asString());
        Assert.assertEquals("me long word".length(), projection.length());
        
        Assert.assertEquals("me lo", projection.take(5).asString());
        Assert.assertEquals("m", projection.take(1).asString());
    }
    @Test public void emptyTail(){
        Word w = new Word("w");
        
        Assert.assertEquals(null,  w.tail());
        Assert.assertEquals(null,  w.tail(2));
        
        w = new Word("word");
        
        Assert.assertEquals("rd", w.tail(2).asString());
        Assert.assertEquals(null, w.tail(2).tail(2));
        
    }
    @Test public void concatenation(){
        Word w = new Word("HHello");
        
        Assert.assertEquals("Hello word!",  w.tail().concatWithSpace(new Word(" word!").tail()).asString());
        
        
    }
    @Test public void iterate(){
        Word w = new Word(new byte[]{'a','b',Word.NL, 'c', 'd'});
        Assert.assertEquals("cd", w.overLines(c -> {}, Phone.class).asString());
        
        w = new Word(new byte[]{'a','b',Word.CR, Word.NL, 'c', 'd'});
        Assert.assertEquals("cd", w.overLines(c -> {}, Phone.class).asString());
        
        w = new Word(new byte[]{'a','b',Word.CR, Word.NL, 'c', 'd', Word.NL});
        Assert.assertEquals(null, w.overLines(c -> {}, Phone.class));
        
        w = new Word(new byte[]{'a','b',Word.CR, Word.NL, 'c', 'd', Word.NL,Word.NL});
        Assert.assertEquals(null, w.overLines(c -> {}, Phone.class));
        
        w = new Word(new byte[]{'a','b',Word.CR, Word.NL, 'c', 'd', Word.NL,Word.NL, Word.NL, 'd'});
        Assert.assertEquals("d", w.overLines(c -> {}, Phone.class).asString());
        
        w = new Word(new byte[]{'a','b',Word.CR, Word.NL, 'c', 'd', Word.CR,Word.NL, Word.NL, 'd'});
        Assert.assertEquals("d", w.overLines(c -> {}, Phone.class).asString());
    }
}
