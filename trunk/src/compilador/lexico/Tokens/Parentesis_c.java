/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package compilador.lexico.Tokens;

/**
 *
 * @author ruben
 */
public class Parentesis_c extends Token{
    public Parentesis_c(int n){
        super(n);
    }

    public String toString(){
        return "Parentesis cerrado";
    }
}
