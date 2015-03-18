Cubes Puzzle Task
=========================

About
--------------------

The following mapping from letters to digits is given:

E | J N Q | R W X | D S Y | F T | A M | C I V | B K U | L O P | G H Z
e | j n q | r w x | d s y | f t | a m | c i v | b k u | l o p | g h z
0 |   1   |   2   |   3   |  4  |  5  |   6   |   7   |   8   |   9

We want to use this mapping for encoding telephone numbers by words, so
that it becomes easier to remember the numbers.

Functional requirements

Your task is writing a program that finds, for a given phone number, all
possible encodings by words, and prints them. A phone number is an
arbitrary(!) string of dashes - , slashes / and digits. The dashes and
slashes will not be encoded. The words are taken from a dictionary which
is given as an alphabetically sorted ASCII file (one word per line).

How to compile
--------------------

Project uses Maven standard repository, so you can easily use 

    mvn clean package

to create runnable JAR file

How to run it
--------------------

To start game just execute 

    java -cp target/numberencoding-0.0.1-SNAPSHOT.jar com.almasoft.numberencoding.Main doc/dictionary.txt doc/input.txt

and follow further instruction

Thechnical solution
--------------------
The key ideas:
1) Do not use Strings since thay requires byte[] --> char[] transformation that is slow and not necessary
2) precalculate inverse function G for given function Hash (see About section). Function G is that
G(phone) = {Word}, such that for each w in {Word}: Hash(w) = phone
3) Store this function as TreeMap index with correspondent comarator.

This function is calculated once, based on dictionary. 
