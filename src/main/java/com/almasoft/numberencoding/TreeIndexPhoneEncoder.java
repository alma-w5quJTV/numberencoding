package com.almasoft.numberencoding;

import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import com.almasoft.numberencoding.model.Phone;
import com.almasoft.numberencoding.model.Word;

public class TreeIndexPhoneEncoder implements PhoneEncoder {
    
    private Dictionary dictionary;
    
    public TreeIndexPhoneEncoder(Dictionary dictionary) {
        this.dictionary = dictionary;
    }
    
    @Override
    public List<Word> encode(Phone phone) throws EncodingException {
        
        Phone refined = phone.refine();
        
        Collector collector = new Collector();
        doEncode(refined, new LinkedList<List<Word>>(), collector);
        
        return collector.retVal.size() == 0 ? null : collector.retVal;
    }
    
    private class Collector implements Consumer<Deque<List<Word>>>{
        final List<Word> retVal = new LinkedList<>();
        @Override
        public void accept(Deque<List<Word>> t) {
            List<Word> joinResult = joinResult(t);
            retVal.addAll(joinResult);
        }
        
        private List<Word> joinResult(Deque<List<Word>> path){
            List<Word> a = path.pollFirst();
            if(path.size() > 0){
                List<Word> b = joinResult(path);
                
                List<Word> retVal = new LinkedList<Word>();
                
                a.forEach(w -> {
                    b.forEach(g -> {
                        retVal.add(w.concatWithSpace(g));
                    });
                });
                return retVal;
            }else{
                List<Word> section = path.pollFirst();
                return section == null ? a : section;
            }
        }
    }
    
    private void doEncode(Word phone, Deque<List<Word>> path, Consumer<Deque<List<Word>>> result){
        
        if(phone != null){
            boolean matchedWordExist = false;
            Word part = null;
            for(int len = 1; (part = phone.take(len)) != null; len++){
                //try to find sub-phone that gives nonempty matching result 
                List<Word> section = dictionary.getMappedWordsByNumber(new Phone(part));
                
                if(section != null){
                    
                    matchedWordExist = true;
                    
                    Deque<List<Word>> increment = new LinkedList<>(path);
                    increment.add(section);
                    
                    doEncode(phone.tail(len), increment, result);
                }
            }
                
            //if now suitable words were found and digit was not used, than can used digit.
            if(!matchedWordExist && !endsWithDigit(path)){
                
                Deque<List<Word>> increment = new LinkedList<>(path);
                increment.add(Collections.singletonList(phone.take(1)));
                
                //proceed with next digit
                doEncode(phone.tail(), increment, result);
            }
        }else{
            result.accept(path);
        }
    }
    
    private boolean endsWithDigit(Deque<List<Word>> path){
        return path.size() > 0 
                && path.getLast().size() == 1  
                && Character.isDigit(path.getLast().get(0).take(1).asString().charAt(0));
    }
}
