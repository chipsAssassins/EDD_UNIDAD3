
package EVA3_3_Proyecto;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;

public class BenchmarkOrdenamiento {

    // ---------- CONFIGURACION ----------

    static final int[] TAMANOS = {
        10, 50, 100, 500, 1000,
        5000, 10000, 100000,
        1000000, 2000000, 5000000, 10000000
    };

    static final int LECTURAS = 30;
    static final int CALENTAMIENTOS = 8;

    // Los algoritmos O(n^2) no se ejecutan arriba de este tamaño.
    static final int MAX_LENTO = 10000;

    static final String[] METODOS = {
        "Insercion", "Seleccion", "Burbuja", "Quick"
    };

    static final Random rnd = new Random(42);
    static final Random pivoteRnd = new Random(123);

    // ---------- ALGORITMOS ----------

    static void insercion(int[] a) {
        for (int i = 1; i < a.length; i++) {
            int k = a[i];
            int j = i - 1;

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

            for (int j = i + 1; j < a.length; j++) {
                if (a[j] < a[m]) {
                    m = j;
                }
            }

            int t = a[i];
            a[i] = a[m];
            a[m] = t;
        }
    }

    static void burbuja(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            boolean cambio = false;

            for (int j = 0; j < a.length - 1 - i; j++) {
                if (a[j] > a[j + 1]) {
                    int t = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = t;
                    cambio = true;
                }
            }

            if (!cambio) break;
        }
    }

    static void quick(int[] a, int lo, int hi) {

        while (lo < hi) {

            int p = a[lo + pivoteRnd.nextInt(hi - lo + 1)];

            int i = lo;
            int j = hi;

            while (i <= j) {

                while (a[i] < p) i++;
                while (a[j] > p) j--;

                if (i <= j) {
                    int t = a[i];
                    a[i] = a[j];
                    a[j] = t;

                    i++;
                    j--;
                }
            }

            // Recursion en el lado pequeño.
            // Iteracion en el lado grande.

            if (j - lo < hi - i) {
                quick(a, lo, j);
                lo = i;
            } else {
                quick(a, i, hi);
                hi = j;
            }
        }
    }

    static void ordenar(int metodo, int[] a) {

        switch (metodo) {
            case 0:
                insercion(a);
                break;

            case 1:
                seleccion(a);
                break;

            case 2:
                burbuja(a);
                break;

            case 3:
                quick(a, 0, a.length - 1);
                break;
        }
    }

    // ---------- VALIDACION ----------

    static boolean estaOrdenado(int[] a) {

        for (int i = 1; i < a.length; i++) {
            if (a[i - 1] > a[i]) {
                return false;
            }
        }

        return true;
    }

    // ---------- GENERACION DE DATOS ----------

    static int[] generarArreglo(int n) {

        int[] a = new int[n];

        for (int i = 0; i < n; i++) {
            a[i] = rnd.nextInt(1_000_000);
        }

        return a;
    }

    // ---------- CALENTAMIENTO JVM ----------

    static void calentarJVM() {

        System.out.println("Calentando JVM...");

        for (int r = 0; r < CALENTAMIENTOS; r++) {

            int[] base = generarArreglo(1000);

            for (int m = 0; m < METODOS.length; m++) {

                int[] a = base.clone();

                ordenar(m, a);

                if (!estaOrdenado(a)) {
                    throw new RuntimeException(
                        "Error durante el calentamiento"
                    );
                }
            }
        }

        System.out.println("Calentamiento terminado.\n");
    }

    // ---------- ORDEN ALEATORIO DE EJECUCION ----------

    static int[] generarOrden() {

        int[] orden = {0, 1, 2, 3};

        for (int i = orden.length - 1; i > 0; i--) {

            int j = rnd.nextInt(i + 1);

            int temp = orden[i];
            orden[i] = orden[j];
            orden[j] = temp;
        }

        return orden;
    }

    // ---------- ESTADISTICAS ----------

    static double promedio(double[] datos) {

        double suma = 0;

        for (double x : datos) {
            suma += x;
        }

        return suma / datos.length;
    }

    static double mediana(double[] datos) {

        double[] copia = datos.clone();

        Arrays.sort(copia);

        int n = copia.length;

        if (n % 2 == 0) {
            return (copia[n / 2 - 1] + copia[n / 2]) / 2.0;
        }

        return copia[n / 2];
    }

    static double minimo(double[] datos) {

        double min = datos[0];

        for (double x : datos) {
            if (x < min) min = x;
        }

        return min;
    }

    static double maximo(double[] datos) {

        double max = datos[0];

        for (double x : datos) {
            if (x > max) max = x;
        }

        return max;
    }

    static String formato(double x) {
        return String.format(Locale.US, "%.6f", x);
    }

    // ---------- BENCHMARK PRINCIPAL ----------

    public static void main(String[] args) throws Exception {

        calentarJVM();

        try (
            PrintWriter lec = new PrintWriter("lecturas.csv", "UTF-8");
            PrintWriter res = new PrintWriter("resumen.csv", "UTF-8")
        ) {

            // Encabezado de lecturas
            lec.print("Metodo,Elementos");

            for (int i = 1; i <= LECTURAS; i++) {
                lec.print(",L" + i);
            }

            lec.println();

            // Encabezado de resumen
            res.println(
                "Elementos,Metodo,Repeticiones," +
                "Promedio_ms,Mediana_ms,Minimo_ms,Maximo_ms"
            );

            // Recorrer tamaños
            for (int n : TAMANOS) {

                System.out.println(
                    "\nProbando " + n + " elementos..."
                );

                double[][] tiempos =
                    new double[METODOS.length][LECTURAS];

                // Repeticiones
                for (int r = 0; r < LECTURAS; r++) {

                    int[] base = generarArreglo(n);

                    // Cambiar orden de ejecucion en cada lectura
                    int[] orden = generarOrden();

                    for (int metodo : orden) {

                        // Evitar algoritmos cuadraticos grandes
                        if (metodo < 3 && n > MAX_LENTO) {
                            continue;
                        }

                        // Todos reciben los mismos datos
                        int[] a = base.clone();

                        // Medicion
                        long inicio = System.nanoTime();

                        ordenar(metodo, a);

                        long fin = System.nanoTime();

                        double tiempo = (fin - inicio) / 1_000_000.0;

                        // Validacion fuera del tiempo medido
                        if (!estaOrdenado(a)) {

                            throw new RuntimeException(
                                "ERROR: " + METODOS[metodo] +
                                " fallo con " + n + " elementos."
                            );
                        }

                        tiempos[metodo][r] = tiempo;
                    }
                }

                // Generar resultados por algoritmo
                for (int m = 0; m < METODOS.length; m++) {

                    if (m < 3 && n > MAX_LENTO) {
                        continue;
                    }

                    double[] datos = tiempos[m];

                    double prom = promedio(datos);
                    double med = mediana(datos);
                    double min = minimo(datos);
                    double max = maximo(datos);

                    // Archivo de lecturas individuales
                    lec.print(METODOS[m] + "," + n);

                    for (double x : datos) {
                        lec.print("," + formato(x));
                    }

                    lec.println();

                    // Archivo de estadisticas
                    res.println(
                        n + "," +
                        METODOS[m] + "," +
                        LECTURAS + "," +
                        formato(prom) + "," +
                        formato(med) + "," +
                        formato(min) + "," +
                        formato(max)
                    );

                    System.out.println(
                        METODOS[m] +
                        " | Promedio: " + formato(prom) + " ms"
                    );
                }

                System.out.println("Tamaño " + n + " terminado.");
            }
        }

        System.out.println("\n================================");
        System.out.println("BENCHMARK FINALIZADO");
        System.out.println("Archivos generados:");
        System.out.println("- lecturas.csv");
        System.out.println("- resumen.csv");
        System.out.println("================================");
    }
}
