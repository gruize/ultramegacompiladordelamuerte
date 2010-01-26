/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package compilador.lexico.Tokens;

/**
 *
 * @author usuario_local
 */
public class Parentesis_a extends Token{
    public Parentesis_a(int n){
        super(n);
    }

    public String toString(){
        return "Parentesis abierto";
    }
}
