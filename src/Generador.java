import java.util.Random;

public class Generador {

    static Random random;
    static int inicioF = 1, inicioC = 1;

    static int[] inicio() {
        return new int[]{inicioF, inicioC};
    }

    static int ultimaImpar(int tam) {
        return (tam - 2) % 2 == 1 ? tam - 2 : tam - 3;
    }

    static void llenarFila(String[] fila, int c) {
        if (c == fila.length) return;
        fila[c] = "#";
        llenarFila(fila, c + 1);
    }

    static void llenarParedes(String[][] lab, int f) {
        if (f == lab.length) return;
        llenarFila(lab[f], 0);
        llenarParedes(lab, f + 1);
    }

    static int deltaFila(int d) {
        return d == 0 ? -1 : d == 1 ? 1 : 0;
    }

    static int deltaColumna(int d) {
        return d == 2 ? -1 : d == 3 ? 1 : 0;
    }

    static void excavar(String[][] lab, int f, int c) {
        lab[f][c] = "L";
        int primera = random.nextInt(4);
        int salto = random.nextBoolean() ? 1 : 3;
        probarDireccion(lab, f, c, primera, salto, 0);
    }

    static void probarDireccion(String[][] lab, int f, int c, int primera, int salto, int k) {
        if (k == 4) return;
        int d = (primera + k * salto) % 4;
        int df = deltaFila(d), dc = deltaColumna(d);
        int nf = f + 2 * df, nc = c + 2 * dc;
        if (nf > 0 && nf < lab.length - 1 && nc > 0 && nc < lab[0].length - 1
                && lab[nf][nc].equals("#")) {
            lab[f + df][c + dc] = "L";
            excavar(lab, nf, nc);
        }
        probarDireccion(lab, f, c, primera, salto, k + 1);
    }

    static void derrumbar(String[][] lab, int faltan) {
        if (faltan == 0) return;
        int f = 1 + random.nextInt(lab.length - 2);
        int c = 1 + random.nextInt(lab[0].length - 2);
        if (!lab[f][c].equals("L")) {
            derrumbar(lab, faltan);
            return;
        }
        lab[f][c] = "#";
        derrumbar(lab, faltan - 1);
    }

    static String[][] generarLaberinto(int n, int m, long seed) {
        random = new Random(seed);
        String[][] lab = new String[n][m];
        llenarParedes(lab, 0);
        excavar(lab, inicioF, inicioC);
        int finF = ultimaImpar(n), finC = ultimaImpar(m);
        lab[inicioF][inicioC] = "S";
        lab[finF][finC] = "E";
        derrumbar(lab, random.nextInt(2));
        System.out.println("\nPortales generados:");
        Portales.colocarPortales(lab, 3, 1);
        return lab;
    }
}