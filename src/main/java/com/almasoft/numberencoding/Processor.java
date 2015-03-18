package com.almasoft.numberencoding;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.List;

import com.almasoft.numberencoding.model.Phone;
import com.almasoft.numberencoding.model.Word;

/**
 * Process input stream that of incomint phones, and output ecndoded into output. 
 *
 */
public class Processor {
    
    private Dictionary dictionary;
    private PhoneEncoder encoder;
    private PhoneValidator validator;

    private static final int BUFFER_SIZE = 8 * 1024; 
    private static final byte[] SEPARATOR =  new byte[]{':', ' '};
    private static final byte[] NEW_LINE =  new byte[]{Word.CR, Word.NL};
    public void init(String dictionaryFile) throws IOException{
        //load dictionary
        dictionary = new TreeMapDictionary(dictionaryFile);
        dictionary.init();
        
        encoder = new TreeIndexPhoneEncoder(dictionary);
        validator = new LengthAndCharactersPhoneValidator();
    }

    public void process(PrintStream out, InputStream in) throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        int len = buffer.length;
        
        Phone tail = null;
        
        while((len = in.read(buffer, 0, len)) != -1){
            Word word = new Word(buffer, 0, len);
            
            //concatenate tail into beginning to the buffer
            if(tail != null){
                word = tail.concat(word);
            }
            tail = word.overLines(phone ->  process(phone, out), Phone.class);
        }
        //process last tail that can be in the end of input stream
        if(tail != null) process(tail, out);
    }

    public void process(PrintStream out, File file) throws IOException{
        try(InputStream io = new BufferedInputStream(
                Files.newInputStream(file.toPath()), BUFFER_SIZE)){
            process(out, io);
        }
    }
    
    public void process(Phone number, PrintStream out) {
        if(validator.valid(number)){
            List<Word> words = encoder.encode(number);
            //if match is found -- we can emit result
            if(words != null){
                words.forEach(w -> emit(number, w, out));
            }
        }
    }
    /**
     * Emits result according task rules:
     * 
     * "PHONE: WORD"
     * 
     * @param p -- phone to encode
     * @param word - word 
     * @param out -- output
     */
    private void emit(Phone p, Word word, PrintStream out){
        try{
            p.write(out);
            out.write(SEPARATOR);
            word.write(out);
            out.write(NEW_LINE);
        }catch(IOException e){
            throw new EncodingException(e);
        }
    }
}
