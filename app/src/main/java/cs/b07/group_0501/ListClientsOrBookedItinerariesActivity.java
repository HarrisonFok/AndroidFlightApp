package cs.b07.group_0501;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import app.Sorting;
import data.Itinerary;
import user.Admin;
import user.Client;

import java.util.List;

/**
 * Lists all the clients in cards which can be clicked to view their info, or lists itineraries
 * that can be clicked to book for clients.
 *
 * @author Terrence Hung
 */
public class ListClientsOrBookedItinerariesActivity extends AppCompatActivity {

  /**
   * Displays all Clients in cards which can be clicked for more info about the Client, or
   * displays itineraries in cards which can be clicked to either book it for a Client if being
   * viewed by an Admin, or for viewing a Client's booked itineraries.
   *
   * @param savedInstanceState A saved instance state.
   */
  @Override
  @SuppressWarnings("unchecked")
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list_clients_or_booked_itineraries);

    // set up the RecyclerView
    RecyclerView rv = (RecyclerView)findViewById(R.id.recycler_view_clients_or_itineraries);
    LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
    rv.setLayoutManager(llm);
    // get the clients or itineraries from the previous activity
    Intent intent = getIntent();
    List<Client> clients = (List<Client>)intent.getSerializableExtra(Constants.CLIENT_KEY);
    // get the admin from the previous activity, because only an admin can access this activity
    Admin admin = (Admin)intent.getSerializableExtra(Constants.ADMIN_KEY);
    // check if this activity is being used to book an itinerary for a client
    if (intent.getBooleanExtra(Constants.THAT_IS_SO_TRUE, false)) {
      clients = Sorting.getClientsByName(clients);
      Itinerary itinerary = (Itinerary)intent.getSerializableExtra(Constants.ITINERARY_KEY);
      // pass all this in to the adapter
      ClientRecyclerAdapter adapter = new ClientRecyclerAdapter(clients, admin, itinerary, this);
      rv.setAdapter(adapter);
    }
    // so we are not trying to book an itinerary for a client
    List<Itinerary> itineraries = (List<Itinerary>)intent.getSerializableExtra(
            Constants.ITINERARY_KEY);
    // check which list is not null then display those
    if (clients != null) {
      // sort the clients
      clients = Sorting.getClientsByName(clients);
      // make an adapter and then set it
      ClientRecyclerAdapter adapter = new ClientRecyclerAdapter(clients, admin);
      rv.setAdapter(adapter);
    } else {
      // showing a client's booked itineraries
      Client client = (Client)intent.getSerializableExtra(Constants.CLIENT_VIEWING_ITINERARIES_KEY);
      // sort itineraries by time then make the adapter and display
      itineraries = Sorting.getItinerariesByTime(itineraries);
      ItineraryRecyclerAdapter adapter = new ItineraryRecyclerAdapter(itineraries, client);
      rv.setAdapter(adapter);
    }
  }
}
