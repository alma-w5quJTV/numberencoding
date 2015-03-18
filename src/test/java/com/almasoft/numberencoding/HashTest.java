package com.almasoft.numberencoding;


import org.junit.Assert;
import org.junit.Test;

import com.almasoft.numberencoding.model.Word;

public class HashTest {
    @Test public void testHashFunction(){
       HashFunction h = new HashFunction();
       
       Assert.assertEquals("9088828283", h.hash(new Word("hello world")).asString());
       Assert.assertEquals("9088828283", h.hash(new Word("hello\"world")).asString());
       
       Assert.assertEquals("12024376885334991789266715", 
               h.hash(new Word("QWE RTY UIO PAS DFG HJK LZX CVB NM")).asString());
       Assert.assertEquals("12024376885334991789266715", h.hash(new Word("qwertyuiopasdfghjklzxcvbnm")).asString());
       
       Assert.assertEquals("12024376885334991789266715", h.hash(new Word("qw\"ertyu\"io\"pasdfghjklzxcvbnm")).asString());
    }
}