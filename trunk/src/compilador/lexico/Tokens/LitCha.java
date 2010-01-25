/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package compilador.lexico.Tokens;

/**
 *
 * @author ruben
 */
public class LitCha extends Token{
    public LitCha(int n){
        super(n);
    }
    public LitCha(String l, int n){
    	super(l,n);
    }
}
