package EVA3_1_ordenamientos;

import java.util.Arrays;

public class main {
    public static void main(String[] args) {
        int[] original = new int[10];
        llenar(original);

        System.out.println("Original:");
        imprimir(original);
        System.out.println("");

        int[] a = Arrays.copyOf(original, original.length);
        long ini = System.nanoTime();
        selectionSort(a);
        long tiempo = System.nanoTime() - ini;
        System.out.println("Selection:");
        imprimir(a);
        System.out.println(tiempo + " ns\n");

        int[] b = Arrays.copyOf(original, original.length);
        ini = System.nanoTime();
        insertionSort(b);
        tiempo = System.nanoTime() - ini;
        System.out.println("Insertion:");
        imprimir(b);
        System.out.println(tiempo + " ns\n");

        int[] c = Arrays.copyOf(original, original.length);
        ini = System.nanoTime();
        bubbleSort(c);
        tiempo = System.nanoTime() - ini;
        System.out.println("Bubble:");
        imprimir(c);
        System.out.println(tiempo + " ns\n");

        int[] d = Arrays.copyOf(original, original.length);
        ini = System.nanoTime();
        quickSort(d, 0, d.length - 1);
        tiempo = System.nanoTime() - ini;
        System.out.println("Quick:");
        imprimir(d);
        System.out.println(tiempo + " ns");
    }

    public static void llenar(int arreglo[]){
        for (int i = 0; i<arreglo.length; i++) {
            arreglo[i] = (int)(Math.random()*100);
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
        for (int i = 0; i <arreglo.length; i++){{
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
}





