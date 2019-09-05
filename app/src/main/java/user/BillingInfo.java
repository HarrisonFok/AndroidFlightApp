package user;

import java.io.Serializable;

/**
 * The billing info that corresponds to a user's booking in the Application.
 *
 * @author Ralph Samson Lal
 */
public class BillingInfo implements Serializable {

  private static final long serialVersionUID = -4058875247287438337L;
  private String creditCardNumber;
  private String expiryDate;

  /**
   * @param creditCardNumber the credit card number the user gives when booking.
   * @param expiryDate the expire date of the given credit card.
   */
  public BillingInfo(String creditCardNumber, String expiryDate) {
    this.creditCardNumber = creditCardNumber;
    // Expire date of format YYYY-MM-DD given by user
    this.expiryDate = expiryDate;
  }

  /**
   * @return The credit card number of this user.
   */
  public String getCreditCardNumber() {
    return creditCardNumber;
  }

  /**
   * @param creditCardNumber the creditCardNumber to set.
   */
  public void setCreditCardNumber(String creditCardNumber) {
    this.creditCardNumber = creditCardNumber;
  }

  /**
   * @return The date the credit card of this user expires.
   */
  public String getExpiryDate() {
    return expiryDate;
  }

  /**
   * @param expiryDate the expiryDate to set.
   */
  public void setExpiryDate(String expiryDate) {
    this.expiryDate = expiryDate;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return creditCardNumber + "," + expiryDate;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((creditCardNumber == null) ? 0 : creditCardNumber.hashCode());
    result = prime * result + ((expiryDate == null) ? 0 : expiryDate.hashCode());
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
    BillingInfo other = (BillingInfo) obj;
    if (creditCardNumber == null) {
      if (other.creditCardNumber != null) {
        return false;
      }
    } else if (!creditCardNumber.equals(other.creditCardNumber)) {
      return false;
    }
    if (expiryDate == null) {
      if (other.expiryDate != null) {
        return false;
      }
    } else if (!expiryDate.equals(other.expiryDate)) {
      return false;
    }
    return true;
  }

}
