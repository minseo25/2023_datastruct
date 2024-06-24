// Bongki Moon (bkmoon@snu.ac.kr)

public class Flight
{
  public String srcPort;
  public String destPort;
  public int start = 0;
  public int end = 0;
  public String startTime = "";
  public String endTime = "";
  public int flightTime = 0;

  // constructor
  public Flight(String src, String dest, String stime, String dtime) {
    srcPort = src;
    destPort = dest;
    start = (Integer.parseInt(stime.substring(0,2))%24)*60 + Integer.parseInt(stime.substring(2,4));
    end = (Integer.parseInt(dtime.substring(0,2))%24)*60 + Integer.parseInt(dtime.substring(2,4));
    startTime = stime;
    endTime = dtime;

    flightTime = end-start;
    if(flightTime<0) flightTime += (24*60);
  }

  // print flight info
  public void print() {
    System.out.print("["+srcPort+"->"+destPort+":"+startTime+"->"+endTime+"]");
  }
}
