package user;

import java.io.Serializable;

/**
 * Personal info, a class to hold personal information about a user such as name, address, and
 * email.
 *
 * @author Ralph Samson Lal.
 */
public class PersonalInfo implements Serializable {

  private static final long serialVersionUID = -2608064016875923517L;
  private String lastName;
  private String firstName;
  private String email;
  private String address;

  /**
   * @param lastName the Last Name of the user.
   * @param firstName the First Name of the user.
   * @param email the Email of the user.
   * @param address the Address of the user.
   */
  public PersonalInfo(String lastName, String firstName, String email, String address) {
    this.lastName = lastName;
    this.firstName = firstName;
    this.email = email;
    this.address = address;
  }

  /**
   * @return This user's first name.
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * @param firstName the firstName to set.
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * @return This user's last name.
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * @param lastName the lastName to set.
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * @return This user's address.
   */
  public String getAddress() {
    return address;
  }

  /**
   * @param address the address to set.
   */
  public void setAddress(String address) {
    this.address = address;
  }

  /**
   * @return This user's email address.
   */
  public String getEmail() {
    return email;
  }

  /**
   * @param email the email to set.
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return lastName + "," + firstName + "," + email + "," + address;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((address == null) ? 0 : address.hashCode());
    result = prime * result + ((email == null) ? 0 : email.hashCode());
    result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
    result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
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
    PersonalInfo other = (PersonalInfo) obj;
    if (address == null) {
      if (other.address != null) {
        return false;
      }
    } else if (!address.equals(other.address)) {
      return false;
    }
    if (email == null) {
      if (other.email != null) {
        return false;
      }
    } else if (!email.equals(other.email)) {
      return false;
    }
    if (firstName == null) {
      if (other.firstName != null) {
        return false;
      }
    } else if (!firstName.equals(other.firstName)) {
      return false;
    }
    if (lastName == null) {
      if (other.lastName != null) {
        return false;
      }
    } else if (!lastName.equals(other.lastName)) {
      return false;
    }
    return true;
  }

}
