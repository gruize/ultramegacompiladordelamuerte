

package interfaz.pila;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import pila.interprete.datos.DatoPila;
import pila.interprete.instrucciones.InstruccionInterprete;

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

    public InstruccionInterprete traducirInstruccion(StringTokenizer st) throws Exception {
        String texto = st.nextToken().toLowerCase();
        if (texto.equals("apilar"))
            return traducirApila(st);
        else if (texto.equals("apilardir"))
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
        else if (texto.equals("castint"))
            return traducirInt(st);
        else if (texto.equals("castchar"))
            return traducirChar(st);
        else if (texto.equals("castfloat"))
            return traducirFloat(st);
        else if (texto.equals("castnat"))
            return traducirNat(st);
        else if (texto.equals("parar"))
            return traducirParar(st);
        else if(texto.equals("in"))
            return traducirIn(st);
        else if(texto.equals("out"))
            return traducirOut(st);
        else if(texto.equals("abs"))
            return traducirAbs(st);
        throw new Exception("Nombre de instrucción \""+texto+"\" inválido");
    }

    public DatoPila traducirDato(StringTokenizer st) throws Exception {
        String str = st.nextToken();
        DatoPila DatoPila = null;
        if(expChar.matcher(str).matches()) {
            DatoPila = traducirChar(str);
            
        }
        else if(expBool.matcher(str).matches())
            DatoPila = traducirBool(str);
            
        else if(expFloat.matcher(str).matches())
            DatoPila = traducirFloat(str);
            
        else if(expNat.matcher(str).matches()) {
            DatoPila = traducirNat(str);

            if(DatoPila == null)
                throw new Exception();
        }
        if(DatoPila == null)
            throw new Exception("DatoPila "+str+" inválido");
        return DatoPila;
    }

    public ArrayList<InstruccionInterprete> traducirPrograma(String programa) throws Exception {
        StringTokenizer st = new StringTokenizer(programa);
        ArrayList<InstruccionInterprete> ar = new ArrayList<InstruccionInterprete>();
        while(st.hasMoreTokens())
            ar.add(traducirInstruccion(st));
        return ar;
    }

    abstract protected DatoPila traducirInt(String str) throws Exception;
    abstract protected DatoPila traducirNat(String str) throws Exception;
    abstract protected DatoPila traducirFloat(String str) throws Exception;
    abstract protected DatoPila traducirChar(String str) throws Exception;
    abstract protected DatoPila traducirBool(String str) throws Exception;

    abstract protected InstruccionInterprete traducirApila(StringTokenizer st) throws Exception;
    abstract protected InstruccionInterprete traducirApilaDir(StringTokenizer st) throws Exception;
    abstract protected InstruccionInterprete traducirDesapila(StringTokenizer st) throws Exception;
    abstract protected InstruccionInterprete traducirDesapilaDir(StringTokenizer st) throws Exception;
    abstract protected InstruccionInterprete traducirChar(StringTokenizer st) throws Exception;
    abstract protected InstruccionInterprete traducirFloat(StringTokenizer st) throws Exception;
    abstract protected InstruccionInterprete traducirNat(StringTokenizer st) throws Exception;
    abstract protected InstruccionInterprete traducirInt(StringTokenizer st) throws Exception;
    abstract protected InstruccionInterprete traducirDiv(StringTokenizer st) throws Exception;
    abstract protected InstruccionInterprete traducirIgual(StringTokenizer st) throws Exception;
    abstract protected InstruccionInterprete traducirMayor(StringTokenizer st) throws Exception;
    abstract protected InstruccionInterprete traducirMayorIg(StringTokenizer st) throws Exception;
    abstract protected InstruccionInterprete traducirMenor(StringTokenizer st) throws Exception;
    abstract protected InstruccionInterprete traducirMenorIg(StringTokenizer st) throws Exception;
    abstract protected InstruccionInterprete traducirMenos(StringTokenizer st) throws Exception;
    abstract protected InstruccionInterprete traducirMod(StringTokenizer st) throws Exception;
    abstract protected InstruccionInterprete traducirMult(StringTokenizer st) throws Exception;
    abstract protected InstruccionInterprete traducirNo(StringTokenizer st) throws Exception;
    abstract protected InstruccionInterprete traducirNoIgual(StringTokenizer st) throws Exception;
    abstract protected InstruccionInterprete traducirO(StringTokenizer st) throws Exception;
    abstract protected InstruccionInterprete traducirResta(StringTokenizer st) throws Exception;
    abstract protected InstruccionInterprete traducirShl(StringTokenizer st) throws Exception;
    abstract protected InstruccionInterprete traducirShr(StringTokenizer st) throws Exception;
    abstract protected InstruccionInterprete traducirSuma(StringTokenizer st) throws Exception;
    abstract protected InstruccionInterprete traducirY(StringTokenizer st) throws Exception;
    abstract protected InstruccionInterprete traducirParar(StringTokenizer st) throws Exception;
    abstract protected InstruccionInterprete traducirIn(StringTokenizer st) throws Exception;
    abstract protected InstruccionInterprete traducirOut(StringTokenizer st) throws Exception;
    abstract protected InstruccionInterprete traducirAbs(StringTokenizer st) throws Exception;


}

