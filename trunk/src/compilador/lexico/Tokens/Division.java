/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package compilador.lexico.Tokens;

/**
 *
 * @author usuario_local
 */
public class Division extends Token{
    public Division(int n){
        super(n);
    }

    public String toString(){
        return "Simbolo /";
    }
}
