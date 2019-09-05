package cs.b07.group_0501;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import data.Flight;
import user.Admin;
import user.Client;

/**
 * Displays the Flight information that are allowed to be edited.
 */
public class ViewFlightInfo extends AppCompatActivity {
  private Flight myFlight;
  private Admin myAdmin;
  @SuppressWarnings("FieldCanBeLocal")
  private Client myClient;

  /**
   * Creates anew Activity that displays the flight info as needed.
   * @param savedInstanceState the current saved state
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_flight_info);

    // get the Intent that launched me.
    Intent intent = getIntent();
    // get the String that was put into the Intent with key dataKey
    // data of flight info.
    myFlight = (Flight) intent.getSerializableExtra(Constants.FLIGHT_KEY);
    myAdmin = (Admin) intent.getSerializableExtra(Constants.ADMIN_KEY);
    myClient = (Client) intent.getSerializableExtra(Constants.CLIENT_KEY);
    if (myAdmin == null) {
      Button myButton = (Button)findViewById(R.id.myButt);
      myButton.setVisibility(View.GONE);
    }
    String[] data =  myFlight.toString().split(",");
    TextView newlySubmitted = (TextView) findViewById(R.id.display_data);
    // specify text to be displayed in the TextView
    newlySubmitted.setText(String.format("FlightNumber: %s\nDepartureTime: %s\nArrivalTime: %s\n"
             + "Airline: %s\nOrigin: %s\nDestination: %s\nPrice: $%.2f\nNumberOfSeatsAvailable: %s",
            data[0], data[1], data[2], data[3], data[4], data[5],
            myFlight.getPrice(), myFlight.getNumSeats()));
  }

  /**
   * button that takes intent to editflightinfo.
   * @param view view object
   */
  public void edit(View view) {
    // When edit flight button is pressed, create a new intent that
    // goes to the EditflightInfoActivity, passing the flight object along.
    Intent intent = new Intent(this, EditFlightInfoActivity.class);
    intent.putExtra(Constants.FLIGHT_KEY, myFlight);
    intent.putExtra(Constants.ADMIN_KEY, myAdmin);
    // End the current activity
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    startActivity(intent);
  }
}
