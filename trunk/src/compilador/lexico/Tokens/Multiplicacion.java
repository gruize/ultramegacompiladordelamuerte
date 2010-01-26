/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package compilador.lexico.Tokens;

/**
 *
 * @author ruben
 */
public class Multiplicacion extends Token{
    public Multiplicacion(int n){
        super(n);
    }

    public String toString(){
        return "Simbolo *";
    }
}
