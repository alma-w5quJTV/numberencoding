package com.almasoft.numberencoding;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.almasoft.numberencoding.model.Phone;

public class PhoneTest {
    @Test @Ignore public void tail(){
        Phone p = new Phone("--23--");
        Assert.assertEquals("3--", p.tail().asString());
        
        p = new Phone("--2");
        Assert.assertEquals(null, p.tail());
        
        
        p = new Phone("--2/3");
        Assert.assertEquals("3", p.tail().asString());
        Assert.assertEquals(null, p.tail().tail());
        
        p = new Phone("--2/3-4-5---");
        Assert.assertEquals("3-4-5---", p.tail().asString());
        Assert.assertEquals("4-5---", p.tail().tail().asString());
        Assert.assertEquals("5---", p.tail().tail().tail().asString());
        Assert.assertEquals(null, p.tail().tail().tail().tail());
    }
    
    @Test public void refine(){
        Phone p = new Phone("--2/3-4-5---");
        Assert.assertEquals("2345", p.refine().asString());
        
        p = new Phone("2/3-4-5---");
        Assert.assertEquals("2345", p.refine().asString());
        
        p = new Phone("2/3-4-5");
        Assert.assertEquals("2345", p.refine().asString());
    }
    
}
