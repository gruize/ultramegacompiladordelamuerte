/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package compilador.lexico.Tokens;

/**
 *
 * @author usuario_local
 */
public class Absoluto extends Token{
    public Absoluto(int n){
        super(n);
    }

    public String toString(){
        return "Barra de valor Absoluto";
    }
}
