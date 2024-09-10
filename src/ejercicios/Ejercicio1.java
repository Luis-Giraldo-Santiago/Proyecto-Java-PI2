package ejercicios;

import java.math.BigInteger;

public class Ejercicio1 {
	
	public static BigInteger factorialIte(Integer n) {
		BigInteger r = BigInteger.ONE;
		while(n!=0) {
			r = r.multiply(BigInteger.valueOf((long) n));
			n -= 1;
		}
		return r;
	}
	
	public static BigInteger factorialRec(Integer n) {
		return factorialRec(n,BigInteger.ONE);
	}
	private static BigInteger factorialRec(Integer n, BigInteger a) {
		BigInteger r;
		if (n == 0) {
			r = a;
		} else {
			r = factorialRec(n - 1, a.multiply(BigInteger.valueOf((long) n)));
		}
		return r;
	}
	
	
	
	public static Double factorialIte_Double(Integer n) {
		Double r = 1.;
		while(n!=0.) {
			r = r * n;
			n = n - 1;
		}
		return r;
	}
	
	public static Double factorialRec_Double(Integer n) {
		return factorialRec_Double(n,1.);
	}
	private static Double factorialRec_Double(Integer n, Double a) {
		Double r;
		if (n == 0.) {
			r = a;
		} else {
			r = factorialRec_Double(n - 1, a * n);
		}
		return r;
	}

}
