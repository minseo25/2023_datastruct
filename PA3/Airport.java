// Bongki Moon (bkmoon@snu.ac.kr)
import java.util.*;

public class Airport
{
  public String portCode = "";
  public int transferTime = 0;
  public ArrayList<Flight> departures = null;

  // constructor
  public Airport(String port, String connectTime) {
    portCode = port;
    transferTime = (Integer.parseInt(connectTime.substring(0,2))%24)*60 + Integer.parseInt(connectTime.substring(2,4));
    departures = new ArrayList<Flight>();
  }

  // I will not use this print method
  public void print() {}

}