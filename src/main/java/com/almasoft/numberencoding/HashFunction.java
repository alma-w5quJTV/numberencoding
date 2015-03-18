package com.almasoft.numberencoding;

import com.almasoft.numberencoding.model.Phone;
import com.almasoft.numberencoding.model.Word;


/**
 * Important: class is not thread-safe.
 *
 */
public class HashFunction {
    
    /**
     * E | J N Q | R W X | D S Y | F T | A M | C I V | B K U | L O P | G H Z
       e | j n q | r w x | d s y | f t | a m | c i v | b k u | l o p | g h z
       0 |   1   |   2   |   3   |  4  |  5  |   6   |   7   |   8   |   9
     */
    private final byte[][] table = new byte[][]{
            {'e','0'}, 
            {'j','1'}, {'n','1'}, {'q','1'},
            {'r','2'}, {'w','2'}, {'x','2'},
            {'d','3'}, {'s','3'}, {'y','3'},
            {'f','4'}, {'t','4'},
            {'a','5'}, {'m','5'},
            {'c','6'}, {'i','6'}, {'v','6'},
            {'b','7'}, {'k','7'}, {'u','7'},
            {'l','8'}, {'o','8'}, {'p','8'},
            {'g','9'}, {'h','9'}, {'z','9'},
            
            {'E','0'}, 
            {'J','1'}, {'N','1'}, {'Q','1'},
            {'R','2'}, {'W','2'}, {'X','2'},
            {'D','3'}, {'S','3'}, {'Y','3'},
            {'F','4'}, {'T','4'},
            {'A','5'}, {'M','5'},
            {'C','6'}, {'I','6'}, {'V','6'},
            {'B','7'}, {'K','7'}, {'U','7'},
            {'L','8'}, {'O','8'}, {'P','8'},
            {'G','9'}, {'H','9'}, {'Z','9'}
    };
    private final char[] mapTable = new char[256];//size of char
    
    private void init(){
        for (byte[] t : table) {
            mapTable[t[0]] = (char)t[1];
        }
    }
    
    private byte[] temp = new byte[250];
    private int encodedLength = 0;
    
    public HashFunction() {
        init();
    }


    public String hash(String word){
        String retVal = null;
        int encodedLength = 0;
        
        byte[] wb = word.getBytes();
        
        for(int i = 0; i < wb.length; i++){
            if(mapTable[wb[i]] > 0){
                temp[encodedLength ++] = (byte) mapTable[wb[i]];
            }
        }
        if(encodedLength > 0){
            retVal = new String(temp, 0, encodedLength);
        }
        return retVal;
    }


    public Phone hash(Word word) {
        encodedLength = 0;
        Phone retVal = null;
        
        word.over((c) -> {
            if(mapTable[c] > 0){
                temp[encodedLength ++] = (byte)mapTable[c];
            }
            return true;
        });
        
        if(encodedLength > 0){
            byte[] w = new byte[encodedLength];
            System.arraycopy(temp, 0, w, 0, encodedLength);
            retVal = new Phone(w);
        }
        return retVal;
    }
}
