package cs.b07.group_0501;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import data.Flight;
import data.Itinerary;
import user.Admin;
import user.Client;

/**
 * View all itineraries.
 */
public class ViewItineraryActivity extends AppCompatActivity {
  private Client myClient;
  private Admin myAdmin;
  private Itinerary myItinerary;

  /**
   * Created a new ViewItinerary Activity that displays the itinerary
   * currently being viewed by the user.
   * @param savedInstanceState the current saved instance
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_itinerary);
    // get the Intent that launched me
    Intent intent = getIntent();
    // get the String that was put into the Intent with key dataKey
    // data of itinerary info
    myItinerary = (Itinerary)intent.getSerializableExtra(Constants.ITINERARY_KEY);
    myClient = (Client)intent.getSerializableExtra(Constants.CLIENT_KEY);
    myAdmin = (Admin)intent.getSerializableExtra(Constants.ADMIN_KEY);
    // if only client are accessing this Activity
    if (myAdmin == null) {
      if (myClient.getBookedItineraries().contains(myItinerary)) {
        Button btn = (Button) findViewById(R.id.my_bt);
        btn.setText(getString(R.string.cancel_booking));
      }
    }
    String data = myItinerary.toString();
    // find the TextView object for TextView with id display_id
    TextView newlySubmitted = (TextView) findViewById(R.id.itinerary_data);
    // specify text to be displayed in the TextView
    newlySubmitted.setText(data);
  }

  /**
   * button that either books Itinerary  or cancel Itinerary.
   * viewable by both Admin and Client
   * @param view view that called this method
   */
  public void booked(View view) {
    Button bt = (Button) view;
    String buttonText = bt.getText().toString();
    // client are either booking or cancelling or just viewing
    if (myAdmin == null) {
      if (buttonText.equals(getString(R.string.book_itinerary))) {
        //add Itinerary to client's bookings.
        data.Storage.getClientMap().get(myClient.getEmail()).addBooked(myItinerary);
        //update num of seats of Itineraries in ItineraryMap in Storage
        String id = myItinerary.getId(); // get id
        for (Flight flight : data.Storage.getItineraryMap().get(id).getListOfFlights()) {
          // reduce current numseat of each flight by 1
          flight.setNumSeats(flight.getNumSeats() - 1);
        }
        // create a toast saying confirm bookings
        Context context = getApplicationContext();
        // get Flights Booked  message from strings.xml
        CharSequence text = getString(R.string.book_successful);
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        // Connects to Booked Flights Activity for review.
        Intent intent = new Intent(this, ClientMainActivity.class);
        // put the bundle into the intent
        intent.putExtra(Constants.CLIENT_KEY, myClient);
        // End the current activity
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

      } else {
        // client probably pressed cancel flight
        if (myClient.getBookedItineraries().contains(myItinerary)) {
          myClient.getBookedItineraries().remove(myItinerary);
          data.Storage.getClientMap().get(myClient.getEmail())
                  .setBookedItineraries(myClient.getBookedItineraries());
          String id = myItinerary.getId(); // get id
          for (Flight flight : data.Storage.getItineraryMap().get(id).getListOfFlights()) {
            // flights cancelled add a seat back to each flight in itinerary
            flight.setNumSeats(flight.getNumSeats() + 1);
            // create a toast saying failed to book flights
            Context context = getApplicationContext();
            // get Book Unsuccessful message from strings.xml
            CharSequence text = getString(R.string.flight_cancelled);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            Intent intent = new Intent(this, ClientMainActivity.class);
            intent.putExtra(Constants.CLIENT_KEY, myClient);
            // End the current activity
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

          }
        }
      }
    } else { // admin are viewing this activity for the client
      if (buttonText.equals(getString(R.string.book_itinerary))) {
        Intent intent = new Intent(this, ListClientsOrBookedItinerariesActivity.class);
        intent.putExtra(Constants.CLIENT_KEY, myClient);
        intent.putExtra(Constants.CLIENT_KEY, myAdmin);
        intent.putExtra(Constants.THAT_IS_SO_TRUE, true);
        intent.putExtra(Constants.ITINERARY_KEY, myItinerary);
        startActivity(intent); // start ClientListActivity
      }
    }
  }
}
