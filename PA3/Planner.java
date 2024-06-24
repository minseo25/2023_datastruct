// Bongki Moon (bkmoon@snu.ac.kr)

import java.util.*;

public class Planner {
  private Map<String, Airport> airportGraph;
  private class Node implements Comparable<Node> {
    public Flight f;
    public int totalTime;
    public int intervalTime;

    public Node(Flight f, int totalTime, int intervalTime) {
      this.f = f;
      this.totalTime = totalTime;
      this.intervalTime = intervalTime;
    }

    @Override
    // if total time is same, then minimize interval time (waiting time in between flights)
    public int compareTo(Node o) {
      if(this.totalTime!=o.totalTime)
        return this.totalTime - o.totalTime;
      return this.intervalTime - o.intervalTime;
    }
  }

  // constructor
  public Planner(LinkedList<Airport> portList, LinkedList<Flight> fltList) {
    airportGraph = new HashMap<String, Airport>();

    // make graph, save vertex (airports)
    for(Airport ap : portList) {
      airportGraph.put(ap.portCode, ap);
    }
    // save nodes (flights)
    for(Flight flight : fltList) {
      Airport ap = airportGraph.get(flight.srcPort);
      ap.departures.add(flight);
    }
  }

  public Itinerary Schedule(String start, String end, String departure) {
    // S for vertex already done (name)
    // d for saving temporary shortest time
    // prev for saving previous flight
    HashSet<String> S = new HashSet<String>();
    PriorityQueue<Node> d = new PriorityQueue<Node>();
    Map<String, Flight> prev = new HashMap<String, Flight>();
    int departureTime = (Integer.parseInt(departure.substring(0,2))%24)*60 + Integer.parseInt(departure.substring(2,4));
    Airport port;

    // invalid request
    if(airportGraph.get(start)==null || airportGraph.get(end)==null)
      return new Itinerary(false, null);

    // initialization
    S.add(start);
    port = airportGraph.get(start);
    for(Flight f : port.departures) {
      int time = f.start;
      if(time<departureTime) time += 60*24;

      // time to wait for flight + actual flight time
      d.add(new Node(f, (time-departureTime)+f.flightTime, 0));
    }

    int iDepartureTime, itime;
    // dijkstra algorithm
    while(!d.isEmpty()) {
      Node shortest = d.poll();

      // check if already in S
      if(S.contains(shortest.f.destPort)) continue;

      // if not in S, then add it to S (prev update)
      S.add(shortest.f.destPort);
      prev.put(shortest.f.destPort, shortest.f);
      if(shortest.f.destPort.equals(end)) break; // for efficiency

      // add affected nodes to d
      port = airportGraph.get(shortest.f.destPort);
      for(Flight f2 : port.departures) {
        iDepartureTime = (departureTime + shortest.totalTime + port.transferTime)%(60*24);
        itime = f2.start;
        if(itime<iDepartureTime) itime += 60*24;

        // time for past flights + time for transfer + time to wait for next flight + actual flight time
        d.add(new Node(f2, shortest.totalTime+port.transferTime+(itime-iDepartureTime)+f2.flightTime, shortest.intervalTime+(itime-iDepartureTime)+port.transferTime));
      }
    }

    // save selected paths
    ArrayList<Flight> selected = new ArrayList<Flight>();
    String curr = end;
    Flight prevFlight;
    while(prev.containsKey(curr)) {
      prevFlight = prev.get(curr);
      selected.add(0, prevFlight);
      curr = prevFlight.srcPort;
    }

    // return selected paths
    if(selected.isEmpty()) return new Itinerary(false, null);
    return new Itinerary(true, selected);
  }
}

