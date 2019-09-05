package cs.b07.group_0501;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import data.Flight;
import data.Storage;

import java.io.IOException;



/**
 * An activity for editing flight information.
 * @author Pak Sun Fok
 */
public class EditFlightInfoActivity extends AppCompatActivity {

  private Flight thisFlight;

  /**
   * Creates a new EditFlightInfoActivity and
   * sets the EditText's hints to be the previous known info
   * of the flight.
   * @param savedInstanceState The saved Instance state
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_flight_info);

    // Get the Intent that launched me
    Intent intent = getIntent();
    // Get the flight in the intent
    thisFlight = (Flight) intent.getSerializableExtra(Constants.FLIGHT_KEY);
    // Set each attribute as a hint for the respective input fields
    // - set departure date and time
    EditText oldDepartureDateTime = (EditText) findViewById(R.id.departure_date);
    oldDepartureDateTime.setHint(thisFlight.getDepartureTime());
    // - set arrival date and time
    EditText oldArrivalDateTime = (EditText) findViewById(R.id.arrival_date);
    oldArrivalDateTime.setHint(thisFlight.getArrivalTime());
    // - set airline
    EditText oldAirline = (EditText) findViewById(R.id.airline);
    oldAirline.setHint(thisFlight.getAirline());
    // - set origin
    EditText oldOrigin = (EditText) findViewById(R.id.origin);
    oldOrigin.setHint(thisFlight.getOrigin());
    // - set destination
    EditText oldDestination = (EditText) findViewById(R.id.destination);
    oldDestination.setHint(thisFlight.getDestination());
    // - set price
    EditText oldPrice = (EditText) findViewById(R.id.price);
    oldPrice.setHint(String.valueOf(thisFlight.getPrice()));
    // - set the number of seats
    EditText oldNumSeats = (EditText) findViewById(R.id.num_seats);
    oldNumSeats.setHint(Integer.toString(thisFlight.getNumSeats()));
  }

  /**
   * Given the inputs from the EditTexts, update the corresponding
   * information about a Flight.
   * @param view the view that calls this method.
   */
  @SuppressWarnings("ConstantConditions")
  public void editFlightInfo(View view) {
    try {
      // Get the info from all editTexts
      String newFlightNumber = thisFlight.getFlightNumber();
      String newDepartureDateTime = ((EditText) findViewById(R.id.departure_date))
              .getText().toString();
      String newArrivalDateTime = ((EditText) findViewById(R.id.arrival_date))
              .getText().toString();
      String newAirline = ((EditText) findViewById(R.id.airline)).getText()
              .toString();
      String newOrigin = ((EditText) findViewById(R.id.origin)).getText().toString();
      String newDestination = ((EditText) findViewById(R.id.destination)).getText()
              .toString();
      String newPrice = ((EditText) findViewById(R.id.price)).getText().toString();
      // Change newPrice to double for setting it
      double newDoublePrice = 0;
      if (!newPrice.equals("")) {
        newDoublePrice = Double.parseDouble(newPrice);
      }
      String newNumSeats = ((EditText) findViewById(R.id.num_seats))
              .getText().toString();
      // Change newNumSeats to int for setting it
      int newIntNumSeats = 0;
      if (!newNumSeats.equals("")) {
        newIntNumSeats = Integer.parseInt(newNumSeats);
      }
      String[] newInfo = {newFlightNumber, newDepartureDateTime, newArrivalDateTime,
                          newAirline, newOrigin, newDestination, newPrice, newNumSeats};
      // Find a boolean to represent when the Edit Texts are empty
      Boolean noChange = true;
      for (String info : newInfo) {
        noChange = noChange && info.isEmpty();
      }
      // - set the new departure time
      if (!(newDepartureDateTime.isEmpty())) {
        thisFlight.setDepartureTime(newDepartureDateTime.trim());
      }
      // - set the new arrival time
      if (!(newArrivalDateTime.isEmpty())) {
        thisFlight.setArrivalTime(newArrivalDateTime.trim());
      }
      // - set the new airline
      if (!(newAirline.isEmpty())) {
        thisFlight.setAirline(newAirline);
      }
      // - set the new origin
      if (!(newOrigin.isEmpty())) {
        thisFlight.setOrigin(newOrigin);
      }
      // - set the new destination
      if (!(newDestination.isEmpty())) {
        thisFlight.setDestination(newDestination);
      }
      // - set the new price
      if (!(newPrice.isEmpty())) {
        thisFlight.setPrice(newDoublePrice);
      }
      // - set the new number of seats
      if (!(newNumSeats.isEmpty())) {
        thisFlight.setNumSeats(newIntNumSeats);
      }
      // Display a Toast saying that the info has been updated
      if (!noChange) {
        Toast.makeText(this, R.string.flight_info_changed, Toast.LENGTH_SHORT).show();
        // close the keyboard
        InputMethodManager inputManager = (InputMethodManager)getSystemService(
                Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
      }
    } catch (NumberFormatException ex) {
      Toast.makeText(this, getString(R.string.invalid_number), Toast.LENGTH_SHORT).show();
      // close the keyboard
      InputMethodManager inputManager = (InputMethodManager)getSystemService(
              Context.INPUT_METHOD_SERVICE);
      inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
              InputMethodManager.HIDE_NOT_ALWAYS);
    }

    // update flight map
    Storage.updateFlight(thisFlight.getFlightNumber(), thisFlight);
    // save to flie
    try {
      Storage.saveToFile(this.getApplicationContext().getDir(Constants.PASSWORD_FILE_PATH,
              MODE_PRIVATE).getPath());
    } catch (IOException e) {
      Toast.makeText(this, getString(R.string.cant_output_file), Toast.LENGTH_SHORT).show();
    }
  }

  /**
   * What happens when the back button is pressed.
   */
  @Override
  public void onBackPressed() {
    Intent intent = new Intent(this, AdminMainActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK );
    // client can log in so go to client main activity
    startActivity(intent);
  }
}