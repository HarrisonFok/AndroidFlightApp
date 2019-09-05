package cs.b07.group_0501;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import app.ClientApp;
import ui.DatePickerFragment;
import user.Admin;
import user.Client;

import java.io.Serializable;
import java.util.Calendar;

/**
 *	Search all Flights or Itinerarys given the date, origin,
 *	and destination based on user input
 */
public class SearchFlightItineraryActivity extends FragmentActivity {

  private final Calendar calendar = Calendar.getInstance();
  private int year;
  private int month;
  private int day;
  private RadioGroup searching;
  private Client client;
  private Admin admin;

  /**
   * creates a new SearchFlightItineraryActivity that gives the user abilty to
   * search Itineraries or Flights as needed.
   * @param savedInstanceState the current saved state
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search_flight_itinerary);

    // Set client from intent
    Intent intent = getIntent();
    client = (Client) intent.getSerializableExtra(Constants.CLIENT_KEY);
    admin = (Admin)intent.getSerializableExtra(Constants.ADMIN_KEY);

    // Set default date
    year = calendar.get(Calendar.YEAR);
    month = calendar.get(Calendar.MONTH);
    day = calendar.get(Calendar.DAY_OF_MONTH);
    changeDateText(year, month, day);

    // Set the RadioGroup
    searching = (RadioGroup) findViewById(R.id.search_type);
    searching.check(R.id.sort_flight);
  }

  /**
   * Show the date that can be picked.
   * @param view the view that called this method.
   */
  public void showDatePickerDialog(View view) {
    DialogFragment newFragment = new DatePickerFragment();
    newFragment.show(getFragmentManager(), "datePicker");
  }

  /**
   * Updates the TextView with id display_date to display the year,
   * month and day chosen.
   * @param year the year to change to
   * @param month the month to change to
   * @param day the day to change to
   */
  public void changeDateText(int year, int month, int day) {
    TextView textView = (TextView) findViewById(R.id.display_date);
    textView.setText(String.format("Year: %d  Month: %d  Day: %d ", year, month + 1, day));
    this.year = year;
    this.month = month + 1;
    this.day = day;
  }

  /**
   * Creates an Intent to ListFlightsAndItinerariesActivity and sends a list of
   * Flights or Itineraries based on the radio button checked. The list will contain
   * all Flights/Itineraries with the given date, origin and destination based on the
   * EditText on the current screen.
   * @param view current view
   */
  public void searchFlightItinerary(View view) {
    // Setup next intent and data to send
    Intent intent = new Intent(this, ListFlightsAndItinerariesActivity.class);

    // Get date, origin, and destination from TextEdits
    EditText originText = ((EditText) findViewById(R.id.search_origin));
    EditText destinationText = ((EditText) findViewById(R.id.search_destination));
    String date = String.format("%04d-%02d-%02d", year, month, day);
    String origin = originText.getText().toString();
    String destination = destinationText.getText().toString();

    // Put data into intent for next activity
    intent.putExtra(Constants.CLIENT_KEY, client);
    intent.putExtra(Constants.ADMIN_KEY, admin);
    switch (searching.getCheckedRadioButtonId()) {
      case R.id.sort_flight:
        intent.putExtra(Constants.FLIGHT_KEY,
                (Serializable) ClientApp.getFlights(date, origin, destination));
        break;

      case R.id.sort_itinerary:
        intent.putExtra(Constants.ITINERARY_KEY,
                (Serializable) ClientApp.getItineraries(date, origin, destination));
        break;

      default:
        break;
    }

    // Go to ListFlightsAndItinerariesActivity
    startActivity(intent);
  }
}
