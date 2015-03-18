package com.almasoft.numberencoding.model;

import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Function;

public class Word{
    private static final Charset ASCII = Charset.forName("ascii");
    
    public static final byte NL = '\n';
    public static final byte CR = '\r';
   
    protected byte[] buffer;
    
    protected int offset;
    protected int len;
    
    public Word(byte[] buffer) {
        this.buffer = buffer;
        this.offset = 0;
        this.len = buffer.length;
    }
    public Word(byte[] buffer, int offset, int len) {
        this.buffer = new byte[len];
        System.arraycopy(buffer, offset, this.buffer, 0, len);
        this.offset = 0;
        this.len = len;
    }
    public Word(Word w) {
        this.buffer = w.buffer;
        this.offset = w.offset;
        this.len = w.len;
    }
    public Word(){
    }
    public Word(String w){
        this(w.getBytes());
    }
    
    protected <T extends Word> T projection(int offset, int len, Class<T> type){
        if(offset < 0 || len < 0 || this.offset + offset > this.buffer.length 
                ||  this.offset + offset + len > this.buffer.length) throw new IndexOutOfBoundsException();
        Word retVal = null;
        try {
            retVal = type.newInstance();
            retVal.buffer = buffer;
            retVal.offset = this.offset + offset;
            retVal.len = len;
            return type.cast(retVal);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }
    
    public Word tail(){
        return len > 1 ? projection(1,len - 1, Word.class) : null;
    }
    public Word tail(int len) {
        return this.len > len ? projection(len, this.len - len, Word.class) : null;
    }
    public Word take(int k){
        return k <= this.len ? projection(0 , k, Word.class) : null;
    }
    
    public <T extends Word> Iterator<T> iterateLines(Class<T> type){
        return new Iterator<T>(){
            
            
            private int startWordPointer = offset;
            private int length = 0;
            
            @Override
            public boolean hasNext() {
                return startWordPointer < offset + len;
            }
            @Override
            public T next() {
                byte b;
                
                do{
                    length ++;
                }while(startWordPointer + length <= offset + len && (b = buffer[startWordPointer + length - 1]) != NL && b != CR);
                 
                T retVal = projection (startWordPointer, length - 1, type);
                
                startWordPointer += length;
                
                while(startWordPointer < offset + len && 
                        ((b = buffer[startWordPointer]) == NL || b == CR)){
                    startWordPointer ++;
                }
                length = 0;
                
                return retVal;
            }
        };
    }
    public <T extends Word> T overLines(Consumer<T> consumer, Class<T> type) {
        int start = offset;
        for(int i = offset; i < offset + len; i++){
            if(buffer[i] == NL){
                int cr = i - 1 >= offset &&  buffer[i - 1] == CR ? 1 : 0;
                if(i - offset - cr > 0 || start >= offset + len){
                    consumer.accept(projection(start - offset , i - start - cr, type));
                    start = i + 1;
                }else{
                    return null;
                }
            }
        }
        return len > start ?  projection(start, len - start ,type) : null;
    }
    public boolean over(Function<Byte, Boolean> c){
        for(int i = offset; i < offset + len; i++){
            if(!c.apply(buffer[i])) return false;
        }
        return true;
    }
    
    public Word concatWithSpace(Word g) {
        Word retVal = new Word();
        retVal.len = len + g.len + 1;
        retVal.buffer = new byte[retVal.len];
        System.arraycopy(this.buffer, this.offset, retVal.buffer, 0, this.len);
        retVal.buffer[this.len] = ' ';
        System.arraycopy(g.buffer, g.offset, retVal.buffer, this.len + 1, g.len);
        
        return retVal;
    }
    public Word concat(Word g) {
        Word retVal = new Word();
        retVal.len = len + g.len;
        retVal.buffer = new byte[retVal.len];
        System.arraycopy(this.buffer, this.offset, retVal.buffer, 0, this.len);
        System.arraycopy(g.buffer, g.offset, retVal.buffer, this.len, g.len);
        
        return retVal;
    }
    public int length() {
        return len;
    }
    public String asString(){
        return new String(buffer, offset, len, ASCII);
    }
    public void write(PrintStream out) {
        out.write(buffer, offset, len);
    }
    @Override
    public String toString() {
        return asString();
    }

   
    
}