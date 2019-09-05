package cs.b07.group_0501;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import data.Itinerary;
import user.Admin;
import user.Client;

import java.util.List;

/**
 * An adapter for RecyclerView that displays Itineraries.
 * Code referenced from here:
 * http://code.tutsplus.com/tutorials/getting-started-with-recyclerview-and-cardview-on-android--cms
 * -23465
 *
 * @author Terrence Hung
 */
public class ItineraryRecyclerAdapter extends RecyclerView.Adapter<
        ItineraryRecyclerAdapter.ItineraryViewHolder> {

  private List<Itinerary> itineraries;
  private Client client;
  private Admin admin;

  /**
   * Constructs an ItineraryRecyclerAdapter.
   *
   * @param itineraries The list of itineraries to be displayed.
   * @param client The client that will be viewing the itineraries.
   */
  public ItineraryRecyclerAdapter(List<Itinerary> itineraries, Client client) {
    this.itineraries = itineraries;
    this.client = client;
  }

  /**
   * Constructs an ItineraryRecyclerAdapter.
   *
   * @param itineraries The list of itineraries to be displayed.
   * @param admin The admin that will be viewing the itineraries.
   */
  public ItineraryRecyclerAdapter(List<Itinerary> itineraries, Admin admin) {
    this.itineraries = itineraries;
    this.admin = admin;
  }

  /**
   * Returns the number of Itinerarys to be displayed.
   *
   * @return The number of Itinerarys to be displayed.
   */
  @Override
  public int getItemCount() {
    return itineraries.size();
  }

  /**
   * Specifies the layout that each Itinerary should have when being displayed.
   *
   * @param viewGroup The ViewGroup to use.
   * @param viewType The view type to use.
   * @return The layout for each Itinerary.
   */
  @Override
  public ItineraryViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.itinerary_cards,
            viewGroup, false);
    return new ItineraryViewHolder(view);
  }

  /**
   * Specifies the content of each Flight in the RecyclerView, and moves to another activity when
   * the Itinerary card is clicked.
   *
   * @param ivh The view holder for Itinerarys.
   * @param position The position of the data.
   */
  @Override
  public void onBindViewHolder(ItineraryViewHolder ivh, final int position) {
    final Itinerary itinerary = itineraries.get(position);
    // set the text for every TextView
    ivh.itineraryOrigin.setText(itinerary.getOrigin());
    ivh.itineraryDestination.setText(itinerary.getDestination());
    ivh.itineraryDepartureTime.setText(itinerary.getDepartureTime());
    ivh.itineraryArrivalTime.setText(itinerary.getArrivalTime());
    ivh.itineraryCost.setText(String.format("$%.2f", itinerary.getTotalCost()));
    ivh.itineraryHours.setText(String.format("%.2f", itinerary.getTotalTime()));
    // set the click action
    View.OnClickListener onClickListener = new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        // view this flight in ViewItinerary
        Intent intent = new Intent(view.getContext(), ViewItineraryActivity.class);
        intent.putExtra(Constants.ITINERARY_KEY, itinerary);
        // put either a client or admin into the intent
        if (client != null) {
          intent.putExtra(Constants.CLIENT_KEY, client);
        } else {
          intent.putExtra(Constants.ADMIN_KEY, admin);
        }
        view.getContext().startActivity(intent);
      }
    };
    ivh.cardView.setOnClickListener(onClickListener);
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
   * A ViewHolder for Itinerarys.
   */
  public static class ItineraryViewHolder extends RecyclerView.ViewHolder {

    CardView cardView;
    TextView itineraryOrigin;
    TextView itineraryDestination;
    TextView itineraryDepartureTime;
    TextView itineraryArrivalTime;
    TextView itineraryCost;
    TextView itineraryHours;

    /**
     * Constructs a ViewHolder for Itinerarys.
     *
     * @param itemView The view that contains the cards and Itinerarys.
     */
    public ItineraryViewHolder(View itemView) {
      super(itemView);
      cardView = (CardView)itemView.findViewById(R.id.card_view);
      itineraryOrigin = (TextView)itemView.findViewById(R.id.itineraryOrigin);
      itineraryDestination = (TextView)itemView.findViewById(R.id.itineraryDestination);
      itineraryDepartureTime = (TextView)itemView.findViewById(R.id.itineraryDepartureTime);
      itineraryArrivalTime = (TextView)itemView.findViewById(R.id.itineraryArrivalTime);
      itineraryCost = (TextView)itemView.findViewById(R.id.itineraryCost);
      itineraryHours = (TextView)itemView.findViewById(R.id.itineraryHours);
    }
  }
}
