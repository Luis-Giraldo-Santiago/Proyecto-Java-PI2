package ejercicios;

import java.util.*;

import us.lsi.common.*;

public class Ejercicio2 {
	
	public static Void quickSort(List<Integer> lista, Integer umbral){
		if(lista.size() <= umbral){
			ordenaInterseccion(lista);
		}else{
			IntPair p = banderaHolandesa(lista);
			quickSort(lista.subList(0, p.first()),umbral);
			quickSort(lista.subList(p.second(), lista.size()),umbral);			
		}
		return null;
	}
	
	public static void ordenaInterseccion(List<Integer> ls) {
		Integer i=1, j;
		while(i<ls.size()) {
			Integer elem = ls.get(i);
			if(elem<ls.get(i-1)) {
				j = i-1;
				while(j>=0) {
					if(j==0 || elem>=ls.get(j-1)) {
						ls.remove(i);
						ls.add(j,elem);
						j=0;
					}
					j--;
				}
			}
			i++;
		}
	}
	
	public static IntPair banderaHolandesa(List<Integer> ls){
		Integer pivote = ls.get(0);
		Integer a=0, b=0;
		Integer c=ls.size();
		while (b<c) {
		    Integer elem =  ls.get(b);
		    if (elem<pivote) {
		    	List2.intercambia(ls,a,b);
				a++;
				b++;
		    } else if (elem>pivote) {
		    	List2.intercambia(ls,b,c-1);
				c--;	
		    } else {
		    	b++;
		    }
		}
		return IntPair.of(a, b);
	}

}
