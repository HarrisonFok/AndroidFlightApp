package app;

import android.os.Parcelable;

import data.Flight;
import data.Itinerary;
import data.Storage;
import user.Client;

import java.util.ArrayList;
import java.util.List;

/**
 * The application that Clients will have access to.
 * 
 * <p>Clients can perform the following tasks:
 * <br>- Search for flights
 * <br>- Search for itineraries
 * <br>- Search itineraries sorted by total travel time or by total cost
 * @author Andy Tan
 *
 */
public class ClientApp extends Application {
  private Client client;
  
  /**
   * Creates a new ClientApp with a Client.
   * @param client the Client associated with this ClientApp
   */
  public ClientApp(Client client) {
    super();
    this.client = client;
  }
  
  /**
   * Creates a new ClientApp.
   */
  public ClientApp() {
    super();
    this.client = null;
  }
  
  /**
   * Returns the client.
   * @return the client
   */
  public Client getClient() {
    return client;
  }

  /**
   * Sets the client.
   * @param client the client to set
   */
  public void setClient(Client client) {
    this.client = client;
  }

  /**
   * Returns the client info.
   * @return the client info
   */
  public String getClientInfo() {
    return client.toString();
  }
  
  /**
   * Books an Itinerary for the Client.
   * @param newItin the Itinerary to book
   */
  public void bookItinerary(Itinerary newItin) {
    client.addBooked(newItin);
  }
}
