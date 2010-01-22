/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package compilador.lexico.Tokens;

/**
 *
 * @author ruben
 */
public class LitNat extends Token{
    String lex;
    public LitNat(String l,int n){
        super(n);
        lex = l;
    }
}
