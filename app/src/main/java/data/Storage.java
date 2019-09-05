package data;

import com.opencsv.CSVWriter;

import user.Admin;
import user.Client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 * reads and writes from client, flight files.
 * Store all info of client, admin, flight, and itineraries 
 * as Maps.
 * @author Leo
 * 
 */
public class Storage implements Serializable {

  private static final long serialVersionUID = 1845314576623938432L;
  // instance variables
  private static Map<String, Flight> flightMap = new HashMap<>(); 
  private static Map<String, Itinerary> itineraryMap = new HashMap<>();
  private static Map<String, Client> clientMap = new HashMap<>();
  private static Map<String, Admin> adminMap = new HashMap<>();
  private static Map<String,String> passMap = new HashMap<>();
  private static final String filePath = "/ObjectInfo.ser";


  /** 
   * Initialize Maps.
   */
  // Constructors
  @SuppressWarnings("ResultOfMethodCallIgnored")
  public Storage(String filepath) {
    File file = new File(filepath + filePath);
    if (!file.exists()) {
      try {
        file.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
      // initialize all maps
      flightMap = new HashMap<>();
      clientMap = new HashMap<>();
      itineraryMap = new HashMap<>();
      adminMap = new HashMap<>();
    } else {
      // initialize all maps
      flightMap = new HashMap<>();
      clientMap = new HashMap<>();
      itineraryMap = new HashMap<>();
      adminMap = new HashMap<>();
      readFromFile(filepath + filePath);
    }

    // add a client and Admin into clientMap JUST FOR INTERVIEW AND DEBUG PURPOSES
    addAdmin(new Admin("Danver", "Carol", "carol@gmail.com", "123 carol Street"));
  }

  /**
   * Read saved objects back to Storage
   * @throws IOException if stream to file cannot be opened or closed.
   */
  // if file exist get file path else create a new file
  @SuppressWarnings("ResultOfMethodCallIgnored")
  public static void readFromFile() throws IOException {
    File file = new File(filePath);
    if (file.exists()) {
      readFromFile(file.getPath());
    } else {
      file.createNewFile();
    }
  }
  
  /**
   * Deserializing
   * @param path file path.
   */
  @SuppressWarnings("unchecked")
  private static void readFromFile(String path) {
    try (
      InputStream file = new FileInputStream(path);
      InputStream buffer = new BufferedInputStream(file);
      ObjectInput input = new ObjectInputStream(buffer)
    ) {
      // Deserialize the Map
      clientMap = (Map<String,Client>)input.readObject();
      flightMap = (Map<String,Flight>)input.readObject();
      adminMap = (Map<String,Admin>)input.readObject();
      itineraryMap = (Map<String,Itinerary>)input.readObject();
      input.close();
    } catch (ClassNotFoundException ex) {
      fLogger.log(Level.SEVERE, "Cannot perform input. Class not found.", ex);
    } catch (IOException ex) {
      fLogger.log(Level.SEVERE, "Cannot perform input.", ex);
    }
  }
  
  
  /**
   * Check if file exists, create one if it does not, else do nothing.
   * @param filepath file location.
   * @throws IOException if stream to file cannot be opened or closed.
   */
  @SuppressWarnings("ResultOfMethodCallIgnored")
  private static void fileExists(String filepath) throws IOException {
    File file = new File(filepath + filePath);
    if (!file.exists()) {
      file.createNewFile();
    }
  }
  
  // Serializing and De-serializing methods.
  /**
   * Serializing
   * @param filepath file location.
   * @throws IOException if stream to file cannot be opened or closed.
   */
  // All maps' objects will be saved to one file
  public static void saveToFile(String filepath) throws IOException {
    fileExists(filepath);
    try (
      
      OutputStream file = new FileOutputStream(filepath + filePath);
      OutputStream buffer = new BufferedOutputStream(file);
      ObjectOutput output = new ObjectOutputStream(buffer)
    ) {
      // more will be added for other Maps
      output.writeObject(clientMap);
      output.writeObject(flightMap);
      output.writeObject(adminMap);
      output.writeObject(itineraryMap);
      output.close();
      
    } catch (IOException ex) {
      fLogger.log(Level.SEVERE, "Cannot perform output.", ex);
    }
  }
  

  private static final Logger fLogger =
             Logger.getLogger(Storage.class.getPackage().getName());

  // add to Map methods:
  /**
   * Add client Obj to clientMap.
   * @param clientObj client class object
   */
  // add client to Map
  public static void addClient(Client clientObj) {
    clientMap.put(clientObj.getEmail(), clientObj);
  }

  
  /**
   * Add admin Obj to adminMap.
   * @param adminObj admin class object
   */
  public static void addAdmin(Admin adminObj) {
    adminMap.put(adminObj.getEmail(), adminObj);
  }
  
  /**
   * Add flight Obj to flightMap.
   * @param flightObj flight class object
   */
  public static void addFlight(Flight flightObj) {
    flightMap.put(flightObj.getFlightNumber(), flightObj);
  }
  
  /**
   * Add itinerary Obj to itineraryMap.
   * @param objItinerary itinerary class object 
   */
  public static void addItinerary(Itinerary objItinerary) {
    if (!itineraryMap.containsValue(objItinerary)) {
      itineraryMap.put(objItinerary.getId(), objItinerary);
    }
  }

  /**
   * add email and password to passMap.
   * @param email String
   * @param password String
   */
  // add client to Map
  public static void addPass(String email, String password) {
    passMap.put(email, password);
  }

  /**
   * Clear itineraryMap.
   */
  public static void clearItinerary() {
    itineraryMap.clear();
  }

  
  
  // return Map methods
  /** 
   * Return the entire Client Map.
   * @return ClientMap
   */
  public static Map<String, Client> getClientMap() {
    return clientMap;
  }
  
  
  /**
   * Return the entire adminMap.
   * @return adminMap map containing admin objects
   */
  public static Map<String, Admin> getAdminMap() {
    return adminMap;
  }
  
  
  /**
   * Return the entire Flight Map.
   * @return FlightMap map containing flight objects
   */
  public static Map<String, Flight> getFlightMap() {
    return flightMap;
  }
 
  /**
  * Return Map of stored Itineraries.
  * @return ItineraryMap map containing itinerary objects
  */

  public static Map<String, Itinerary> getItineraryMap() {
    return itineraryMap;
  }

  /**
   * Return the entire password Map.
   * @return FlightMap map containing flight objects
   */
  public static Map<String, String> getPassMap() {
    return passMap;
  }

  // read and write methods
  /**
   * Read and store client info to map.
   * @param filepath file location.
   * @throws FileNotFoundException if file cannot be found at filepath.
   */
  public static void readClientfile(String filepath) throws FileNotFoundException {
    Scanner sc = new Scanner(new FileInputStream(filepath));
    String [] clientData;
    Client client;
    while (sc.hasNextLine()) {
      clientData = sc.nextLine().split(",");
      // csv file: LastName, FirstName, Address, Email, Card#, Expire Date
      // client object parameter: lastname, firstname, address,Email card, expire date.
      client = new Client(clientData[0], clientData[1], clientData[2], clientData[3], 
                          clientData[4], clientData[5]);
      addClient(client);
    }
    sc.close();
  }

  /**
   * Write new client obj info to client csv file
   * @param filepath file location
   * @throws IOException if stream to file cannot be open or closed.
   */
  public static void writeClientfile(String filepath, Client clientObj) throws IOException {
    // add the new client object to clientMap
    addClient(clientObj);
    // write new client information to client csv
    CSVWriter writer = new CSVWriter(new FileWriter(filepath), CSVWriter.DEFAULT_SEPARATOR, 
                           CSVWriter.NO_QUOTE_CHARACTER);
    for (Entry<String, Client> entry : clientMap.entrySet()) {
      String [] record = entry.getValue().toString().split(",");
      writer.writeNext(record);
    }
    writer.close();

  }
  
  
  /**
   * Read and store flight object into flightMap.
   * @param filepath file location
   * @throws FileNotFoundException if file cannot be found at filepath.
   */
  public static void readFlightFile(String filepath) throws FileNotFoundException {
    Scanner sc = new Scanner(new FileInputStream(filepath));
    String[] flightData;
    Flight flight;
    while (sc.hasNextLine()) {
      flightData = sc.nextLine().split(",");
      // FlightID, DepartureDate/time, ArrivalDate/time, Airline, Origin, Destination, price
      try {

        flight = new Flight(flightData[0], flightData[1], flightData[2], flightData[3], 
                            flightData[4], flightData[5], Double.parseDouble(flightData[6]),
                            Integer.parseInt(flightData[7]));
        addFlight(flight);
      } catch (NumberFormatException | ParseException e) {
        e.printStackTrace();
      }

    }
    sc.close();
  }

  /** 
   * write new flight obj info to flight csv file.
   * @param filepath path to csv flight file
   * @param flight flight object
   * @throws IOException if stream to file cannot be open or closed.
   */
  public static void writeFlightfile(String filepath, Flight flight) throws IOException {
    // add new flight object to flightMap
    addFlight(flight);
    // write new flight info to flight csv
    CSVWriter writer = new CSVWriter(new FileWriter(filepath), CSVWriter.DEFAULT_SEPARATOR, 
                           CSVWriter.NO_QUOTE_CHARACTER);
    for (Entry<String, Flight> entry : flightMap.entrySet()) {
      String [] record = (entry.getValue().toString() + "," 
                          + String.valueOf(entry.getValue().getPrice())).split(",");
      writer.writeNext(record);
    }
    writer.close();
  }

  /**
   * Read and store password info to map.
   * @param filepath file location.
   * @throws FileNotFoundException if file cannot be found at filepath.
   */
  public static void readPassFile(String filepath) throws FileNotFoundException {
    Scanner sc = new Scanner(new FileInputStream(filepath));
    String [] passData;
    while (sc.hasNextLine()) {
      passData = sc.nextLine().split(",");
      // csv file: Email,Password.
      addPass(passData[0], passData[1]);
    }
    sc.close();
  }

  /**
   * Read and store flight object into flightMap if its a new flight, else update
   * existing flight.
   * @param filepath location of the file containing flight information.
   * @throws FileNotFoundException if file cannot be found at filepath.
   */
  public static void adminUploadFlight(String filepath) throws FileNotFoundException {
    Scanner sc = new Scanner(new FileInputStream(filepath));
    String[] flightData;
    Flight flight;
    while (sc.hasNextLine()) {
      flightData = sc.nextLine().split(",");
      // FlightID, DepartureDate/time, ArrivalDate/time, Airline, Origin, Destination, price
      try {

        flight = new Flight(flightData[0], flightData[1], flightData[2], flightData[3],
                flightData[4], flightData[5], Double.parseDouble(flightData[6]),
                Integer.parseInt(flightData[7]));

        // get the FlightMap
        Map<String, Flight> tempFlightMap = getFlightMap();
        // if temFlightMap contains the above flight's id then set that flight to this
        // new flight.
        if (tempFlightMap.containsKey(flight.getFlightNumber())) {
          // get the flight that exists and update the flights info as needed
          // start at index 1 as the flight number(i.e. id) stays same
          // the index matches the constructor of Flight object
          tempFlightMap.get(flight.getFlightNumber()).setDepartureTime(flightData[1]);
          tempFlightMap.get(flight.getFlightNumber()).setArrivalTime(flightData[2]);
          tempFlightMap.get(flight.getFlightNumber()).setAirline(flightData[3]);
          tempFlightMap.get(flight.getFlightNumber()).setOrigin(flightData[4]);
          tempFlightMap.get(flight.getFlightNumber()).setDestination(flightData[5]);
          tempFlightMap.get(flight.getFlightNumber()).setPrice(Double.parseDouble(flightData[6]));
          tempFlightMap.get(flight.getFlightNumber()).setNumSeats(Integer.parseInt(flightData[7]));
        } else { // else since the new flight doesn't exist just add it to the FlightMap
          addFlight(flight);
        }
      } catch (NumberFormatException | ParseException e) {
        e.printStackTrace();
      }

    }
    sc.close();
  }

  /**
   * Read and store client object into clientMap if its a new client, otherwise update
   * the existing client.
   * @param filePath location of the file containing client information.
   * @throws FileNotFoundException if file cannot be found at filepath.
   */
  public static void adminUploadClient(String filePath) throws  FileNotFoundException {
    Scanner sc = new Scanner(new FileInputStream(filePath));
    String[] clientData;
    Client client;
    while (sc.hasNextLine()) {
      clientData = sc.nextLine().split(",");
      // Last Name, First Names, Email, Address, Credit card number, Expiry Date
      client = new Client(clientData[0], clientData[1], clientData[2], clientData[3],
              clientData[4], clientData[5]);

      // Get the ClientMap
      Map<String, Client> tempClientMap = getClientMap();

      // If tempClientMap contains the above client's email, then update the
      // information of this client
      if (tempClientMap.containsKey(client.getEmail())) {
        // Get the client that exists and update the client info as needed
        // Index 2 is skipped over because the email is the key in the client map
        // The index matches the constructor of Flight object
        tempClientMap.get(client.getEmail()).setLastName(clientData[0]);
        tempClientMap.get(client.getEmail()).setFirstName(clientData[1]);
        tempClientMap.get(client.getEmail()).setAddress(clientData[3]);
        tempClientMap.get(client.getEmail()).setCreditCardNumber(clientData[4]);
        tempClientMap.get(client.getEmail()).setExpiryDate(clientData[5]);
      } else { // If there isn't such an email, add the client into the client map
        addClient(client);
      }
    }
    sc.close();
  }

  /**
   * update the old email key mapped to client to new mail and update passMap with the
   * new mail.
   * @param oldEmail String email
   * @param newClient client Object
   */
  public static void updateClientAndPassMap(String oldEmail, Client newClient) {
    // remove client obj mapped to old email
    clientMap.remove(oldEmail);
    // add new client mapped to new email
    addClient(newClient);
    // get pass from passMap
    String tempPass = passMap.get(oldEmail);
    // remove pass mapped to old client email
    passMap.remove(oldEmail);
    // add updated client email + pass to map
    addPass(newClient.getEmail(), tempPass);
  }

  /**
   * Update the old flight in the map with the new flight. It will change the key if the flight
   * @param flightNum the flight number of the flight
   * @param newFlight the new flight to add to the map
   */
  public static void updateFlight(String flightNum, Flight newFlight) {
    // remove old flight mapped to old key
    flightMap.remove(flightNum);
    // add new flight to map
    addFlight(newFlight);
  }

  /**
   * write to password file given filepath.
   * @param filepath file location.
   * @throws IOException FileNotFoundException if file cannot be found at filepath.
   */
  public static void writeToPassFile(String filepath) throws IOException {

    CSVWriter writer = new CSVWriter(new FileWriter(filepath), CSVWriter.DEFAULT_SEPARATOR,
              CSVWriter.NO_QUOTE_CHARACTER);
    for (Entry<String, String> entry : passMap.entrySet()) {
      // take password(key) and email(value) together as a string array.
      String [] record = (entry.getKey() + "," + entry.getValue()).split(",");
      writer.writeNext(record);
    }
    writer.close();


  }
}

