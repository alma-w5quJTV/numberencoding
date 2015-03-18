package com.almasoft.numberencoding;

import com.almasoft.numberencoding.model.Phone;

public interface PhoneValidator {
    /**
     * 
     * @param phone
     * @return true if phone is valid
     */
    boolean valid(Phone phone);
}