

package interfaz.pila;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import pila.Dato;
import pila.Instruccion;

/**
 *
 */
public abstract class TraductorPila {

    private Pattern expInt;
    private Pattern expFloat;
    private Pattern expNat;
    private Pattern expChar;
    private Pattern expBool;

    public TraductorPila() {
        expInt = Pattern.compile("-?[1-9][0-9]*");
        expNat = Pattern.compile("[1-9][0-9]*|0");
        expFloat = Pattern.compile("-?[1-9][0-9]*\\.(0|[0-9]*[1-9])");
        expBool = Pattern.compile("true|false");
        expChar = Pattern.compile("'[a-zA-Z]'");
    }

    public Instruccion traducirInstruccion(StringTokenizer st) throws Exception {
        String texto = st.nextToken().toLowerCase();
        if (texto.equals("apila"))
            return traducirApila(st);
        else if (texto.equals("apiladir"))
            return traducirApilaDir(st);
        else if (texto.equals("desapilar"))
            return traducirDesapila(st);
        else if (texto.equals("desapilardir"))
            return traducirDesapilaDir(st);
        else if (texto.equals("menor"))
            return traducirMenor(st);
        else if (texto.equals("menorig"))
            return traducirMenorIg(st);
        else if (texto.equals("mayor"))
            return traducirMayor(st);
        else if (texto.equals("mayorig"))
            return traducirMayorIg(st);
        else if (texto.equals("igual"))
            return traducirIgual(st);
        else if (texto.equals("noigual"))
            return traducirNoIgual(st);
        else if (texto.equals("suma"))
            return traducirSuma(st);
        else if (texto.equals("resta"))
            return traducirResta(st);
        else if (texto.equals("multi"))
            return traducirMult(st);
        else if (texto.equals("div"))
            return traducirDiv(st);
        else if (texto.equals("mod"))
            return traducirMod(st);
        else if (texto.equals("y"))
            return traducirY(st);
        else if (texto.equals("o"))
            return traducirO(st);
        else if (texto.equals("no"))
            return traducirNo(st);
        else if (texto.equals("menos"))
            return traducirMenos(st);
        else if (texto.equals("shl"))
            return traducirShl(st);
        else if (texto.equals("shr"))
            return traducirShr(st);
        else if (texto.equals("int"))
            return traducirInt(st);
        else if (texto.equals("char"))
            return traducirChar(st);
        else if (texto.equals("float"))
            return traducirFloat(st);
        throw new Exception("Nombre de instrucción \""+texto+"\" inválido");
    }

    public Dato traducirDato(StringTokenizer st) throws Exception {
        String str = st.nextToken();
        Dato dato = null;
        if(expChar.matcher(str).matches()) {
            dato = traducirChar(str);
            
        }
        else if(expBool.matcher(str).matches())
            dato = traducirBool(str);
            
        else if(expFloat.matcher(str).matches())
            dato = traducirFloat(str);
            
        else if(expInt.matcher(str).matches()) {
            try {
                dato = traducirInt(str);
                
                if(dato == null)
                    throw new Exception();
            }
            catch (Exception e) { //es demasiado grande
                if(expNat.matcher(str).matches()) {
                    dato = traducirNat(str);
                }
            }
        }
        if(dato == null)
            throw new Exception("Dato "+str+" inválido");
        return dato;
    }

    public ArrayList<Instruccion> traducirPrograma(String programa) throws Exception {
        StringTokenizer st = new StringTokenizer(programa);
        ArrayList<Instruccion> ar = new ArrayList<Instruccion>();
        while(st.hasMoreTokens())
            ar.add(traducirInstruccion(st));
        return ar;
    }

    abstract protected Dato traducirInt(String str) throws Exception;
    abstract protected Dato traducirNat(String str);
    abstract protected Dato traducirFloat(String str);
    abstract protected Dato traducirChar(String str);
    abstract protected Dato traducirBool(String str);

    abstract protected Instruccion traducirApila(StringTokenizer st) throws Exception;
    abstract protected Instruccion traducirApilaDir(StringTokenizer st) throws Exception;
    abstract protected Instruccion traducirDesapila(StringTokenizer st) throws Exception;
    abstract protected Instruccion traducirDesapilaDir(StringTokenizer st) throws Exception;
    abstract protected Instruccion traducirChar(StringTokenizer st) throws Exception;
    abstract protected Instruccion traducirFloat(StringTokenizer st) throws Exception;
    abstract protected Instruccion traducirInt(StringTokenizer st) throws Exception;
    abstract protected Instruccion traducirDiv(StringTokenizer st) throws Exception;
    abstract protected Instruccion traducirIgual(StringTokenizer st) throws Exception;
    abstract protected Instruccion traducirMayor(StringTokenizer st) throws Exception;
    abstract protected Instruccion traducirMayorIg(StringTokenizer st) throws Exception;
    abstract protected Instruccion traducirMenor(StringTokenizer st) throws Exception;
    abstract protected Instruccion traducirMenorIg(StringTokenizer st) throws Exception;
    abstract protected Instruccion traducirMenos(StringTokenizer st) throws Exception;
    abstract protected Instruccion traducirMod(StringTokenizer st) throws Exception;
    abstract protected Instruccion traducirMult(StringTokenizer st) throws Exception;
    abstract protected Instruccion traducirNo(StringTokenizer st) throws Exception;
    abstract protected Instruccion traducirNoIgual(StringTokenizer st) throws Exception;
    abstract protected Instruccion traducirO(StringTokenizer st) throws Exception;
    abstract protected Instruccion traducirResta(StringTokenizer st) throws Exception;
    abstract protected Instruccion traducirShl(StringTokenizer st) throws Exception;
    abstract protected Instruccion traducirShr(StringTokenizer st) throws Exception;
    abstract protected Instruccion traducirSuma(StringTokenizer st) throws Exception;
    abstract protected Instruccion traducirY(StringTokenizer st) throws Exception;
}

