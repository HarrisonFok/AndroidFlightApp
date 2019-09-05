package data;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Representation of an itinerary.
 * @author Pak Sun Fok
 */
public class Itinerary implements Serializable {

  private static final long serialVersionUID = -2698717388149068009L;
  private List<Flight> listOfFlights = new ArrayList<>();
  private double totalCost = 0;
  private double travelTime = 0;
  private String id = "";
  private static final int MILLISECOND_TO_MINUTE = 60000;
  private static final int MINUTE_TO_HOUR = 60;

  /**
   * A constructor for an itinerary.
   */
  public Itinerary() {}

  /**
   * Returns the list of flights in an itinerary.
   * @return listOfFlights - a List containing all Flight objects.
   */
  public List<Flight> getListOfFlights() {
    return listOfFlights;
  }

  /**
   * Returns the total cost of the itinerary.
   * @return totalCost - the total cost of the itinerary.
   */
  public double getTotalCost() {
    return totalCost;
  }

  /**
   * Returns the id of the itinerary.
   * @return id - the id of the itinerary.
   */
  public String getId() {
    return id;
  }

  /**
   * Adds the cost of each Flight in the Itinerary.
   */
  private void calcTotalCost() {
    totalCost = 0;
    for (int i = 0; i < listOfFlights.size(); i++) {
      totalCost += listOfFlights.get(i).getPrice();
    }
  }

  /**
   * Returns the total travel time of the itinerary.
   * @return travelTime - the sum of all the length of the flights.
   */
  public double getTotalTime() {
    return travelTime;
  }

  /**
   * Adds the total time
   * @throws ParseException - if either firstTime or secondTime are not given in that format.
   */
  private void calcTotalTime() throws ParseException {
    travelTime = data.Flight.hourDifference(listOfFlights.get(0).getDepartureTime(),
        getLastFlight().getArrivalTime());
  }

  /**
   * Returns the origin of the itinerary.
   * @return the place where the itinerary starts.
   */
  public String getOrigin() {
    return listOfFlights.get(0).getOrigin();
  }

  /**
   * Returns the destination of the itinerary.
   * @return the place where the itinerary ends.
   */
  public String getDestination() {
    return listOfFlights.get(listOfFlights.size() - 1).getDestination();
  }

  /**
   * Returns the departure time and date of the itinerary.
   * @return the start time and date of the first flight of the itinerary.
   */
  public String getDepartureTime() {
    return listOfFlights.get(0).getDepartureTime();
  }

  /**
   * Returns the departure date of the itinerary.
   * @return the date of the first flight.
   */
  public String getDepartureDate() {
    return listOfFlights.get(0).getDepartureDate();
  }

  /**
   * Returns the arrival date of the itinerary.
   * @return the date of arrival.
   */
  public String getArrivalDate() {
    return listOfFlights.get(listOfFlights.size() - 1).getArrivalDate();
  }

  /**
   * Returns the arrival time and date of the itinerary.
   * @return the arrival time and date of the last flight of the itinerary.
   */
  public String getArrivalTime() {
    return listOfFlights.get(listOfFlights.size() - 1).getArrivalTime();
  }

  /**
   * Adds a flight to an itinerary.
   * @param newFlight - a flight that is to be added.
   */
  public void addFlight(Flight newFlight) {
    id += newFlight.getFlightNumber();
    listOfFlights.add(newFlight);
    calcTotalCost();
    try {
      calcTotalTime();
    } catch (ParseException e) {
      // This should never catch because we will always pass correct formats.
    }
  }

  /**
   * Returns the last Flight in the itinerary.
   * @return the last Flight object in the List
   */
  public Flight getLastFlight() {
    return listOfFlights.get(listOfFlights.size() - 1);
  }

  /**
   * Calculates the time difference in hours and minutes, formatted as HH:MM between two times.
   *
   * @param firstTime The first time, formatted as "YYYY-MM-DD HH:MM" using a 24 hour clock.
   * @param secondTime The second time, formatted as "YYYY-MM-DD HH:MM" using a 24 hour clock.
   * @return The number of hours and minutes between the two times.
   * @throws ParseException if either firstTime or secondTime are not given in that format.
   */
  public String hourMinuteDifference(String firstTime, String secondTime) throws ParseException {
    // construct the two dates
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    Date initialTime = format.parse(firstTime);
    Date finalTime = format.parse(secondTime);
    // get the time difference in milliseconds and convert to minutes
    long timeDifference = (finalTime.getTime() - initialTime.getTime()) / MILLISECOND_TO_MINUTE;
    return String.format("%02d:%02d", timeDifference / MINUTE_TO_HOUR, timeDifference
        % MINUTE_TO_HOUR);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Itinerary other = (Itinerary) obj;
    if (listOfFlights == null) {
      if (other.listOfFlights != null) {
        return false;
      }
    } else if (!listOfFlights.equals(other.listOfFlights)) {
      return false;
    }
    if (Double.doubleToLongBits(totalCost) != Double.doubleToLongBits(other.totalCost)) {
      return false;
    }
    //noinspection RedundantIfStatement
    if (Double.doubleToLongBits(travelTime) != Double.doubleToLongBits(other.travelTime)) {
      return false;
    }
    return true;
  }

  /**
   * Returns the string representation of an Itinerary.
   */
  @Override
  public String toString() {
    String info = "";
    for (Flight flight: listOfFlights) {
      info += flight.toString();
      info += "\n";
    }
    info += String.format("%.2f\n", totalCost);
    try {
      info += hourMinuteDifference(getDepartureTime(), getArrivalTime());
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return info;
  }
}