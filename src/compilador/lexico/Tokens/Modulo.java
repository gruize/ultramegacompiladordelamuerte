/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package compilador.lexico.Tokens;

/**
 *
 * @author ruben
 */
public class Modulo extends Token{
    public Modulo(int n){
        super(n);
    }

    public String toString(){
        return "Simbolo %";
    }
}
