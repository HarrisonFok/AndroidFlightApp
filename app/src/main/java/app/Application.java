package app;

import cs.b07.group_0501.Constants;
import data.Flight;
import data.Itinerary;
import data.Storage;
import user.Admin;
import user.Client;

import java.util.ArrayList;
import java.util.List;

/**
 * The main application which holds all user functions. A user must login to
 * access any of the functions.
 * @author Andy Tan
 *
 */
public class Application {

  /**
   * Create a new Application.
   */
  public Application() {}

  /**
   * Allows the user to login.
   * @param email the email of the client
   * @param pass the password of the client
   * @return 0, if invalid login information was passed.
   *         1, if valid client login information was passed.
   *         2, if valid admin login information was passed.
   */
  public int login(String email, String pass) {
    // check if the email password combo exists
    String passwordFromStorage = Storage.getPassMap().get(email);
    if (passwordFromStorage != null && passwordFromStorage.equals(pass)) {
      // log the appropriate user in
      Client myClient = Storage.getClientMap().get(email);
      Admin myAdmin = Storage.getAdminMap().get(email);
      if (myClient != null) {
        return Constants.LOGIN_CLIENT;
      } else if (myAdmin != null) {
        return Constants.LOGIN_ADMIN;
      }
    }
    return Constants.LOGIN_NULL;
  }

  /**
   * Returns a list of all flights that satisfy all given information.
   * @param date a departure date (in the format YYYY-MM-DD)
   * @param origin a flight origin
   * @param destination a flight destination
   * @return a list of all flights that have the specified departure date,
   *     origin, and destination.
   */
  public static List<Flight> getFlights(String date, String origin, String destination) {
    List<Flight> result = new ArrayList<>();
    for (Flight next : Storage.getFlightMap().values()) {
      if (next.getDepartureDate().equals(date)
              && next.getOrigin().toUpperCase().equals(origin.toUpperCase())
              && next.getDestination().toUpperCase().equals(destination.toUpperCase())) {
        result.add(next);
      }
    }
    return result;
  }

  /**
   * Create and add new Itineraries to the Map of Itinerary in Storage.
   * Execute this after new Flights have been added into the Storage to update
   * the current Map of Itinerary.
   * @param date a departure date (in the format YYYY-MM-DD)
   * @param origin a flight origin
   * @param destination a flight destination
   */
  private static void generateItineraries(String date, String origin, String destination) {
    Storage.clearItinerary();
    for (Flight nextFlight : Storage.getFlightMap().values()) {
      if (nextFlight.getOrigin().toUpperCase().equals(origin.toUpperCase())
              && nextFlight.getDepartureDate().equals(date)) {
        Itinerary newItinerary = new Itinerary();
        newItinerary.addFlight(nextFlight);
        if (nextFlight.getDestination().toUpperCase().equals(destination.toUpperCase())) {
          Storage.addItinerary(newItinerary);
        } else {
          nextFlightSequence(newItinerary, destination);
        }
      }
    }
  }

  /**
   * A helper function for generateItineraries. It iterates through all
   * other flights. If the next checked Flight's departure time is within six
   * hours of the current Itinerary's arrival time, then we add a new
   * Itinerary with that Flight. The Itinerary must not contain a cycle.
   * @param curPath the Itinerary which stores the current flight path
   */
  private static void nextFlightSequence(Itinerary curPath, String destination) {
    for (Flight nextFlight : Storage.getFlightMap().values()) {
      // The origin of the next flight must equal destination of current path
      // Cannot fly to same place twice
      // Must be within six hours of each other
      if (nextFlight.getOrigin().toUpperCase().equals(curPath.getDestination().toUpperCase())
              && isNotCycle(curPath, nextFlight)
              && curPath.getLastFlight().isValidFlight(nextFlight)) {
        // Add back all elements from current path to new copy of Itinerary
        Itinerary newItinerary = new Itinerary();
        for (Flight preFlight : curPath.getListOfFlights()) {
          newItinerary.addFlight(preFlight);
        }
        newItinerary.addFlight(nextFlight);
        // Recurse again if we didn't get to the destination
        if (nextFlight.getDestination().toUpperCase().equals(destination.toUpperCase())) {
          Storage.addItinerary(newItinerary);
        } else {
          nextFlightSequence(newItinerary, destination);
        }
      }
    }
  }

  /**
   * Checks whether a flight will cause a cycle if added to an Itinerary.
   * A cycle occurs when a flight goes to a place that has already been visited
   * in the Itinerary. Returns true in the case that it is not a cycle.
   * @param curPath the Itinerary to check for a cycle
   * @param curFlight the Flight to check
   * @return true, if curFlight will not make a cycle in the Itinerary, curPath.
   *         false, otherwise.
   */
  private static boolean isNotCycle(Itinerary curPath, Flight curFlight) {
    // Cannot fly to same place twice
    boolean check = true;
    for (Flight checkFlight : curPath.getListOfFlights()) {
      if (checkFlight.getOrigin().toUpperCase().equals(curFlight.getDestination().toUpperCase())) {
        check = false;
        break;
      }
    }
    return check;
  }

  /**
   * Returns a list of all itineraries that satisfy all given information.
   * @param date a departure date (in the format YYYY-MM-DD)
   * @param origin a flight origin
   * @param destination a flight destination
   * @return a list of all itineraries that have the specified departure date,
   *     origin, and destination.
   */
  public static List<Itinerary> getItineraries(String date, String origin, String destination) {
    generateItineraries(date, origin, destination);
    List<Itinerary> result = new ArrayList<>();
    for (Itinerary next : Storage.getItineraryMap().values()) {
      result.add(next);
    }
    return result;
  }

}
