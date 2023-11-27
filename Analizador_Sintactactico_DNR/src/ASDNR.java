import java.util.*;


public class ASDNR implements Parser{
    Deque<String> pila = new ArrayDeque<>();
    // Tabla de análisis sintáctico para la gramática
    private static final Map<String, Map<TipoToken, String>> tablaASDNR = new HashMap<>();
    static{
       Map<TipoToken, String> Q = new HashMap<>();
       Q.put(TipoToken.SELECT, "select D from T");
       Q.put(TipoToken.DISTINCT, "error");
       Q.put(TipoToken.FROM, "error");
       Q.put(TipoToken.ASTERISCO, "error");
       Q.put(TipoToken.COMA, "error");
       Q.put(TipoToken.PUNTO, "error");
       Q.put(TipoToken.IDENTIFICADOR, "error");
       Q.put(TipoToken.EOF, "error");
       tablaASDNR.put("Q", Q);

       Map<TipoToken, String> D = new HashMap<>();
       D.put(TipoToken.SELECT, "error");
       D.put(TipoToken.DISTINCT, "distinct P");
       D.put(TipoToken.FROM, "error");
       D.put(TipoToken.ASTERISCO, "P");
       D.put(TipoToken.COMA, "error");
       D.put(TipoToken.PUNTO, "error");
       D.put(TipoToken.IDENTIFICADOR, "P");
       D.put(TipoToken.EOF, "error");
       tablaASDNR.put("D", D);

       Map<TipoToken, String> P = new HashMap<>();
       P.put(TipoToken.SELECT, "error");
       P.put(TipoToken.DISTINCT, "error");
       P.put(TipoToken.FROM, "error");
       P.put(TipoToken.ASTERISCO, "*");
       P.put(TipoToken.COMA, "error");
       P.put(TipoToken.PUNTO, "error");
       P.put(TipoToken.IDENTIFICADOR, "A");
       P.put(TipoToken.EOF, "error");
       tablaASDNR.put("P", P);

       Map<TipoToken, String> A = new HashMap<>();
       A.put(TipoToken.SELECT, "error");
       A.put(TipoToken.DISTINCT, "error");
       A.put(TipoToken.FROM, "error");
       A.put(TipoToken.ASTERISCO, "error");
       A.put(TipoToken.COMA, "error");
       A.put(TipoToken.PUNTO, "error");
       A.put(TipoToken.IDENTIFICADOR, "A2 A1");
       A.put(TipoToken.EOF, "error");
       tablaASDNR.put("A", A);

       Map<TipoToken, String> A1 = new HashMap<>();
       A1.put(TipoToken.SELECT, "error");
       A1.put(TipoToken.DISTINCT, "error");
       A1.put(TipoToken.FROM, "Ɛ");
       A1.put(TipoToken.ASTERISCO, "error");
       A1.put(TipoToken.COMA, ", A");
       A1.put(TipoToken.PUNTO, "error");
       A1.put(TipoToken.IDENTIFICADOR, "A2 A1");
       A1.put(TipoToken.EOF, "error");
       tablaASDNR.put("A1", A1);

       Map<TipoToken, String> A2 = new HashMap<>();
       A2.put(TipoToken.SELECT, "error");
       A2.put(TipoToken.DISTINCT, "error");
       A2.put(TipoToken.FROM, "error");
       A2.put(TipoToken.ASTERISCO, "error");
       A2.put(TipoToken.COMA, ", error");
       A2.put(TipoToken.PUNTO, "error");
       A2.put(TipoToken.IDENTIFICADOR, "id A3");
       A2.put(TipoToken.EOF, "error");
       tablaASDNR.put("A2", A2);

       Map<TipoToken, String> A3 = new HashMap<>();
       A3.put(TipoToken.SELECT, "error");
       A3.put(TipoToken.DISTINCT, "error");
       A3.put(TipoToken.FROM, "Ɛ");
       A3.put(TipoToken.ASTERISCO, "error");
       A3.put(TipoToken.COMA, ", Ɛ");
       A3.put(TipoToken.PUNTO, ". id");
       A3.put(TipoToken.IDENTIFICADOR, "id A3");
       A3.put(TipoToken.EOF, "error");
       tablaASDNR.put("A3", A3);

       Map<TipoToken, String> T = new HashMap<>();
       T.put(TipoToken.SELECT, "error");
       T.put(TipoToken.DISTINCT, "error");
       T.put(TipoToken.FROM, "error");
       T.put(TipoToken.ASTERISCO, "error");
       T.put(TipoToken.COMA, "error");
       T.put(TipoToken.PUNTO, "error");
       T.put(TipoToken.IDENTIFICADOR, "T2 T1");
       T.put(TipoToken.EOF, "error");
       tablaASDNR.put("T", T);

       Map<TipoToken, String> T1 = new HashMap<>();
       T1.put(TipoToken.SELECT, "error");
       T1.put(TipoToken.DISTINCT, "error");
       T1.put(TipoToken.FROM, "error");
       T1.put(TipoToken.ASTERISCO, "error");
       T1.put(TipoToken.COMA, ", T");
       T1.put(TipoToken.PUNTO, "error");
       T1.put(TipoToken.IDENTIFICADOR, "error");
       T1.put(TipoToken.EOF, "Ɛ");
       tablaASDNR.put("T1", T1);

       Map<TipoToken, String> T2 = new HashMap<>();
       T2.put(TipoToken.SELECT, "error");
       T2.put(TipoToken.DISTINCT, "error");
       T2.put(TipoToken.FROM, "error");
       T2.put(TipoToken.ASTERISCO, "error");
       T2.put(TipoToken.COMA, "error");
       T2.put(TipoToken.PUNTO, "error");
       T2.put(TipoToken.IDENTIFICADOR, "id T3");
       T2.put(TipoToken.EOF, "error");
       tablaASDNR.put("T2", T2);

       Map<TipoToken, String> T3 = new HashMap<>();
       T3.put(TipoToken.SELECT, "error");
       T3.put(TipoToken.DISTINCT, "error");
       T3.put(TipoToken.FROM, "error");
       T3.put(TipoToken.ASTERISCO, "error");
       T3.put(TipoToken.COMA, "Ɛ");
       T3.put(TipoToken.PUNTO, "error");
       T3.put(TipoToken.IDENTIFICADOR, "id");
       T3.put(TipoToken.EOF, "Ɛ");
       tablaASDNR.put("T3", T3);
    }
    private boolean hayErrores = false;

    private Token preanalisis;
    private final List<Token> tokens;
    Map<TipoToken, String> produccion;

    public ASDNR(List<Token> tokens){
        this.tokens = tokens;
    }

    @Override
    public boolean parse() {

        int i = 0;  //indice
        pila.push("Q");
        produccion = tablaASDNR.get(pila.peek());
        String salida;
        String [] elementos;
        List<Token> tokenError = new ArrayList<>();

        while (!pila.isEmpty() ){
            //identificamos si es alguna palabra reservada
            if (tokens.get(i).tipo.equals(TipoToken.SELECT) && pila.peek().equals("select")){
                pila.pop();
                produccion = tablaASDNR.get(pila.peek());
                i++;
            }
            else if (tokens.get(i).tipo.equals(TipoToken.DISTINCT) && pila.peek().equals("distinct")){
                pila.pop();
                produccion = tablaASDNR.get(pila.peek());
                i++;
            }
            else if (tokens.get(i).tipo.equals(TipoToken.FROM) && pila.peek().equals("from")){
                pila.pop();
                produccion = tablaASDNR.get(pila.peek());
                i++;
            }//identificar si es un identificador
            else if(tokens.get(i).tipo.equals(TipoToken.IDENTIFICADOR) && pila.peek().equals("id")) {
                pila.pop();
                produccion = tablaASDNR.get(pila.peek());
                i++;
            } //identificar si es un asterisco
            else if(tokens.get(i).tipo.equals(TipoToken.ASTERISCO) && pila.peek().equals("*")) {
                pila.pop();
                produccion = tablaASDNR.get(pila.peek());
                i++;
            } // identificar si es una comma
            else if(tokens.get(i).tipo.equals(TipoToken.COMA) && pila.peek().equals(",")) {
                pila.pop();
                produccion = tablaASDNR.get(pila.peek());
                i++;
            } //identificar si es un punto
            else if(tokens.get(i).tipo.equals(TipoToken.PUNTO) && pila.peek().equals(".")) {
                pila.pop();
                produccion = tablaASDNR.get(pila.peek());
                i++;
            } //Hay errores
            else if (produccion.get(tokens.get(i).tipo).equals("error")) {
                hayErrores = true;
                preanalisis = tokens.get(i);
                error(tokenError);
                break;
            }//producciones
            else {
                pila.pop();
                salida = produccion.get(tokens.get(i).tipo);
                elementos = salida.split(" ");
                for (int j = elementos.length - 1; j >= 0 ; j--) {
                    pila.push(elementos[j]);
                }

                if (pila.peek().equals("Ɛ")){
                    pila.pop();
                }

                if (tokens.get(i).tipo.equals(TipoToken.EOF) && pila.isEmpty()){
                    preanalisis = tokens.get(i);
                    break;
                }

                Deque<String> auxiliar = new ArrayDeque<>();

                while (tablaASDNR.get(pila.peek()) == null ){
                    auxiliar.push(pila.peek());
                    pila.pop();
                }

                produccion = tablaASDNR.get(pila.peek());

                while (!auxiliar.isEmpty()) {
                    pila.push(auxiliar.peek());
                    auxiliar.pop();
                }

            }
        }

        if(preanalisis.tipo == TipoToken.EOF && !hayErrores){
            System.out.println("Consulta correcta");
            return  true;
        }else {
            System.err.println("Se encontraron errores");
            System.err.println("Se puso: " + preanalisis.tipo + " en la posicion " + preanalisis.posicion);
            System.err.println("Se esperaba: ");
            imprimirErrrores(tokenError);
        }
        return false;
    }

    private void error(List <Token> tokenError){

        tokenError.add(new Token(TipoToken.SELECT, produccion.get(TipoToken.SELECT)));
        tokenError.add(new Token(TipoToken.DISTINCT, produccion.get(TipoToken.DISTINCT)));
        tokenError.add(new Token(TipoToken.FROM, produccion.get(TipoToken.FROM)));
        tokenError.add(new Token(TipoToken.ASTERISCO, produccion.get(TipoToken.ASTERISCO)));
        tokenError.add(new Token(TipoToken.COMA, produccion.get(TipoToken.COMA)));
        tokenError.add(new Token(TipoToken.PUNTO, produccion.get(TipoToken.PUNTO)));
        tokenError.add(new Token(TipoToken.IDENTIFICADOR, produccion.get(TipoToken.IDENTIFICADOR)));
        tokenError.add(new Token(TipoToken.EOF, produccion.get(TipoToken.EOF)));
    }

    private void imprimirErrrores(List <Token> tokenError){
        for (Token tokenE: tokenError ) {
            if (!tokenE.lexema.equals("error"))
                System.err.println(tokenE.tipo);
        }
    }
}
