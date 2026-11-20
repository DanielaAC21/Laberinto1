import java.util.Stack;

public class Resolver {

    static final int INF = Integer.MAX_VALUE;

    static int resolver(String[][] lab, Stack<int[]> pila, int f, int c,
                        int pasos, int limite, boolean dejarCamino) {
        if (f < 0 || f >= lab.length || c < 0 || c >= lab[0].length) return INF;
        if (pasos >= limite) return INF;
        String celda = lab[f][c];
        char tipo = celda.charAt(0);
        if (tipo == 'E') {
            if (dejarCamino) pila.push(new int[]{f, c});
            return pasos;
        }
        if (tipo == '#' || Character.isLowerCase(tipo)) return INF;

        lab[f][c] = Character.toLowerCase(tipo) + celda.substring(1);
        pila.push(new int[]{f, c});

        int arriba = resolver(lab, pila, f - 1, c, pasos + 1, limite, dejarCamino);
        if (dejarCamino && arriba < INF) return arriba;
        int abajo = resolver(lab, pila, f + 1, c, pasos + 1, limite, dejarCamino);
        if (dejarCamino && abajo < INF) return abajo;
        int izquierda = resolver(lab, pila, f, c - 1, pasos + 1, limite, dejarCamino);
        if (dejarCamino && izquierda < INF) return izquierda;
        int derecha = resolver(lab, pila, f, c + 1, pasos + 1, limite, dejarCamino);
        if (dejarCamino && derecha < INF) return derecha;

        int portal = INF;
        if (tipo == 'P') {
            int x = Portales.saltoX(celda);
            int y = Portales.saltoY(celda);
            if (Portales.destinoValido(lab, f + y, c + x)) {
                portal = resolver(lab, pila, f + y, c + x, pasos + 1, limite, dejarCamino);
                if (dejarCamino && portal < INF) return portal;
            }
        }

        pila.pop();
        lab[f][c] = celda;
        return Math.min(Math.min(Math.min(arriba, abajo), Math.min(izquierda, derecha)), portal);
    }
}