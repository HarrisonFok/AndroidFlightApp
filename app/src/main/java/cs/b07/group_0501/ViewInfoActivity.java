package cs.b07.group_0501;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import user.Admin;
import user.Client;

import java.io.Serializable;

/**
 * View any information.
 */
public class ViewInfoActivity extends AppCompatActivity {

  private Client client;
  @SuppressWarnings("FieldCanBeLocal")
  private Admin admin;

  /**
   * Creates a new ViewInfoActivity.
   * @param savedInstanceState the current saved state
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_info);

    Intent intent = getIntent();
    client = (Client) intent.getSerializableExtra(Constants.CLIENT_KEY);
    admin = (Admin) intent.getSerializableExtra(Constants.ADMIN_KEY);

    // Hide the button if you're not admin
    if (admin == null) {
      Button btn = (Button) findViewById(R.id.view_booked_itineraries);
      btn.setVisibility(View.GONE);
      btn = (Button) findViewById(R.id.edit_own_info);
      btn.setVisibility(View.GONE);
    }
    /*
    index 0 = last name
          1 = first name
          2 = email
          3 = address
          4 = card number
          5 = expiry date
     */
    String[] data = client.toString().split(",");
    TextView newlySubmitted = (TextView) findViewById(R.id.display_data);
    // specify text to be displayed in the TextView
    newlySubmitted.setText(String.format("Last Name: %s\nFirst Name: %s\nEmail: %s\n"
                    + "Address: %s\nCredit Card Number: %s\nExpiry Date: %s",
            data[0], data[1], data[2], data[3], data[4], data[5]));

  }

  /**
   * link to ListClientsOrBookedItinerariesActivity, and pass needed info.
   * in the intent
   * @param view the view that called this method
   */
  public void viewBookedItineraries(View view) {
    // check if client has booked itineraries before moving to the proper activity
    if (client.getBookedItineraries().size() != 0) {
      Intent intent = new Intent(this, ListClientsOrBookedItinerariesActivity.class);
      // put client in bundle
      intent.putExtra(Constants.CLIENT_VIEWING_ITINERARIES_KEY, client);
      intent.putExtra(Constants.ITINERARY_KEY, (Serializable) client.getBookedItineraries());
      startActivity(intent); // Starts ListClientsOrBookedItinerariesActivity
    } else {
      Toast.makeText(this, getString(R.string.admin_viewing_client_no_flights), Toast.LENGTH_SHORT)
              .show();
    }
  }

  /**
   * link to EditClientInfoActivity, and pass needed info.
   * in the intent
   * @param view the view that called this method
   */
  public void editSelfInfo(View view) {
    // Specifies the next Activity to move to: EditClientInfoActivity
    Intent intent = new Intent(this, EditClientInfoActivity.class);
    // Passes client into EditClientInfoActivity
    intent.putExtra(Constants.CLIENT_KEY, client);
    startActivity(intent); // Starts EditClientInfoActivity
  }
}
