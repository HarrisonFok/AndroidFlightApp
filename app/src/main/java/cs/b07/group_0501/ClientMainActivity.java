package cs.b07.group_0501;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import app.ClientApp;
import data.Itinerary;
import data.Storage;
import user.Client;

/**
 * Contains all abilities performable by a client.
 * @author Ralph
 */

public class ClientMainActivity extends AppCompatActivity {

  private static String accountEmail;
  private ClientApp clientApp;

  /**
   * Creates a new ClientMainActivity.
   * @param savedInstanceState The saved instance state
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_client_main);

    // Set Client in ClientApp
    String testEmail = getIntent().getStringExtra(Constants.EMAIL_KEY);
    if (testEmail != null) {
      accountEmail = testEmail;
    }
    clientApp = new ClientApp(Storage.getClientMap().get(accountEmail));
  }

  /**
   * Switches the current viewable activity from this to ViewBookedFlights
   * passes the current Client to ViewBookedFlights.
   * @param view The view that calls this method
   */
  public void viewBookedItineraries(View view) {
    // Update Client
    updateClient();
    // Gets the data from the client, and get the Client itself
    Client client =  clientApp.getClient();
    if (client.getBookedItineraries().size() != 0) {
      // send the client and itineraries to the correct activity
      List<Itinerary> itineraries =  client.getBookedItineraries();
      Intent intent = new Intent(this, ListClientsOrBookedItinerariesActivity.class);
      intent.putExtra(Constants.ITINERARY_KEY, (Serializable)itineraries);
      intent.putExtra(Constants.CLIENT_VIEWING_ITINERARIES_KEY, client);
      startActivity(intent);
    } else {
      Toast.makeText(this, getString(R.string.no_booked_flights), Toast.LENGTH_SHORT).show();
    }
  }

  /**
   * Switches the current viewable activity from this to SearchFlightItineraryActivity
   * passes the current Client to SearchFlightItineraryActivity.
   * @param view The view that calls this method
   */
  public void searchFlightsItinerary(View view) {
    // Specifies the next Activity to move to: SearchFlightItineraryActivity
    Intent intent = new Intent(this, SearchFlightItineraryActivity.class);
    // Update Client
    updateClient();
    // Gets the data from the client, and get the Client itself
    Client client =  clientApp.getClient();
    // Passes client into SearchFlightItineraryActivity
    intent.putExtra(Constants.CLIENT_KEY, client);
    startActivity(intent); // Starts SearchFlightItineraryActivity
  }

  /**
   * Switches the current viewable activity from this to ViewInfoActivity
   * passes the current Client to ViewInfoActivity.
   * @param view The view that calls this method
   */
  public void viewInfo(View view) {
    // Specifies the next Activity to move to: ViewInfoActivity
    Intent intent = new Intent(this, ViewInfoActivity.class);
    // Update Client
    updateClient();
    // Gets Client itself
    Client client =  clientApp.getClient();
    // Passes Client to ViewInfoActivity
    intent.putExtra(Constants.CLIENT_KEY, client);
    startActivity(intent); // Starts ViewInfoActivity
  }

  /**
   * Switches the current viewable activity from this to EditClientInfoActivity
   * passes the current Client to EditClientInfoActivity.
   * @param view The view that calls this method
   */
  public void editSelfInfo(View view) {
    // Specifies the next Activity to move to: EditClientInfoActivity
    Intent intent = new Intent(this, EditClientInfoActivity.class);
    // Update Client
    updateClient();
    // Gets the data from the client, and get the Client itself
    Client client =  clientApp.getClient();
    // Passes client into EditClientInfoActivity
    intent.putExtra(Constants.CLIENT_KEY, client);
    startActivity(intent); // Starts EditClientInfoActivity
  }

  /**
   * Goes back to the login screen and finishes all other activities.
   * @param view a View from the application.
   */
  public void logout(View view) {
    // Make the intent to LoginActivity
    Intent intent = new Intent(this, LoginActivity.class);
    // Stop all other activities
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    // Time to logout
    startActivity(intent);
  }

  /**
   * Set the newly changed Client into current ClientApp
   */
  public void updateClient() {
    clientApp.setClient(Storage.getClientMap().get(accountEmail));
  }
}
