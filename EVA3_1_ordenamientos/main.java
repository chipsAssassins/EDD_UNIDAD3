package EVA3_1_ordenamientos;

public class main {
    public static void main(String[] args) {
        int[] original, selection;
        
        original = new int [10];
        llenar(original);
        System.out.println("");
        imprimir(original);
        System.out.println("");
        long ini = System.nanoTime();
        selectionSort(original);
        long fin = System.nanoTime();
        long tiempo = fin-ini;
        imprimir(original);
        System.out.println(tiempo);
        System.out.println("");
        long ini1 = System.nanoTime();
        insertionSort(original);
        long fin1 = System.nanoTime();
        long tiempo1 = fin1-ini1;
        System.out.println(tiempo1);
        System.out.println("");
        long ini2 = System.nanoTime();
        bubbleSort(original);
        long fin2 = System.nanoTime();
        long tiempo2 = fin2-ini2;
        imprimir(original);
        System.out.println(tiempo2);
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
}

