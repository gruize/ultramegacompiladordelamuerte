/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.jvm.instrucciones;

import pila.jvm.Constantes;

/**
 * if(value1 mayor value2) result = 1
 * else if(value1 menor value2) result = -1
 * else result = 0
 * ..., long value1, long value2 -> ...,int result
 */
public class Lcmp extends InstJvm {
    public Lcmp() {
        opcode = Constantes.LCMP;
    }
}
