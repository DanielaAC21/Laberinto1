import java.util.Scanner;
import java.util.Stack;

public class Pantalla {

    static final String RESET = "\u001B[0m";
    static final String GRIS = "\u001B[90m";
    static final String VERDE = "\u001B[92m";
    static final String AZUL = "\u001B[94m";
    static final String ROJO = "\u001B[91m";
    static final String MORADO = "\u001B[95m";
    static final String FONDO_VERDE = "\u001B[42m\u001B[30m";

    static final Scanner teclado = new Scanner(System.in);

    static String simbolo(String celda) {
        switch (celda.charAt(0)) {
            case '#': return GRIS + "##" + RESET;
            case 'L': return "  ";
            case 'l': return VERDE + "()" + RESET;
            case 'S':
            case 's': return AZUL + "S " + RESET;
            case 'E': return ROJO + "E " + RESET;
            case 'P': return MORADO + "P " + RESET;
            case 'p': return FONDO_VERDE + "P " + RESET;
            default:  return "??";
        }
    }

    static void imprimirFila(String[] fila, int c) {
        if (c == fila.length) {
            System.out.println();
            return;
        }
        System.out.print(simbolo(fila[c]));
        imprimirFila(fila, c + 1);
    }

    static void imprimir(String[][] lab, int f) {
        if (f == lab.length) return;
        imprimirFila(lab[f], 0);
        imprimir(lab, f + 1);
    }

    static void imprimirLeyenda() {
        System.out.println("\n" + AZUL + "S" + RESET + " inicio   " + ROJO + "E" + RESET + " final   "
                + MORADO + "P" + RESET + " portal   " + VERDE + "()" + RESET + " camino   "
                + FONDO_VERDE + "P " + RESET + " portal usado");
    }

    static void imprimirCamino(Stack<int[]> pila, int i) {
        if (i == pila.size()) {
            System.out.println();
            System.out.println("Longitud: " + (pila.size() - 1) + " pasos");
            return;
        }
        int[] actual = pila.get(i);
        if (i > 0) {
            int[] anterior = pila.get(i - 1);
            int distancia = Math.abs(actual[0] - anterior[0]) + Math.abs(actual[1] - anterior[1]);
            System.out.print(distancia == 1 ? " -> " : " =PORTAL=> ");
        }
        System.out.print("(" + actual[0] + "," + actual[1] + ")");
        imprimirCamino(pila, i + 1);
    }

    static long pedirEntero(String mensaje, long defecto, long minimo, long maximo) {
        System.out.print(mensaje + " [" + defecto + "]: ");
        String texto = teclado.nextLine().trim();
        if (texto.isEmpty()) return defecto;
        try {
            long valor = Long.parseLong(texto);
            if (valor >= minimo && valor <= maximo) return valor;
        } catch (NumberFormatException e) {
        }
        System.out.println("  Escribe un numero entre " + minimo + " y " + maximo + ".");
        return pedirEntero(mensaje, defecto, minimo, maximo);
    }
}