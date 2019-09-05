package cs.b07.group_0501;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioGroup;

import app.Sorting;
import data.Flight;
import data.Itinerary;
import user.Admin;
import user.Client;

import java.util.List;

/**
 * An activity to list either Flights or Itinerarys using a RecyclerView and Cards.
 *
 * @author Terrence Hung
 */
public class ListFlightsAndItinerariesActivity extends AppCompatActivity {

  private List<Flight> flights;
  private List<Itinerary> itineraries;
  private RecyclerView rv;
  private Client client;
  private Admin admin;
  private boolean isClient;

  /**
   * Displays all the Flights or Itinerarys given by the previous activity using cards, and can
   * be clicked on to view more info.
   *
   * @param savedInstanceState A saved instance state.
   */
  @Override
  @SuppressWarnings("unchecked")
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list_flights_and_itineraries);

    // set up the RecyclerView
    rv = (RecyclerView)findViewById(R.id.recycler_view);
    LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
    rv.setLayoutManager(llm);
    // get the flights or itineraries from the previous activity
    Intent intent = getIntent();
    flights = (List<Flight>)intent.getSerializableExtra(Constants.FLIGHT_KEY);
    itineraries = (List<Itinerary>)intent.getSerializableExtra(Constants.ITINERARY_KEY);
    // get either the client or admin from the previous activity
    client = (Client)intent.getSerializableExtra(Constants.CLIENT_KEY);
    admin = (Admin)intent.getSerializableExtra(Constants.ADMIN_KEY);
    // check if a client called this activity
    isClient = client != null;
    // check off the sort by cost radio button by default
    RadioGroup sort = (RadioGroup)findViewById(R.id.sorting);
    sort.check(R.id.sortByCost);
    // sort the list that isn't null
    if (flights != null) {
      flights = Sorting.getFlightsByCost(flights);
      // make an adapter for the recyclerview
      rv.setAdapter(makeFlightAdapter(flights));
    } else {
      itineraries = Sorting.getItinerariesByCost(itineraries);
      // make an adapter for the recyclerview
      rv.setAdapter(makeItineraryAdapter(itineraries));
    }
  }

  /**
   * Sorts the Flights or Itinerarys by cost and displays them.
   *
   * @param view The view that calls the sort.
   */
  public void sortByCost(View view) {
    // sort the list that isn't null
    if (flights != null) {
      flights = Sorting.getFlightsByCost(flights);
      // make an adapter for the recyclerview
      rv.setAdapter(makeFlightAdapter(flights));
    } else {
      itineraries = Sorting.getItinerariesByCost(itineraries);
      // make an adapter for the recyclerview
      rv.setAdapter(makeItineraryAdapter(itineraries));
    }
  }

  /**
   * Sorts the Flights or Itinerarys by time and displays them.
   *
   * @param view The view that calls the sort.
   */
  public void sortByTime(View view) {
    // sort the list that isn't null
    if (flights != null) {
      flights = Sorting.getFlightsByTime(flights);
      // make an adapter for the recyclerview
      rv.setAdapter(makeFlightAdapter(flights));
    } else {
      itineraries = Sorting.getItinerariesByTime(itineraries);
      // make an adapter for the recyclerview
      rv.setAdapter(makeItineraryAdapter(itineraries));
    }
  }

  /**
   * Constructs a FlightRecyclerAdapter with either a client or an admin, depending on who called
   * this activity.
   *
   * @param flights The list of flights to be viewed.
   * @return The FlightRecyclerAdapter with a client or admin.
   */
  private FlightRecyclerAdapter makeFlightAdapter(List<Flight> flights) {
    if (isClient) {
      return new FlightRecyclerAdapter(flights, client);
    } else {
      return new FlightRecyclerAdapter(flights, admin);
    }
  }

  /**
   * Constructs an ItineraryRecyclerAdapter with either a client or an admin, depending on who
   * called this activity.
   *
   * @param itineraries The list of itineraries to be viewed.
   * @return The ItineraryRecyclerAdapter with a client or admin.
   */
  private ItineraryRecyclerAdapter makeItineraryAdapter(List<Itinerary> itineraries) {
    if (isClient) {
      return new ItineraryRecyclerAdapter(itineraries, client);
    } else {
      return new ItineraryRecyclerAdapter(itineraries, admin);
    }
  }
}
