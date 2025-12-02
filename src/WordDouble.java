/**
 * Simple pair of String, Double
 * Designed to facilitate finding cosine similarities for 
 * Strings with associated vectors.
 * 
 * Instance variables are accessible directly within
 * this (anonymous) package.
 * 
 * @author Owen Astrachan
 * @date November, 2025
 */

public class WordDouble {
    String word;
    double measure;
    public WordDouble(String s, double v){
        word = s;
        measure = v;
    }
    @Override
    public String toString(){
        return String.format("(%s %1.3f)", word,measure);
    }
    @Override
    public boolean equals(Object o) {
        WordDouble wp = (WordDouble) o;
        return wp.word.equals(this.word);
    }
    
    public static WordDouble of(String w, double v) {
        return new WordDouble(w, v);
    }
}