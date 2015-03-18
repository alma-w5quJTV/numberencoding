package com.almasoft.numberencoding;

import java.util.List;

import com.almasoft.numberencoding.model.Phone;
import com.almasoft.numberencoding.model.Word;

public interface PhoneEncoder {
    /**
     * 
     * @param phone valid phone to encode. Value have to be valid and non-null.
     * @return Non-empty list of all encoded variants or null if this input cannot be encoded 
     * @throws in case of wrong (not valid) input was provided
     */
    List<Word> encode(Phone phone) throws EncodingException;
}
