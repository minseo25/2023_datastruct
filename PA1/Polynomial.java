import java.util.TreeMap;

public class Polynomial {
  // Exponent, Coefficient (Long for coef overflow)
  TreeMap<Integer, Long> poly;

  // Create an empty polynomial
  public Polynomial() {
    poly = new TreeMap<Integer, Long>();
  }

  // Create a single-term polynomial
  public Polynomial(int coef, int exp) {
    this();
    if(coef != 0) {
      poly.put(exp, (long)coef);
    }
  }

  // Add opnd to 'this' polynomial; 'this' is returned
  public Polynomial add(Polynomial opnd) {
    TreeMap<Integer, Long> poly2 = opnd.poly;
    for(Integer exp: poly2.keySet()) {
      if(poly.containsKey(exp)) {
        // add coefficient
        long new_coef = poly.get(exp) + poly2.get(exp);
        if(new_coef == 0) {
          poly.remove(exp);
        } else {
          poly.put(exp, new_coef);
        }
      } else {
        // simply add a node
        poly.put(exp, poly2.get(exp));
      }
    }
    return this;
  }

  // Subtract opnd from 'this' polynomial; 'this' is returned
  public Polynomial sub(Polynomial opnd) {
    TreeMap<Integer, Long> poly2 = opnd.poly;
    for(Integer exp: poly2.keySet()) {
      if(poly.containsKey(exp)) {
        // add coefficient
        long new_coef = poly.get(exp) - poly2.get(exp);
        if(new_coef == 0) {
          poly.remove(exp);
        } else {
          poly.put(exp, new_coef);
        }
      } else {
        // simply add a node
        poly.put(exp, -1L * poly2.get(exp));
      }
    }

    return this;
  }

  // Print the terms of 'this' polynomial in decreasing order of exponents.
  // No pair of terms can share the same exponent in the printout.
  public void print() {
    for(Integer exp: poly.descendingKeySet()) {
      System.out.print(poly.get(exp)+" "+exp+" ");
    }
  }
}

