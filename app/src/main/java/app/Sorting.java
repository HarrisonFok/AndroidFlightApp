package app;

import data.Flight;
import data.Itinerary;
import user.Client;

import java.util.List;

/**
 * The class where all the sorting happens.
 */
public class Sorting {

  /**
   * Returns a list of flights that is sorted by cost.
   * @param myFlights a list of itineraries to sort
   * @return a list of all itineraries that have the specified departure date,
   *     origin, and destination sorted by cost (lowest to highest).
   */
  public static List<Flight> getFlightsByCost(List<Flight> myFlights) {
    quickSortFlightCost(myFlights, 0, myFlights.size() - 1);
    return myFlights;
  }

  /**
   * Returns a list of flights that is sorted by cost.
   * @param myFlights a list of itineraries to sort
   * @return a list of all itineraries that have the specified departure date,
   *     origin, and destination sorted by travel time (lowest to highest).
   */
  public static List<Flight> getFlightsByTime(List<Flight> myFlights) {
    quickSortFlightTime(myFlights, 0, myFlights.size() - 1);
    return myFlights;
  }

  /**
   * Returns a list of itinerary that is sorted by cost.
   * @param myItineraries a list of itineraries to sort
   * @return a list of all itineraries that have the specified departure date,
   *     origin, and destination sorted by cost (lowest to highest).
   */
  public static List<Itinerary> getItinerariesByCost(List<Itinerary> myItineraries) {
    quickSortCost(myItineraries, 0, myItineraries.size() - 1);
    return myItineraries;
  }

  /**
   * Returns a list of Clients that is sorted by full name.
   * @param myClients a list of clients to sort
   * @return a list of all itineraries that have the specified departure date,
   *     origin, and destination sorted by travel time (lowest to highest).
   */
  public static List<Client> getClientsByName(List<Client> myClients) {
    quickSortClientName(myClients, 0, myClients.size() - 1);
    return myClients;
  }

  /**
   * Returns a list of itinerary that is sorted by cost.
   * @param myItineraries a list of itineraries to sort
   * @return a list of all itineraries that have the specified departure date,
   *     origin, and destination sorted by travel time (lowest to highest).
   */
  public static List<Itinerary> getItinerariesByTime(List<Itinerary> myItineraries) {
    quickSortTime(myItineraries, 0, myItineraries.size() - 1);
    return myItineraries;
  }

  /**
   * Mutates myList to be sorted by the total cost of each Itinerary.
   * @param myList the list to mutate
   * @param lo the lowest index of this partition
   * @param hi the highest index of this partition
   */
  private static void quickSortFlightCost(List<Flight> myList, int lo, int hi) {
    if (lo < hi) {
      int partition = partitionFlightCost(myList, lo, hi);
      quickSortFlightCost(myList, partition, hi);
      quickSortFlightCost(myList, lo, partition - 1);
    }
  }

  /**
   * A helper function for quickSortCost to find the index for a pivot.
   * @param myList the List to sort
   * @param lo the lowest index of this partition
   * @param hi the highest index of this partition
   * @return the index of the pivot
   */
  private static int partitionFlightCost(List<Flight> myList, int lo, int hi) {
    double pivot = myList.get(lo).getPrice();
    // Use two indices to scan the list
    int i1 = lo;
    int i2 = hi;

    while (true) {
      // Find two indices to swap
      while (i2 > lo && myList.get(i2).getPrice() > pivot) {
        i2--;
      }

      while (i1 < hi && myList.get(i1).getPrice() < pivot) {
        i1++;
      }

      // Swap them, otherwise we're done
      if (i1 <= i2) {
        Flight temp = myList.get(i2);
        myList.set(i2, myList.get(i1));
        myList.set(i1, temp);
        i1++;
        i2--;
      } else {
        return i1;
      }
    }
  }

  /**
   * Mutates myList to be sorted by the total travel time of each Itinerary.
   * @param myList the list to mutate
   * @param lo the lowest index of this partition
   * @param hi the highest index of this partition
   */
  private static void quickSortFlightTime(List<Flight> myList, int lo, int hi) {
    if (lo < hi) {
      int partition = partitionFlightTime(myList, lo, hi);
      quickSortFlightTime(myList, partition, hi);
      quickSortFlightTime(myList, lo, partition - 1);
    }
  }

  /**
   * A helper function for quickSortTime to find the index for a pivot.
   * @param myList the List to sort
   * @param lo the lowest index of this partition
   * @param hi the highest index of this partition
   * @return the index of the pivot
   */
  private static int partitionFlightTime(List<Flight> myList, int lo, int hi) {
    double pivot = myList.get(lo).getTravelTime();
    // Use two indices to scan the list
    int i1 = lo;
    int i2 = hi;

    while (true) {
      // Find two indices to swap
      while (i2 > lo && myList.get(i2).getTravelTime() > pivot) {
        i2--;
      }

      while (i1 < hi && myList.get(i1).getTravelTime() < pivot) {
        i1++;
      }

      // Swap them, otherwise we're done
      if (i1 <= i2) {
        Flight temp = myList.get(i2);
        myList.set(i2, myList.get(i1));
        myList.set(i1, temp);
        i1++;
        i2--;
      } else {
        return i1;
      }
    }
  }

  /**
   * Mutates myList to be sorted by the total cost of each Itinerary.
   * @param myList the list to mutate
   * @param lo the lowest index of this partition
   * @param hi the highest index of this partition
   */
  private static void quickSortCost(List<Itinerary> myList, int lo, int hi) {
    if (lo < hi) {
      int partition = partitionCost(myList, lo, hi);
      quickSortCost(myList, partition, hi);
      quickSortCost(myList, lo, partition - 1);
    }
  }

  /**
   * A helper function for quickSortCost to find the index for a pivot.
   * @param myList the List to sort
   * @param lo the lowest index of this partition
   * @param hi the highest index of this partition
   * @return the index of the pivot
   */
  private static int partitionCost(List<Itinerary> myList, int lo, int hi) {
    double pivot = myList.get(lo).getTotalCost();
    // Use two indices to scan the list
    int i1 = lo;
    int i2 = hi;

    while (true) {
      // Find two indices to swap
      while (i2 > lo && myList.get(i2).getTotalCost() > pivot) {
        i2--;
      }

      while (i1 < hi && myList.get(i1).getTotalCost() < pivot) {
        i1++;
      }

      // Swap them, otherwise we're done
      if (i1 <= i2) {
        Itinerary temp = myList.get(i2);
        myList.set(i2, myList.get(i1));
        myList.set(i1, temp);
        i1++;
        i2--;
      } else {
        return i1;
      }
    }
  }

  /**
   * Mutates myList to be sorted by the total travel time of each Itinerary.
   * @param myList the list to mutate
   * @param lo the lowest index of this partition
   * @param hi the highest index of this partition
   */
  private static void quickSortTime(List<Itinerary> myList, int lo, int hi) {
    if (lo < hi) {
      int partition = partitionTime(myList, lo, hi);
      quickSortTime(myList, partition, hi);
      quickSortTime(myList, lo, partition - 1);
    }
  }

  /**
   * A helper function for quickSortTime to find the index for a pivot.
   * @param myList the List to sort
   * @param lo the lowest index of this partition
   * @param hi the highest index of this partition
   * @return the index of the pivot
   */
  private static int partitionTime(List<Itinerary> myList, int lo, int hi) {
    double pivot = myList.get(lo).getTotalTime();
    // Use two indices to scan the list
    int i1 = lo;
    int i2 = hi;

    while (true) {
      // Find two indices to swap
      while (i2 > lo && myList.get(i2).getTotalTime() > pivot) {
        i2--;
      }

      while (i1 < hi && myList.get(i1).getTotalTime() < pivot) {
        i1++;
      }

      // Swap them, otherwise we're done
      if (i1 <= i2) {
        Itinerary temp = myList.get(i2);
        myList.set(i2, myList.get(i1));
        myList.set(i1, temp);
        i1++;
        i2--;
      } else {
        return i1;
      }
    }
  }

  /**
   * Mutates myList to be sorted by the First Name of each Client.
   * @param myList the list to mutate
   * @param lo the lowest index of this partition
   * @param hi the highest index of this partition
   */
  private static void quickSortClientName(List<Client> myList, int lo, int hi) {
    if (lo < hi) {
      int partition = partitionClientName(myList, lo, hi);
      quickSortClientName(myList, partition, hi);
      quickSortClientName(myList, lo, partition - 1);
    }
  }

  /**
   * A helper function for quickSortClientName to find the index for a pivot.
   * @param myList the List to sort
   * @param lo the lowest index of this partition
   * @param hi the highest index of this partition
   * @return the index of the pivot
   */
  private static int partitionClientName(List<Client> myList, int lo, int hi) {
    String pivot = myList.get(lo).getFullName();
    // Use two indices to scan the list
    int i1 = lo;
    int i2 = hi;

    while (true) {
      // Find two indices to swap
      while (i2 > lo && myList.get(i2).getFullName().compareToIgnoreCase(pivot) > 0) {
        i2--;
      }

      while (i1 < hi && myList.get(i1).getFullName().compareToIgnoreCase(pivot) < 0) {
        i1++;
      }

      // Swap them, otherwise we're done
      if (i1 <= i2) {
        Client temp = myList.get(i2);
        myList.set(i2, myList.get(i1));
        myList.set(i1, temp);
        i1++;
        i2--;
      } else {
        return i1;
      }
    }
  }
}
