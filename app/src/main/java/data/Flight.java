package data;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A Flight object that will be used when creating Itineraries.
 *
 * @author Terrence Hung
 *
 */
public class Flight implements Serializable {

  private static final long serialVersionUID = -2950127359400152791L;
  private String flightNumber;
  private String departureTime;
  private String arrivalTime;
  private String airline;
  private String origin;
  private String destination;
  private double price;
  private double travelTime;
  private int numSeats;
  private static final int HOUR_CONVERTER = 3600000;
  private static final Logger fLogger = Logger.getLogger(Flight.class.getPackage().getName());

  /**
   * Creates a new Flight.
   *
   * @param flightNumber The flight number.
   * @param departureTime The departure time, formatted as "YYYY-MM-DD HH:MM" using a 24 hour clock.
   * @param arrivalTime The arrival time, formatted as "YYYY-MM-DD HH:MM" using a 24 hour clock.
   * @param airline The flight's airline.
   * @param origin The flight's origin.
   * @param destination The flight's destination.
   * @param price The flight's price.
   * @throws ParseException if any of the dates or times are not formatted properly.
   */
  public Flight(String flightNumber, String departureTime, String arrivalTime, String airline,
      String origin, String destination, double price, int numSeats) throws ParseException {
    this.flightNumber = flightNumber;
    this.departureTime = departureTime;
    this.arrivalTime = arrivalTime;
    this.airline = airline;
    this.origin = origin;
    this.destination = destination;
    this.price = price;
    this.numSeats = numSeats;
    travelTime = hourDifference(departureTime, arrivalTime);
  }
  
  /**
   * Checks if the layover between this Flight and other is between 30 minutes and 6 hours.
   *
   * @param other The next Flight that will depart.
   * @return true if the layover between them is between 30 minutes and 6 hours, false otherwise.
   */
  public boolean isValidFlight(Flight other) {
    double layoverHours = 7;
    try {
      layoverHours = hourDifference(arrivalTime, other.getDepartureTime());
    } catch (ParseException e) {
      // this exception should never be reached since all dates and times will be stored in the
      // proper format
      fLogger.log(Level.SEVERE, "Flight times cannot be parsed.", e);
    }
    return layoverHours <= 6 && layoverHours >= 0.5;
  }

  /**
   * Calculates the time difference in hours between two times.
   *
   * @param firstTime The first time, formatted as "YYYY-MM-DD HH:MM" using a 24 hour clock.
   * @param secondTime The second time, formatted as "YYYY-MM-DD HH:MM" using a 24 hour clock.
   * @return The number of hours between the two times.
   * @throws ParseException if either firstTime or secondTime are not given in that format.
   */
  @SuppressWarnings("UnnecessaryBoxing")
  public static double hourDifference(String firstTime, String secondTime) throws ParseException {
    // construct the two dates
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    Date initialTime = format.parse(firstTime);
    Date finalTime = format.parse(secondTime);
    // get the time difference in milliseconds
    Long timeDifference = new Long(finalTime.getTime() - initialTime.getTime());
    // timeDifference is in milliseconds, convert to hours
    return timeDifference.doubleValue() / HOUR_CONVERTER;
  }

  /**
   * @return This Flight's departure date, formatted as YYYY-MM-DD.
   */
  public String getDepartureDate() {
    String[] departureInfo = departureTime.split(" ");
    return departureInfo[0];
  }
  
  /**
   * @return This Flight's arrival date, formatted as YYYY-MM-DD.
   */
  public String getArrivalDate() {
    String[] arrivalInfo = arrivalTime.split(" ");
    return arrivalInfo[0];
  }
  
  /**
   * @return This Flight's flight number.
   */
  public String getFlightNumber() {
    return flightNumber;
  }

  /**
   * @param flightNumber The flight number to set.
   */
  public void setFlightNumber(String flightNumber) {
    this.flightNumber = flightNumber;
  }

  /**
   * @return This Flight's departure time, formatted as YYYY-MM-DD HH:MM.
   */
  public String getDepartureTime() {
    return departureTime;
  }

  /**
   * @param departureTime The departure time to set, formatted as YYYY-MM-DD HH:MM.
   */
  public void setDepartureTime(String departureTime) {
    this.departureTime = departureTime;
  }

  /**
   * @return This Flight's arrival time, formatted as YYYY-MM-DD HH:MM.
   */
  public String getArrivalTime() {
    return arrivalTime;
  }

  /**
   * @param arrivalTime The arrival time to set, formatted as YYYY-MM-DD HH:MM.
   */
  public void setArrivalTime(String arrivalTime) {
    this.arrivalTime = arrivalTime;
  }

  /**
   * @return This Flight's airline.
   */
  public String getAirline() {
    return airline;
  }

  /**
   * @param airline The airline to set.
   */
  public void setAirline(String airline) {
    this.airline = airline;
  }

  /**
   * @return This Flight's origin.
   */
  public String getOrigin() {
    return origin;
  }

  /**
   * @param origin The origin to set.
   */
  public void setOrigin(String origin) {
    this.origin = origin;
  }

  /**
   * @return This Flight's destination.
   */
  public String getDestination() {
    return destination;
  }

  /**
   * @param destination The destination to set.
   */
  public void setDestination(String destination) {
    this.destination = destination;
  }

  /**
   * @return This Flight's price.
   */
  public double getPrice() {
    return price;
  }

  /**
   * @param price The price to set.
   */
  public void setPrice(double price) {
    this.price = price;
  }

  /**
   * @return This Flight's total travel time, in hours.
   */
  public double getTravelTime() {
    return travelTime;
  }

  /**
   * @param travelTime The travel time to set, in hours.
   */
  public void setTravelTime(double travelTime) {
    this.travelTime = travelTime;
  }

  /**
   * @param numSeats The number of seats to set.
   */
  public void setNumSeats(int numSeats) {
    this.numSeats = numSeats;
  }

  /**
   * @return The number of seats on this Flight.
   */
  public int getNumSeats() {
    return numSeats;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((airline == null) ? 0 : airline.hashCode());
    result = prime * result + ((arrivalTime == null) ? 0 : arrivalTime.hashCode());
    result = prime * result + ((departureTime == null) ? 0 : departureTime.hashCode());
    result = prime * result + ((destination == null) ? 0 : destination.hashCode());
    result = prime * result + ((flightNumber == null) ? 0 : flightNumber.hashCode());
    result = prime * result + ((origin == null) ? 0 : origin.hashCode());
    long temp;
    temp = Double.doubleToLongBits(price);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(travelTime);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
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
    Flight other = (Flight) obj;
    if (airline == null) {
      if (other.airline != null) {
        return false;
      }
    } else if (!airline.equals(other.airline)) {
      return false;
    }
    if (arrivalTime == null) {
      if (other.arrivalTime != null) {
        return false;
      }
    } else if (!arrivalTime.equals(other.arrivalTime)) {
      return false;
    }
    if (departureTime == null) {
      if (other.departureTime != null) {
        return false;
      }
    } else if (!departureTime.equals(other.departureTime)) {
      return false;
    }
    if (destination == null) {
      if (other.destination != null) {
        return false;
      }
    } else if (!destination.equals(other.destination)) {
      return false;
    }
    if (flightNumber == null) {
      if (other.flightNumber != null) {
        return false;
      }
    } else if (!flightNumber.equals(other.flightNumber)) {
      return false;
    }
    if (origin == null) {
      if (other.origin != null) {
        return false;
      }
    } else if (!origin.equals(other.origin)) {
      return false;
    }
    if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price)) {
      return false;
    }
    //noinspection RedundantIfStatement
    if (Double.doubleToLongBits(travelTime) != Double.doubleToLongBits(other.travelTime)) {
      return false;
    }
    return true;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return String.format("%s,%s,%s,%s,%s,%s", flightNumber, departureTime, arrivalTime,
        airline, origin, destination);
  }


}
