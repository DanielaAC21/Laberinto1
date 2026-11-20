import java.util.Random;
import java.util.Stack;

public class Main {

    static void programa(String[] args) {
        System.out.println("=== LABERINTO CON PORTALES ===");
        int n, m;
        long seed;
        if (args.length >= 2) {
            n = Integer.parseInt(args[0]);
            m = Integer.parseInt(args[1]);
            seed = args.length >= 3 ? Long.parseLong(args[2]) : 1 + new Random().nextInt(99999);
        } else {
            n = (int) Pantalla.pedirEntero("Filas (N)", 15, 5, 60);
            m = (int) Pantalla.pedirEntero("Columnas (M)", 25, 5, 60);
            seed = Pantalla.pedirEntero("Seed (misma seed = mismo laberinto)",
                    1 + new Random().nextInt(99999), 0, 1_000_000_000L);
        }
        System.out.println("\nLaberinto de " + n + " x " + m + "  |  seed: " + seed);

        String[][] lab = Generador.generarLaberinto(n, m, seed);

        System.out.println("\nLaberinto generado:");
        Pantalla.imprimir(lab, 0);

        int[] inicio = Generador.inicio();
        Stack<int[]> pila = new Stack<>();

        int mejor = Resolver.resolver(lab, pila, inicio[0], inicio[1], 0, Resolver.INF, false);
        if (mejor == Resolver.INF) {
            System.out.println("\nEl laberinto NO tiene solucion: no existe camino de S a E.");
            return;
        }
        Resolver.resolver(lab, pila, inicio[0], inicio[1], 0, mejor + 1, true);

        System.out.println("\nCamino mas corto: " + mejor + " pasos");
        Pantalla.imprimir(lab, 0);
        Pantalla.imprimirLeyenda();
        System.out.println("\nCamino (fila,columna):");
        Pantalla.imprimirCamino(pila, 0);
    }

    public static void main(String[] args) throws InterruptedException {
        Thread hilo = new Thread(null, () -> programa(args), "laberinto", 256L * 1024 * 1024);
        hilo.start();
        hilo.join();
    }
}