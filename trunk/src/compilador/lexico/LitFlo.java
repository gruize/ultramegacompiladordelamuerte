/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package compilador.lexico;

/**
 *
 * @author usuario_local
 */
public class LitFlo extends Token{
    String lex;
    public LitFlo(String l,int n){
        super(n);
        lex=l;
    }
}
