/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.jvm.instrucciones;

import java.io.DataOutputStream;
import java.io.IOException;
import pila.jvm.Tabla;
import pila.jvm.U1;

/**
 *
 * @author Gonzalo Ortiz Jaureguizar (Golthiryus)
 */
public abstract class InstJvmConArg extends InstJvm {

    private boolean tieneArgumento;

    public abstract void appendArgumento(Tabla<U1> tabla);

    @Override
    public void appendTo(Tabla<U1> tabla) {
        super.appendTo(tabla);
        if(tieneArgumento()) {
            appendArgumento(tabla);
        }
    }

    public abstract void salvarArgumento(DataOutputStream dos) throws IOException;

    @Override
    public void salvar(DataOutputStream dos) throws IOException {
        super.salvar(dos);
        if(tieneArgumento()) {
            salvarArgumento(dos);
        }
    }

    public abstract int dameAnchuraArgumentos();

    @Override
    public int dameNumBytes() {
        return 1 + dameAnchuraArgumentos();
    }

    /**
     * @param tieneArg the tieneArg to set
     */
    public void setTieneArgumento(boolean tieneArgumento) {
        this.tieneArgumento = tieneArgumento;
    }

    @Override
    public boolean tieneArgumento() {
        return tieneArgumento;
    }
}
