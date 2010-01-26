/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package compilador.lexico.Tokens;

/**
 *
 * @author ruben
 */
public class Cast_int extends Token{
    public Cast_int(int n){
        super(n);
    }

    public String toString(){
        return "Cast a integer";
    }
}
