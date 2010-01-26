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
    public LitNat(String l,int n){
        super(l,n);
    }

    public String toString(){
        return "Literal natural";
    }
}
