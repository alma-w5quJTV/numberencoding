package com.almasoft.numberencoding;

import java.io.IOException;
import java.util.List;

import com.almasoft.numberencoding.model.Phone;
import com.almasoft.numberencoding.model.Word;

public interface Dictionary {
    /**
     * perform all necessary one-time initialization
     * 
     * @throws IOException
     */
    void init() throws IOException;
    
    /**
     * Maps phone to set of words or null if empty set of words can be encoded into give number
     * 
     * This is inverse function to hash function defined in given exercise.
     * 
     * @param number -- Word instance representing phone number or its part. Word must be striped (means, it contains only digits)
     * 
     * @return list of inverse images of given phone number for hash function, or null if co-image is empty.
     */
    List<Word> getMappedWordsByNumber(Phone number);
    /**
     * 
     * @return size of the dictionary
     */
    int getWordCount();
}
