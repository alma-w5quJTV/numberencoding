package com.almasoft.numberencoding;

import com.almasoft.numberencoding.model.Phone;

/**
 * Validate phone for rules: length should be from 1 to 50 inclusive, and contains only in valid chars, @see {Phone#containsValidChars}  
 *  
 */
public class LengthAndCharactersPhoneValidator implements PhoneValidator {

    @Override
    public boolean valid(Phone phone) {
        int l = phone.length();
        return l > 0 && l <= 50 && phone.refine().length() > 0 && phone.containsValidChars();
    }
}
