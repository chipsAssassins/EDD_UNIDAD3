package EVA3_1_ordenamientos;

public class main {
    public static void main(String[] args) {
        int[] original, selection;
        original = new int [10];
        llenar(original);
        imprimir(original);
        selectionSort(original);
        imprimir(original);
        
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
}

