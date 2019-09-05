package cs.b07.group_0501;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import data.Flight;
import user.Admin;
import user.Client;

import java.util.List;

/**
 * An adapter for RecyclerView that displays Flights.
 * Code referenced from here:
 * http://code.tutsplus.com/tutorials/getting-started-with-recyclerview-and-cardview-on-android--cms
 *
 * @author Terrence Hung
 */
public class FlightRecyclerAdapter extends RecyclerView.Adapter<
        FlightRecyclerAdapter.FlightViewHolder> {

  private List<Flight> flights;
  private Client client;
  private Admin admin;
  
  /**
   * Constructs a FlightRecyclerAdapter.
   *
   * @param flights The list of Flights to be displayed.
   * @param client The client that will be viewing the flights.
   */
  public FlightRecyclerAdapter(List<Flight> flights, Client client) {
    this.flights = flights;
    this.client = client;
  }

  /**
   * Constructs a FlightRecyclerAdapter.
   *
   * @param flights The list of Flights to be displayed.
   * @param admin The admin that will be viewing the flights.
   */
  public FlightRecyclerAdapter(List<Flight> flights, Admin admin) {
    this.flights = flights;
    this.admin = admin;
  }

  /**
   * Returns the number of Flights to be displayed.
   *
   * @return the number of Flights to be displayed.
   */
  @Override
  public int getItemCount() {
    return flights.size();
  }

  /**
   * Specifies the layout that each Flight should have when being displayed.
   *
   * @param viewGroup The ViewGroup to use.
   * @param viewType The view type to use.
   * @return The layout for each Flight.
   */
  @Override
  public FlightViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.flight_cards,
            viewGroup, false);
    return new FlightViewHolder(view);
  }

  /**
   * Specifies the content of each Flight in the RecyclerView, and moves to another activity when
   * the Flight card is clicked.
   *
   * @param fvh The view holder for Flights.
   * @param position The position of the data.
   */
  @Override
  public void onBindViewHolder(FlightViewHolder fvh, final int position) {
    final Flight flight = flights.get(position);
    // set the text for every TextView
    fvh.flightOrigin.setText(flight.getOrigin());
    fvh.flightDestination.setText(flight.getDestination());
    fvh.flightNumber.setText(flight.getFlightNumber());
    fvh.flightDepartureTime.setText(flight.getDepartureTime());
    fvh.flightArrivalTime.setText(flight.getArrivalTime());
    fvh.flightCost.setText(String.format("$%.2f", flight.getPrice()));
    fvh.flightHours.setText(String.format("%.2f", flight.getTravelTime()));
    // set the click action
    View.OnClickListener onClickListener = new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        // view this flight in ViewFlightInfo
        Intent intent = new Intent(view.getContext(), ViewFlightInfo.class);
        // put the flight in the intent
        intent.putExtra(Constants.FLIGHT_KEY, flight);
        // put either a client or an admin into the intent
        if (client != null) {
          intent.putExtra(Constants.CLIENT_KEY, client);
        } else {
          intent.putExtra(Constants.ADMIN_KEY, admin);
        }
        view.getContext().startActivity(intent);
      }
    };
    fvh.cardView.setOnClickListener(onClickListener);
  }

  /**
   * Allows the RecyclerView to use this adapter.
   *
   * @param recyclerView The RecyclerView that will use this adapter.
   */
  @Override
  public void onAttachedToRecyclerView(RecyclerView recyclerView) {
    super.onAttachedToRecyclerView(recyclerView);
  }

  /**
   * A ViewHolder for Flights.
   */
  public static class FlightViewHolder extends RecyclerView.ViewHolder {

    CardView cardView;
    TextView flightOrigin;
    TextView flightDestination;
    TextView flightNumber;
    TextView flightDepartureTime;
    TextView flightArrivalTime;
    TextView flightCost;
    TextView flightHours;

    /**
     * Constructs a ViewHolder for Flights.
     *
     * @param itemView The view that contains the cards and Flights.
     */
    public FlightViewHolder(View itemView) {
      super(itemView);
      cardView = (CardView)itemView.findViewById(R.id.card_view);
      flightOrigin = (TextView)itemView.findViewById(R.id.flightOrigin);
      flightDestination = (TextView)itemView.findViewById(R.id.flightDestination);
      flightNumber = (TextView)itemView.findViewById(R.id.flightNumber);
      flightDepartureTime = (TextView)itemView.findViewById(R.id.flightDepartureTime);
      flightArrivalTime = (TextView)itemView.findViewById(R.id.flightArrivalTime);
      flightCost = (TextView)itemView.findViewById(R.id.flightCost);
      flightHours = (TextView)itemView.findViewById(R.id.flightHours);
    }
  }
}
