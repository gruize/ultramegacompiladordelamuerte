/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package compilador.lexico.Tokens;

/**
 *
 * @author ruben
 */
public class Signo_menos extends Token{
    public Signo_menos(int n){
        super(n);
    }

    public String toString(){
        return "Simbolo -";
    }
}
