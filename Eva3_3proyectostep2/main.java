package Eva3_3proyectostep2;

import java.util.Arrays;

public class main {

    
    static final int LIMITE_CUADRATICOS = 1000000;

    public static void main(String[] args) {
        int[] tamanos = {10, 50, 100, 500, 1000, 5000, 10000, 100000, 1000000};

        System.out.printf("%-10s | %-14s | %-14s | %-14s | %-14s | %-14s%n",
                "Datos", "Selection", "Insertion", "Bubble", "Quick", "Merge");
        System.out.println("-".repeat(95));

        for (int n : tamanos) {
            int[] original = new int[n];
            llenar(original);

            String sel = "N/A", ins = "N/A", bub = "N/A";

            if (n <= LIMITE_CUADRATICOS) {
                int[] a = copiar(original);
                long ini = System.nanoTime();
                selectionSort(a);
                sel = formato(System.nanoTime() - ini);

                int[] b = copiar(original);
                ini = System.nanoTime();
                insertionSort(b);
                ins = formato(System.nanoTime() - ini);

                int[] c = copiar(original);
                ini = System.nanoTime();
                bubbleSort(c);
                bub = formato(System.nanoTime() - ini);
            }

            int[] d = copiar(original);
            long ini = System.nanoTime();
            quickSort(d, 0, d.length - 1);
            String qui = formato(System.nanoTime() - ini);

            int[] e = copiar(original);
            ini = System.nanoTime();
            mergeSort(e, 0, e.length - 1);
            String mer = formato(System.nanoTime() - ini);

            // Verificación: todos deben dar lo mismo que Arrays.sort
            int[] esperado = Arrays.copyOf(original, n);
            Arrays.sort(esperado);
            if (!Arrays.equals(d, esperado) || !Arrays.equals(e, esperado)) {
                System.out.println("ERROR: un ordenamiento falló con n = " + n);
            }

            System.out.printf("%-10d | %-14s | %-14s | %-14s | %-14s | %-14s%n",
                    n, sel, ins, bub, qui, mer);
        }
    }

    // Convierte nanosegundos a algo legible
    public static String formato(long ns) {
        if (ns < 1_000_000) return ns + " ns";
        return String.format("%.3f ms", ns / 1_000_000.0);
    }

    public static void llenar(int arreglo[]){
        for (int i = 0; i<arreglo.length; i++) {
            arreglo[i] = (int)(Math.random()*1000000);//originalmente estaba en 100 pero lo cambie a 1000000 para 
                                                        //que se aprecie mejor la diferencia en el tiempo de ejecucion
        }
    }
    public static void imprimir(int arreglo[]){
        for (int i = 0; i<arreglo.length; i++) {
            System.out.print("[" +arreglo[i]+ "]");
        }
        System.out.println("");
    }
    public static int[] copiar(int[] arreglo){
        int resu[] = new int [arreglo.length];
        for (int i = 0; i < arreglo.length; i++) {
            resu[i] = arreglo[i];
        }
        return resu;
    }
    public static void selectionSort(int[] arreglo) {
        for (int i = 0; i <arreglo.length; i++){
            int min = i;
            for (int j = i+1;j<arreglo.length ; j++) {
                if(arreglo[min]> arreglo[j])
                    min=j;
            }
            int temp = arreglo[min];
            arreglo[min] = arreglo[i];
            arreglo[i]=temp;
        }
    }

    public static void insertionSort(int[] arreglo) {
        for (int i = 1; i < arreglo.length; i++) {
            int actual = arreglo[i];
            int j = i - 1;

            // Mueve a la derecha los elementos mayores que "actual"
            while (j >= 0 && arreglo[j] > actual) {
                arreglo[j + 1] = arreglo[j];
                j--;
            }

            arreglo[j + 1] = actual;
        }
    }

    public static void bubbleSort(int[] arreglo){
        for (int i = 0; i < arreglo.length; i++) {
            for (int j = 0; j < arreglo.length - 1; j++) {
                if (arreglo[j]> arreglo[j+1]) {
                    int temp = arreglo[j];
                    arreglo[j]= arreglo[j+1];
                    arreglo[j+1] = temp;
                }
            }
        }
    }

    public static int partition(int[] arreglo, int low, int high) {
        int temp = arreglo[low];
        arreglo[low] = arreglo[high];
        arreglo[high] = temp;

        int pivot = arreglo[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arreglo[j] <= pivot) {
                i++;
                temp = arreglo[i];
                arreglo[i] = arreglo[j];
                arreglo[j] = temp;
            }
        }

        temp = arreglo[i + 1];
        arreglo[i + 1] = arreglo[high];
        arreglo[high] = temp;

        return i + 1;
    }

    public static void quickSort(int[] arreglo, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arreglo, low, high);
            quickSort(arreglo, low, pivotIndex - 1);
            quickSort(arreglo, pivotIndex + 1, high);
        }
    }

    public static void mergeSort(int[] arreglo, int low, int high) {
        if (low < high) {
            int mid = low + (high - low) / 2;
            mergeSort(arreglo, low, mid);
            mergeSort(arreglo, mid + 1, high);
            merge(arreglo, low, mid, high);
        }
    }

    public static void merge(int[] arreglo, int low, int mid, int high) {
        int[] izq = Arrays.copyOfRange(arreglo, low, mid + 1);
        int[] der = Arrays.copyOfRange(arreglo, mid + 1, high + 1);

        int i = 0, j = 0, k = low;

        while (i < izq.length && j < der.length) {
            if (izq[i] <= der[j]) {
                arreglo[k++] = izq[i++];
            } else {
                arreglo[k++] = der[j++];
            }
        }
        while (i < izq.length) arreglo[k++] = izq[i++];
        while (j < der.length) arreglo[k++] = der[j++];
    }
}
