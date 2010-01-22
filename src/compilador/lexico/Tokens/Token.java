/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package compilador.lexico.Tokens;

/**
 *
 * @author ruben
 */
public class Token {
    int numLinea;
    String lex;
    public Token(String l,int n){
    	lex=l;
        numLinea = n;
    }
    
    public Token(int n){
    	lex="";
    	numLinea=n;
    }
    public Token(){
    	lex="";
    }
    
    public String getLex(){
    	return lex;
    }
    public int numLinea(){
    	return numLinea;
    }
}
