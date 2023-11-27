package org.ASDRsql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scanner {
    private final String source;

    private final List<Token> tokens = new ArrayList<>();

    private static final Map<String, TipoToken> palabrasReservadas;
    static {
        palabrasReservadas = new HashMap<>();
        palabrasReservadas.put("select", TipoToken.SELECT);
        palabrasReservadas.put("from", TipoToken.FROM);
        palabrasReservadas.put("distinct", TipoToken.DISTINCT);
    }

    Scanner(String source){
        this.source = source + " ";
    }

    List<Token> scanTokens(){
        int estado = 0;
        char caracter = 0;
        String lexema = "";
        int inicioLexema = 0;

        for(int i=0; i<source.length(); i++){
            caracter = source.charAt(i);

            switch (estado){
                case 0:
                    if(caracter == '*'){
                        tokens.add(new Token(TipoToken.ASTERISCO, "*", i + 1));
                    }
                    else if(caracter == ','){
                        tokens.add(new Token(TipoToken.COMA, ",", i + 1));
                    }
                    else if(caracter == '.'){
                        tokens.add(new Token(TipoToken.PUNTO, ".", i + 1));
                    }
                    else if(Character.isAlphabetic(caracter)){
                        estado = 1;
                        lexema = lexema + caracter;
                        inicioLexema = i;
                    }
                    break;

                case 1:
                    if(Character.isAlphabetic(caracter) || Character.isDigit(caracter) ){//Mientras estoy loopeando, si el simbolo es letra o numero, se sigue construyendo la cadena
                        lexema = lexema + caracter;
                    }
                    else{//De lo contrario, es decir, si es un espacio, indica que el token que puede ser una palabra reservada o un identificador ha terminado de construirse
                        TipoToken tt = palabrasReservadas.get(lexema);//Busco si el lexema fabricado en el string "lexema" existe en las palabras reservadas
                        if(tt == null){ //Si no existe, lo tomo como el nombre de un identificador y lo agrego a mi lista de tokens como el mismo
                            tokens.add(new Token(TipoToken.IDENTIFICADOR, lexema, inicioLexema + 1));
                        }
                        else{//si existe, de igual forma lo agrego a mi lista de tokens como la palabra reservada que corresponda
                            tokens.add(new Token(tt, lexema, inicioLexema + 1));
                        }
                        //Reinicio los estados para la siguiente iteracion
                        estado = 0;
                        i--;
                        lexema = "";
                        inicioLexema = 0;
                    }
                    break;
            }
        }
        tokens.add(new Token(TipoToken.EOF, "", source.length()));

        return tokens;
    }

}
