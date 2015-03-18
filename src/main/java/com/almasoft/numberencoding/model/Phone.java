package com.almasoft.numberencoding.model;


public class Phone extends Word implements Comparable<Phone>{
    
    public Phone() {
        super();
    }
    
    public Phone(byte[] buffer) {
        super(buffer);
    }
    public Phone(String w) {
        super(w);
    }
    public Phone(Word w) {
        super(w);
    }
    
    private boolean isDigit(byte b){
        return b >= '0' && b <= '9';
    }

    
    @Override
    public int compareTo(Phone o) {
        Phone tr = this.refine();
        Phone or = o.refine();
        
        //FIXME avoid creation of String: create array comparator
        return new String(tr.buffer, 0, tr.len).compareTo(new String(or.buffer, 0, or.len));
    }
    public Phone refine() {
        Phone retVal = new Phone();
        retVal.buffer = new byte[len];
        over(c -> {
            if(isDigit(c)){
                retVal.buffer[retVal.len ++] = c;
            }
            return true;
        });
        return retVal;
    }
    public boolean containsValidChars() {
        return over(c -> isDigit(c) || c == '/' || c == '-');
    }
}