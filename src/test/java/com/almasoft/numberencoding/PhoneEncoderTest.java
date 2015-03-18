package com.almasoft.numberencoding;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.almasoft.numberencoding.model.Phone;
import com.almasoft.numberencoding.model.Word;

public class PhoneEncoderTest {
    
    private Dictionary dictionary;
    private TreeIndexPhoneEncoder encoder;
    @Before public void init(){
        dictionary = new TestFixture().createSmallDictionary();
        
        encoder = new TreeIndexPhoneEncoder(dictionary);
    }
    /**
     *
     * Test encoding according specification:
     * 
        112
        5624-82
        4824
        0721/608-4067
        10/783--5
        1078-913-5
        381482
        04824
        
        Corresponding correct program output (on screen):
        5624-82: mir Tor
        5624-82: Mix Tor
        4824: Torf
        4824: fort
        4824: Tor 4
        10/783--5: neu o"d 5
        10/783--5: je bo"s 5
        10/783--5: je Bo" da
        381482: so 1 Tor
        04824: 0 Torf
        04824: 0 fort
        04824: 0 Tor 4 
    */
    @Test public void testEncode(){
        Assert.assertEquals(23, dictionary.getWordCount());
        
        
        Assert.assertEquals(null, encoder.encode(new Phone("111")));
        Assert.assertArrayEquals(sorted("Mix Tor","mir Tor"), encode("562482"));
        Assert.assertArrayEquals(sorted("Torf","Tor 4","fort"), encode("4824"));
        Assert.assertArrayEquals(sorted("so 1 Tor"), encode("381482"));
        Assert.assertArrayEquals(sorted("0 Torf","0 fort","0 Tor 4"), encode("04824"));
        Assert.assertArrayEquals(sorted("neu o\"d 5","je bo\"s 5","je Bo\" da"), encode("107835"));
    }
    
    @Test public void testEncodeWithDashes(){
        Assert.assertEquals(23, dictionary.getWordCount());
        
        
        Assert.assertEquals(null, encoder.encode(new Phone("111")));
        Assert.assertArrayEquals(sorted("Mix Tor","mir Tor"), encode("5624-82"));
        Assert.assertArrayEquals(sorted("Torf","Tor 4","fort"), encode("4824"));
        Assert.assertArrayEquals(sorted("so 1 Tor"), encode("381482"));
        Assert.assertArrayEquals(sorted("0 Torf","0 fort","0 Tor 4"), encode("04824"));
        Assert.assertArrayEquals(sorted("neu o\"d 5","je bo\"s 5","je Bo\" da"), encode("10/783--5"));
        
        Assert.assertArrayEquals(sorted("Was-ser"), encode("25/3--302"));
        
    }
    private Object[] sorted(String... w){
        Arrays.sort(w);
        return w;
    }
    private Object[] encode(String phone){
        List<Word> l = encoder.encode(new Phone(phone));
        if(l != null){
            Object[] s = l.stream().flatMap(word -> Stream.of(word.asString())).toArray();
            Arrays.sort(s);
            return s;
        }
        return null;
    }

}
