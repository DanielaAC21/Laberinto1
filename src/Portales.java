public class Portales {

    static boolean destinoValido(String[][] lab, int f, int c) {
        return f >= 0 && f < lab.length && c >= 0 && c < lab[0].length && !lab[f][c].equals("#");
    }

    static int saltoX(String celda) {
        int coma = celda.indexOf(',', 2);
        return Integer.parseInt(celda.substring(2, coma));
    }

    static int saltoY(String celda) {
        int coma = celda.indexOf(',', 2);
        return Integer.parseInt(celda.substring(coma + 1));
    }

    static void colocarPortales(String[][] lab, int faltan, int numero) {
        if (faltan == 0) return;
        int f = 1 + Generador.random.nextInt(lab.length - 2);
        int c = 1 + Generador.random.nextInt(lab[0].length - 2);
        if (!lab[f][c].equals("L")) {
            colocarPortales(lab, faltan, numero);
            return;
        }
        int x = Generador.random.nextInt(lab[0].length + 1) - lab[0].length / 2;
        int y = Generador.random.nextInt(lab.length + 1) - lab.length / 2;
        lab[f][c] = "P," + x + "," + y;
        String estado = destinoValido(lab, f + y, c + x) ? "valido"
                : (f + y < 0 || f + y >= lab.length || c + x < 0 || c + x >= lab[0].length)
                ? "SIN EFECTO (fuera del laberinto)" : "SIN EFECTO (cae en pared)";
        System.out.printf("  Portal %d en (%d,%d)  x=%+d, y=%+d  ->  destino (%d,%d)  %s%n",
                numero, f, c, x, y, f + y, c + x, estado);
        colocarPortales(lab, faltan - 1, numero + 1);
    }
}