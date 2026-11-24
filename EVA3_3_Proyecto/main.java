package EVA3_3_Proyecto;

import java.io.PrintWriter;
import java.util.Locale;
import java.util.Random;

public class main {

    static final int[] TAMANOS = {10, 50, 100, 500, 1000, 5000, 10000, 100000, 1000000};
    static final int LECTURAS = 10;
    static final int MAX_LENTO = 100000; // los O(n^2) no se miden arriba de esto
    static final String[] METODOS = {"Insercion", "Seleccion", "Burbuja", "Quick"};
    static final Random rnd = new Random();

    // ---------- Algoritmos ----------
    static void insercion(int[] a) {
        for (int i = 1; i < a.length; i++) {
            int k = a[i], j = i - 1;
            while (j >= 0 && a[j] > k) {
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = k;
        }
    }

    static void seleccion(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            int m = i;
            for (int j = i + 1; j < a.length; j++)
                if (a[j] < a[m]) m = j;
            int t = a[i]; a[i] = a[m]; a[m] = t;
        }
    }

    static void burbuja(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            boolean cambio = false;
            for (int j = 0; j < a.length - 1 - i; j++) {
                if (a[j] > a[j + 1]) {
                    int t = a[j]; a[j] = a[j + 1]; a[j + 1] = t;
                    cambio = true;
                }
            }
            if (!cambio) break;
        }
    }

    static void quick(int[] a, int lo, int hi) {
        while (lo < hi) {
            int p = a[lo + rnd.nextInt(hi - lo + 1)];
            int i = lo, j = hi;
            while (i <= j) {
                while (a[i] < p) i++;
                while (a[j] > p) j--;
                if (i <= j) {
                    int t = a[i]; a[i] = a[j]; a[j] = t;
                    i++; j--;
                }
            }
            // recursion en el lado chico, iteracion en el grande
            if (j - lo < hi - i) { quick(a, lo, j); lo = i; }
            else { quick(a, i, hi); hi = j; }
        }
    }

    static void ordenar(int metodo, int[] a) {
        switch (metodo) {
            case 0: insercion(a); break;
            case 1: seleccion(a); break;
            case 2: burbuja(a); break;
            default: quick(a, 0, a.length - 1);
        }
    }

    // ---------- Main ----------
    public static void main(String[] args) throws Exception {
        try (PrintWriter lec = new PrintWriter("lecturas.csv");
             PrintWriter res = new PrintWriter("resumen.csv")) {

            lec.print("Metodo,Elementos");
            for (int i = 1; i <= LECTURAS; i++) lec.print(",L" + i);
            lec.println(",Promedio_ms");
            res.println("Elementos,Insercion,Seleccion,Burbuja,Quick");

            for (int n : TAMANOS) {
                double[][] t = new double[METODOS.length][LECTURAS];

                for (int r = 0; r < LECTURAS; r++) {
                    int[] base = new int[n];
                    for (int i = 0; i < n; i++) base[i] = rnd.nextInt(1_000_000);

                    for (int m = 0; m < METODOS.length; m++) {
                        if (m < 3 && n > MAX_LENTO) continue;
                        int[] a = base.clone(); // mismo arreglo para los 4 metodos
                        long t0 = System.nanoTime();
                        ordenar(m, a);
                        t[m][r] = (System.nanoTime() - t0) / 1e6; // ms
                    }
                }

                res.print(n);
                for (int m = 0; m < METODOS.length; m++) {
                    if (m < 3 && n > MAX_LENTO) { res.print(","); continue; }
                    double suma = 0;
                    lec.print(METODOS[m] + "," + n);
                    for (double x : t[m]) {
                        lec.print("," + String.format(Locale.US, "%.4f", x));
                        suma += x;
                    }
                    double prom = suma / LECTURAS;
                    lec.println("," + String.format(Locale.US, "%.4f", prom));
                    res.print("," + String.format(Locale.US, "%.4f", prom));
                }
                res.println();
                System.out.println("n=" + n + " listo");
            }
        }
        System.out.println("Listo: lecturas.csv y resumen.csv");
    }
}
