package app;

import data.Flight;
import data.Storage;
import user.Admin;
import user.Client;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The application that Admins will have access to.
 * This application will be allowed to view client information, upload client and flight
 * information from CSV files, and manually enter flight info to be stored.
 *
 * @author Terrence Hung
 *
 */
public class AdminApp extends Application {

  private Admin admin;

  /**
   * Constructs a new AdminApp.
   */
  public AdminApp() {
  }

  /**
   *
   * @return an ArrayList containing ao
   */
  public List<Client> getAllClients() {
    // get the all the clients
    return new ArrayList<>(Storage.getClientMap().values());
  }

  /**
   * @param admin The admin that has logged in.
   */
  public void setAdmin(Admin admin) {
    this.admin = admin;
  }

  public Admin getAdmin() {
    return admin;
  }

  /**
   * Uploads client info from a CSV file to be stored in the system.
   * 
   * @param filePath The location of the CSV file with client info.
   * @throws FileNotFoundException if the CSV file cannot be found at the specified location.
   */
  public void uploadClientInfo(String filePath) throws FileNotFoundException {
    Storage.readClientfile(filePath);
  }
  
  /**
   * Uploads flight info from a CSV file to be stored in the system.
   * 
   * @param filePath The location of the CSV file with flight info.
   * @throws FileNotFoundException if the CSV file cannot be found at the specified location.
   */
  public void uploadFlightInfo(String filePath) throws FileNotFoundException {
    Storage.readFlightFile(filePath);
  }
  
  /**
   * Creates and adds a Flight to be stored in the system.
   * 
   * @param flightNumber The new Flight's flight number.
   * @param departureTime The new Flight's departure time, formatted as "YYYY-MM-DD HH:MM" using a
   *     24 hour clock.
   * @param arrivalTime The new Flight's arrival time, formatted as "YYYY-MM-DD HH:MM" using a 24
   *     hour clock.
   * @param airline The new Flight's airline.
   * @param origin The new Flight's origin.
   * @param destination The new Flight's destination.
   * @param price The new Flight's price.
   * @throws ParseException if either departureTime or arrivalTime are not given in that format.
   */
  public void enterFlightInfo(String flightNumber, String departureTime, String arrivalTime, 
      String airline, String origin, String destination, double price, int numSeats)
          throws ParseException {
    // create a new flight with the parameters
    Flight flight = new Flight(flightNumber, departureTime, arrivalTime, airline, origin,
        destination, price, numSeats);
    Storage.addFlight(flight);
  }
  
}
