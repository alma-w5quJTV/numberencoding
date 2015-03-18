package com.almasoft.numberencoding;

import org.junit.Assert;
import org.junit.Test;

import com.almasoft.numberencoding.model.Phone;

public class ValidationTest {
    @Test public void phoneValidatorTest(){
        PhoneValidator v = new LengthAndCharactersPhoneValidator();
        Assert.assertTrue(v.valid(new Phone("234")));
        Assert.assertTrue(v.valid(new Phone("01234567890123456789012345678901234567890123456789")));
        Assert.assertTrue(v.valid(new Phone("0123456789-123456789-123456789-123456789-123456789")));
        Assert.assertTrue(v.valid(new Phone("/-1-/")));
        
        
        Assert.assertFalse(v.valid(new Phone("012345678901234567890123456789012345678901234567891")));
        Assert.assertFalse(v.valid(new Phone("/--/")));
        Assert.assertFalse(v.valid(new Phone("")));
        Assert.assertFalse(v.valid(new Phone("2 ")));
    }
}
