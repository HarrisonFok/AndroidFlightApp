package cs.b07.group_0501;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import data.Flight;
import data.Itinerary;
import user.Admin;
import user.Client;

import java.util.List;

/**
 * An adapter for RecyclerView that displays Clients.
 * Code referenced from here:
 * http://code.tutsplus.com/tutorials/getting-started-with-recyclerview-and-cardview-on-android--cms
 * -23465
 *
 * @author Terrence Hung
 */
public class ClientRecyclerAdapter extends RecyclerView.Adapter<
        ClientRecyclerAdapter.ClientViewHolder> {

  private List<Client> clients;
  private Admin admin;
  private Itinerary itinerary;
  private Context context;

  /**
   * Constructs a ClientRecyclerAdapter.
   *
   * @param clients The list of Clients to be displayed.
   * @param admin The admin that will be viewing the clients.
   */
  public ClientRecyclerAdapter(List<Client> clients, Admin admin) {
    this.clients = clients;
    this.admin = admin;
  }

  /**
   * Constructs a ClientRecyclerAdapter that will book the given itinerary for a selected client.
   *
   * @param clients The list of clients to be displayed.
   * @param admin The admin that will be viewing the clients.
   * @param itinerary The itinerary that will be booked.
   * @param context The context of the application.
   */
  public ClientRecyclerAdapter(List<Client> clients, Admin admin, Itinerary itinerary,
                               Context context) {
    this.clients = clients;
    this.admin = admin;
    this.itinerary = itinerary;
    this.context = context;
  }

  /**
   * Returns the number of Clients to be displayed.
   *
   * @return the number of Clients to be displayed.
   */
  @Override
  public int getItemCount() {
    return clients.size();
  }

  /**
   * Specifies the layout that each Client should have when being displayed.
   *
   * @param viewGroup The ViewGroup to use.
   * @param viewType The view type to use.
   * @return The layout for each Client.
   */
  @Override
  public ClientViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.client_cards,
            viewGroup, false);
    return new ClientViewHolder(view);
  }

  /**
   * Specifies the content of each Client in the RecyclerView, and moves to another activity when
   * the Client card is clicked.
   *
   * @param cvh The view holder for Clients.
   * @param position The position of the data.
   */
  @Override
  public void onBindViewHolder(ClientViewHolder cvh, final int position) {
    final Client client = clients.get(position);
    cvh.clientName.setText(client.getFullName());
    cvh.clientEmail.setText(client.getEmail());
    // set the click action
    View.OnClickListener onClickListener = new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        // if there is an itinerary, then book it for the client
        if (itinerary != null) {
          // add itinerary to client's bookings
          data.Storage.getClientMap().get(client.getEmail()).addBooked(itinerary);
          // update num of seats of flights
          for (Flight flight : itinerary.getListOfFlights()) {
            flight.setNumSeats((flight.getNumSeats() - 1));
          }
          // display a toast saying the itinerary was booked
          Toast.makeText(view.getContext(), context.getString(R.string.book_successful),
                  Toast.LENGTH_SHORT).show();
        } else {
          // view this client in ViewInfoActivity
          Intent intent = new Intent(view.getContext(), ViewInfoActivity.class);
          // put the client and admin into the intent
          intent.putExtra(Constants.CLIENT_KEY, client);
          intent.putExtra(Constants.ADMIN_KEY, admin);
          view.getContext().startActivity(intent);
        }
      }
    };
    cvh.cardView.setOnClickListener(onClickListener);
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
   * A ViewHolder for Clients.
   */
  public static class ClientViewHolder extends RecyclerView.ViewHolder {

    CardView cardView;
    TextView clientName;
    TextView clientEmail;

    /**
     * Constructs a ViewHolder for Clients.
     *
     * @param itemView The view that contains the cards and Clients.
     */
    public ClientViewHolder(View itemView) {
      super(itemView);
      cardView = (CardView)itemView.findViewById(R.id.card_view);
      clientName = (TextView)itemView.findViewById(R.id.clientName);
      clientEmail = (TextView)itemView.findViewById(R.id.clientEmail);
    }
  }
}
