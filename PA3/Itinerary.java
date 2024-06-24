// Bongki Moon (bkmoon@snu.ac.kr)
import java.util.*;

public class Itinerary
{
  private boolean found = false;
  private ArrayList<Flight> selected = null;
  // constructor
  public Itinerary(boolean found, ArrayList<Flight> selected) {
    this.found = found;
    this.selected = selected;
  }

  public boolean isFound() {
    return found;
  }

  public void print() {
    if(selected==null) {
      System.out.println("No Flight Schedule Found.");
      return;
    }
    for(Flight f : selected) {
      f.print();
    }
    System.out.println();
  }

}
