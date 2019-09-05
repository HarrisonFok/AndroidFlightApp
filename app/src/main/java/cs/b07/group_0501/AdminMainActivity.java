package cs.b07.group_0501;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import app.AdminApp;
import data.Flight;
import data.Storage;
import user.Admin;
import user.Client;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains everything that an administrator can do.
 * @author Pak Sun Fok and Terrence Hung
 */

public class AdminMainActivity extends AppCompatActivity {

  private AdminApp adminApp;
  private Admin admin;

  /**
   * Creates a new AdminMainActivity.
   *
   * @param savedInstanceState The saved instance state.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_admin_main);
    adminApp = new AdminApp();
    // get the admin from the previous activity
    admin = (Admin)(getIntent().getSerializableExtra(Constants.ADMIN_KEY));
  }

  /**
   * Switches activities to SearchFlightItineraryActivity.
   * @param view The view that calls this method.
   */
  public void search(View view) {
    Intent intent = new Intent(this, SearchFlightItineraryActivity.class);
    intent.putExtra(Constants.ADMIN_KEY, admin);
    startActivity(intent);
  }

  /**
   * Gets a list of all Clients and sends it to ListClientsOrBookedItinerariesActivity.
   *
   * @param view The view that calls this method.
   */
  public void viewClients(View view) {
    // get the list of clients
    List<Client> clients = adminApp.getAllClients();
    // put this list into an intent and send it to ListClientsOrBookedItinerariesActivity
    Intent intent = new Intent(this, ListClientsOrBookedItinerariesActivity.class);
    intent.putExtra(Constants.CLIENT_KEY, (Serializable)clients);
    intent.putExtra(Constants.ADMIN_KEY, admin);
    startActivity(intent);
  }

  /**
   * Opens up a file browser in order to select a file to upload.
   *
   * @param view a View from the application.
   */
  public void uploadClientInfo(View view) {
    // create a new file explorer intent
    Intent fileExploreIntent = new Intent(FileBrowserActivity.INTENT_ACTION_SELECT_FILE, null, this,
            FileBrowserActivity.class
    );
    startActivityForResult(fileExploreIntent, Constants.REQUEST_CLIENT_FILE);
  }

  /**
   * Opens up a file browser in order to select a file to upload.
   *
   * @param view a View from the application.
   */
  public void uploadFlightInfo(View view) {
    // create a new file explorer intent
    Intent fileExploreIntent = new Intent(FileBrowserActivity.INTENT_ACTION_SELECT_FILE, null, this,
            FileBrowserActivity.class
    );
    startActivityForResult(fileExploreIntent, Constants.REQUEST_FLIGHT_FILE);
  }

  /**
   * Gets the list of all Flights and passes it to ListFlightsAndItinerariesActivity for viewing.
   *
   * @param view a View from the application.
   */
  public void editFlightInfo(View view) {
    // Create an Intent that specifies where the next Activity should be
    Intent intent = new Intent(this, ListFlightsAndItinerariesActivity.class);
    // Make a list of flight to pass into the intent
    List<Flight> flights = new ArrayList<>(Storage.getFlightMap().values());
    // Put the list of flights and admin into the intent
    intent.putExtra(Constants.FLIGHT_KEY, (Serializable)flights);
    intent.putExtra(Constants.ADMIN_KEY, admin);
    // Start the activity
    startActivity(intent);
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
   * Uploads the selected file to Storage.
   *
   * @param requestCode The request code for either a Client or a Flight file.
   * @param resultCode The result code to indicated whether the file read was successful or not.
   * @param data The intent that contains the file path for the selected file.
   */
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    // try uploading a client file
    if (requestCode == Constants.REQUEST_CLIENT_FILE) {
      // if file read was successful
      if (resultCode == RESULT_OK) {
        // try uploading the file, otherwise show an error toast
        try {
          Storage.adminUploadClient(data.getStringExtra(FileBrowserActivity.returnFileParameter));
          Storage.saveToFile(this.getApplicationContext().getDir(Constants.PASSWORD_FILE_PATH,
                  MODE_PRIVATE).getPath());
          Storage.writeToPassFile(this.getApplicationContext().getDir(Constants.PASSWORD_FILE_PATH,
                  MODE_PRIVATE).getPath());
          Toast.makeText(this, getString(R.string.clients_uploaded), Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException ex) {
          Toast.makeText(this, getString(R.string.cant_get_file), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
          Toast.makeText(this, getString(R.string.cant_output_file), Toast.LENGTH_SHORT).show();
        }
      }
    } else if (requestCode == Constants.REQUEST_FLIGHT_FILE) {
      // try uploading a flight file
      if (resultCode == RESULT_OK) {
        // try uploading the file, otherwise show an error toast
        try {
          Storage.adminUploadFlight(data.getStringExtra(FileBrowserActivity.returnFileParameter));
          Storage.saveToFile(this.getApplicationContext().getDir(Constants.PASSWORD_FILE_PATH,
                  MODE_PRIVATE).getPath());
          Toast.makeText(this, getString(R.string.flights_uploaded), Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException ex) {
          Toast.makeText(this, getString(R.string.cant_get_file), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
          Toast.makeText(this, getString(R.string.cant_output_file), Toast.LENGTH_SHORT).show();
        }
      }
    }
  }
}
