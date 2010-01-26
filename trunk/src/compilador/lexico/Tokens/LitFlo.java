/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package compilador.lexico.Tokens;

/**
 *
 * @author usuario_local
 */
public class LitFlo extends Token{
    public LitFlo(String l,int n){
        super(l,n);
    }

    public String toString(){
        return "Literal real";
    }
}
