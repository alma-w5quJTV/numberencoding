package com.almasoft.numberencoding;

import java.io.File;
import java.io.IOException;

public class Main {
    /**
     * Main entry point for entire application
     * 
     * If command arguments are not empty than 
     * First -- path to dictionary
     * Second -- path to input file 
     * 
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        
        String dictionary = "doc/dictionary.txt";
        String input = "doc/input.txt";
        if(args.length == 2){
            dictionary = args[0];
            input = args[1];
        }
        
        Processor processor = new Processor();

        processor.init(dictionary);
        processor.process(System.out, new File(input));
    }
}
