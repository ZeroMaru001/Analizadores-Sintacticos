import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.io.InputStreamReader;

public class Main {
    static boolean existenErrores = false;

    public static void main(String[] args) throws IOException {
        ejecutarPrompt();
    }

    private static void ejecutarPrompt() throws IOException{
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        for(;;){
            System.out.print(">>> ");
            String linea = reader.readLine();
            if(linea == null) break; // Presionar Ctrl + D
            ejecutar(linea);
            System.out.println(" ");
            existenErrores = false;
        }
    }

    private static void ejecutar(String source){
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();

        /*for(Token token : tokens){
            System.out.println(token);
        }*/

        Parser parser = new ASDNR(tokens);
        parser.parse();
    }

    /*
    El método error se puede usar desde las distintas clases
    para reportar los errores:
    Interprete.error(....);
     */
    static void error(int linea, String mensaje){
        reportar(linea, "", mensaje);
    }

    private static void reportar(int linea, String donde, String mensaje){
        System.err.println(
                "[linea " + linea + "] Error " + donde + ": " + mensaje
        );
        existenErrores = true;
    }
}