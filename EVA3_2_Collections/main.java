package EVA3_2_Collections;

import java.util.Comparator;
import java.util.LinkedList;

public class main {
    public static void main(String[] args) {
        LinkedList<Integer> lista = new LinkedList <>();
        lista.add(500);
        lista.add(300);
        lista.add(900);
        lista.add(100);
        lista.add(800);
        lista.add(700);
        lista.add(200);
        
        //CREAMOS UN COMPARATOR
        Comparator comparator = new Comparator(){
            @Override 
            public int compare (Object o1, Object o2){
                int val1 = (int)o1;
                int val2 = (int)o2;
                
                //el compare regresa un valor entero, que significa:
                //positivo --> o1 es mayor 
                // cero    --> o1 y o2 son iguales
                //negativo --> 02 es mayor
               /*  if (val1 > val2)
                    return 1000;
                else if (val1 == val2)
                    return 0;
                else 
                    return -1;
                */
                return val1 - val2;
                
            }
        };

        lista.sort(comparator);
        System.out.println(lista);
        LinkedList <String> datos = new LinkedList<>();
        datos.add("hola");
        datos.add("");
        datos.add("mundo");
        datos.add("!!!");
        
        Comparator comparator2 = new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                String cade1 = (String) o1;
                String cade2 = (String) o2; 
                return cade2.compareTo(cade2);
            }
        };
        datos.sort(comparator2);
        System.out.println(datos);
    }
}

