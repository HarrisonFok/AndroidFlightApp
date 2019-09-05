package user;

import data.Itinerary;
import user.BillingInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A representation of a client in an airport.
 * @author Pak Sun Fok
 */
public class Client implements Serializable {

  private static final long serialVersionUID = -8150827434658077871L;
  private List<Itinerary> bookedItineraries;
  private PersonalInfo personalInfo;
  private BillingInfo billingInfo;

  /**
   * Constructor of a client.
   * @param lastName - the client's last name
   * @param firstName - the client's first name
   * @param address - the client's address
   * @param email - email of the client
   * @param creditCardNumber - the client's credit card number
   * @param expiryDate - the date when the credit card expires
   */
  public Client(String lastName, String firstName, String email, String address,
      String creditCardNumber, String expiryDate) {
    this.personalInfo = new PersonalInfo(lastName, firstName, email, address);
    this.billingInfo = new BillingInfo(creditCardNumber, expiryDate);
    bookedItineraries = new ArrayList<>();
  }

  /**
   * Returns the string representation of a Client.
   */
  @Override
  public String toString() {
    return (personalInfo.toString() + "," + billingInfo.toString());
  }

  /**
   * Returns the personal information of the Client.
   * @return personalInfo - the PersonalInfo object of the Client.
   */
  public PersonalInfo getPersonalInfo() {
    return personalInfo;
  }

  /**
   * Sets the personal info of the Client.
   * @param personalInfo - a new PersonalInfo object containing every needed
   *                       personal information.
   */
  public void setPersonalInfo(PersonalInfo personalInfo) {
    this.personalInfo = personalInfo;
  }

  /**
   * Returns the billing information of the Client.
   * @return billingInfo - the billingInfo of the Client.
   */
  public BillingInfo getBillingInfo() {
    return billingInfo;
  }

  /**
   * Sets the billing information of the client.
   * @param billingInfo - a new BillingInfo object containing every needed
   *                      billing information.
   */
  public void setBillingInfo(BillingInfo billingInfo) {
    this.billingInfo = billingInfo;
  }

  /**
   * Keeps track of the client's booked itineraries.
   * @param newItin - the itinerary that the client wants to book.
   */
  public void addBooked(Itinerary newItin) {
    bookedItineraries.add(newItin);
  }

  /**
   * Gets the email of the Client.
   * @return email - the email of the Client.
   */
  public String getEmail() {
    return personalInfo.getEmail();
  }

  /**
   * Sets the email of the Client.
   * @param email - the new email that the user wants to set to.
   */
  public void setEmail(String email) {
    this.personalInfo.setEmail(email);;
  }

  /**
   * Gets the first name of the Client.
   */
  public String getFirstName() {
    return personalInfo.getFirstName();
  }

  /**
   * Sets the first name of the Client.
   * @param firstName - the new first name that the user wants to set to.
   */
  public void setFirstName(String firstName) {
    this.personalInfo.setFirstName(firstName);;
  }

  /**
   * Gets the last name of the Client.
   * @return lastName - the last name of the Client.
   */
  public String getLastName() {
    return personalInfo.getLastName();
  }

  /**
   * Sets the last name of the Client.
   * @param lastName - the new last name that the user wants to set to.
   */
  public void setLastName(String lastName) {
    this.personalInfo.setLastName(lastName);;
  }

  /**
   * Returns the full name of the Client.
   */
  public String getFullName() {
    return this.getFirstName() + " " + this.getLastName();
  }

  /**
   * Gets the address of the Client.
   * @return address - the address of the Client.
   */
  public String getAddress() {
    return personalInfo.getAddress();
  }

  /**
   * Sets the address of the Client.
   * @param address - the new address that the user wants to set to.
   */
  public void setAddress(String address) {
    this.personalInfo.setAddress(address);
  }

  /**
   * Gets the credit card number of the Client.
   * @return creditCardNumber - the credit card number of the Client.
   */
  public String getCreditCardNumber() {
    return billingInfo.getCreditCardNumber();
  }

  /**
   * Sets the credit card number.
   * @param creditCardNumber - the new credit card number that the user wants to set to.
   */
  public void setCreditCardNumber(String creditCardNumber) {
    this.billingInfo.setCreditCardNumber(creditCardNumber);
  }

  /**
   * Gets the date on which the Client's credit card expires.
   * @return expiryDate - the date on which the Client's credit card expires.
   */
  public String getExpiryDate() {
    return billingInfo.getExpiryDate();
  }

  /**
   * Sets the date on which the Client's credit card expires.
   * @param expiryDate - the new date on which the Client's credit card expires.
   */
  public void setExpiryDate(String expiryDate) {
    this.billingInfo.setExpiryDate(expiryDate);
  }

  /**
   * Returns the list of itineraries booked by the client.
   * @return the list of booked itineraries
   */
  public List<Itinerary> getBookedItineraries() {
    return this.bookedItineraries;
  }

  /**
   * Sets the list of booked itineraries of the Client.
   * @param bookedItineraries - a List of booked itineraries
   */
  public void setBookedItineraries(List<Itinerary> bookedItineraries) {
    this.bookedItineraries = bookedItineraries;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((billingInfo == null) ? 0 : billingInfo.hashCode());
    result = prime * result + ((bookedItineraries == null) ? 0 : bookedItineraries.hashCode());
    result = prime * result + ((personalInfo == null) ? 0 : personalInfo.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Client other = (Client) obj;
    if (billingInfo == null) {
      if (other.billingInfo != null) {
        return false;
      }
    } else if (!billingInfo.equals(other.billingInfo)) {
      return false;
    }
    if (bookedItineraries == null) {
      if (other.bookedItineraries != null) {
        return false;
      }
    } else if (!bookedItineraries.equals(other.bookedItineraries)) {
      return false;
    }
    if (personalInfo == null) {
      if (other.personalInfo != null) {
        return false;
      }
    } else if (!personalInfo.equals(other.personalInfo)) {
      return false;
    }
    return true;
  }
}
