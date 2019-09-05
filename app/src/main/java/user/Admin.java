package user;

import user.PersonalInfo;

import java.io.Serializable;

/**
 * A representation of an administrator in an airport.
 * @author Pak Sun Fok
 */
public class Admin implements Serializable {

  private static final long serialVersionUID = 845968711967787886L;
  private PersonalInfo personalInfo;

  /**
   * Constructor of personal info.
   * @param lastName - the person's last name
   * @param firstName - the person's first name
   * @param email - the person's email
   * @param address - the person's address
   */
  public Admin(String lastName, String firstName, String email, String address) {
    this.personalInfo = new PersonalInfo(lastName, firstName, email, address);
  }

  /**
   * Returns the personal information of the administrator.
   * @return personalInfo - the PersonalInfo object of the administrator.
   */
  public PersonalInfo getPersonalInfo() {
    return personalInfo;
  }

  /**
   * Sets the personal information of the administrator.
   * @param newPersonalInfo - the PersonalInfo object for the administrator.
   */
  public void setPersonalInfo(PersonalInfo newPersonalInfo) {
    this.personalInfo = newPersonalInfo;
  }

  /**
   * Returns a string representation of personal info.
   */
  @Override
  public String toString() {
    return personalInfo.toString();
  }

  /**
   * Gets the last name of the administrator.
   * @return lastName - the last name of the administrator.
   */
  public String getLastName() {
    return personalInfo.getLastName();
  }

  /**
   * Sets the last name of the administrator.
   * @param lastName - the new last name of the administrator.
   */
  public void setLastName(String lastName) {
    this.personalInfo.setLastName(lastName);
  }

  /**
   * Gets the first name of the administrator.
   * @return firstName - the first name of the administrator.
   */
  public String getFirstName() {
    return personalInfo.getFirstName();
  }

  /**
   * Sets the first name of the administrator.
   * @param firstName - the new first name of the administrator.
   */
  public void setFirstName(String firstName) {
    this.personalInfo.setFirstName(firstName);
  }

  /**
   * Gets the email of the administrator.
   * @return email - the email of the administrator.
   */
  public String getEmail() {
    return personalInfo.getEmail();
  }

  /**
   * Sets the email of the administrator.
   * @param email - the new email of the administrator.
   */
  public void setEmail(String email) {
    this.personalInfo.setEmail(email);
  }

  /**
   * Gets the address of the administrator.
   * @return address - the address of the administrator.
   */
  public String getAddress() {
    return personalInfo.getAddress();
  }

  /**
   * Sets the address of the administrator.
   * @param address - the new address of the administrator.
   */
  public void setAddress(String address) {
    this.personalInfo.setAddress(address);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
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
    Admin other = (Admin) obj;
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
