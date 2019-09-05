package cs.b07.group_0501;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import data.Storage;
import user.Admin;
import user.Client;


import java.io.IOException;





/**
 * Activity that gives the client and the admin the ability to edit their
 * personal info.
 * @author Ralph
 */
public class EditClientInfoActivity extends AppCompatActivity {
  private Client thisClient;


  /**
   * Creates a new EditClientInfoActivity and
   * sets the EditText's hints to be the previous known info
   * of the current user.
   * @param savedInstanceState The saved Instance state
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_client_info);

    // get the Intent that launched me
    Intent intent = getIntent();
    // get the client in the intent
    thisClient = (Client) intent.getSerializableExtra(Constants.CLIENT_KEY);
    // set each attribute as a hint for the respective input fields
    // set Last Name
    EditText oldLastName = (EditText) findViewById(R.id.newLastName);
    oldLastName.setHint(thisClient.getLastName());
    // set First Name
    EditText oldFirstName = (EditText) findViewById(R.id.newFirstName);
    oldFirstName.setHint(thisClient.getFirstName());
    // set Email
    EditText oldEmail = (EditText) findViewById(R.id.newEmail);
    oldEmail.setHint(thisClient.getEmail());
    // set Address
    EditText oldAddress = (EditText) findViewById(R.id.newAddress);
    oldAddress.setHint(thisClient.getAddress());
    // set Credit Card Number
    EditText oldCreditCardNumber = (EditText) findViewById(R.id.newCreditCardNumber);
    oldCreditCardNumber.setHint(thisClient.getCreditCardNumber());
    // set Expiry Date
    EditText oldExpiryDate = (EditText) findViewById(R.id.newExpiryDate);
    oldExpiryDate.setHint(thisClient.getExpiryDate());
  }

  /**
   * given the input from the edit texts updates the personal
   * and billing info of the client.
   * @param view the view that calls this method
   */
  public void editSelfInfo(View view) {

    String email = thisClient.getEmail();
    // get the edit texts needed
    EditText oldLastName = (EditText) findViewById(R.id.newLastName);
    EditText oldFirstName = (EditText) findViewById(R.id.newFirstName);
    EditText oldEmail = (EditText) findViewById(R.id.newEmail);
    EditText oldAddress = (EditText) findViewById(R.id.newAddress);
    EditText oldCreditCardNumber = (EditText) findViewById(R.id.newCreditCardNumber);
    EditText oldExpiryDate = (EditText) findViewById(R.id.newExpiryDate);

    // get the info from all edittexts
    String newLastName = oldLastName.getText().toString();
    String newFirstName = oldFirstName.getText().toString();
    String newEmail = oldEmail.getText().toString();
    String newAddress = oldAddress.getText().toString();
    String newCreditCardNumber = oldCreditCardNumber.getText().toString();
    String newExpiryDate = oldExpiryDate.getText().toString();
    String[] newInfo = {
      newLastName,newFirstName,newEmail,newAddress,newCreditCardNumber,newExpiryDate} ;
    String[] newInfoButExpiry = {newLastName,newFirstName,newEmail,newAddress,newCreditCardNumber} ;
    // find a boolean to represent when the Edit Texts are empty
    Boolean noChange = true;
    for (String info: newInfo) {
      noChange = noChange && info.isEmpty();
    }
    // find a boolean to represent when the Edit Texts are empty except Expiry date
    Boolean noChangeButExpiry = true;
    for (String info: newInfoButExpiry) {
      noChangeButExpiry = noChangeButExpiry && info.isEmpty();
    }

    if (!(newLastName.isEmpty())) {
      thisClient.setLastName(newLastName.trim());
      //update Hints
      oldLastName.setHint(thisClient.getLastName());
    }
    // set the new First Name
    if (!(newFirstName.isEmpty())) {
      thisClient.setFirstName(newFirstName.trim());
      //update Hints
      oldFirstName.setHint(thisClient.getFirstName());
    }
    // set the new Email
    if (!(newEmail.isEmpty())) {
      thisClient.setEmail(newEmail.trim());
      //update Hints
      oldEmail.setHint(thisClient.getEmail());
      //update passwords.txt
      try {
        Storage.writeToPassFile(this.getApplicationContext().getDir(Constants.PASSWORD_FILE_PATH,
                MODE_PRIVATE).getPath());
      } catch (IOException e) {
        Toast.makeText(this, getString(R.string.cant_output_file), Toast.LENGTH_SHORT).show();
      }
    }
    // set the new Address
    if (!(newAddress.isEmpty())) {
      thisClient.setAddress(newAddress.trim());
      //update Hints
      oldAddress.setHint(thisClient.getAddress());
    }
    // set the new Credit Card Number
    if (!(newCreditCardNumber.isEmpty())) {
      thisClient.setCreditCardNumber(newCreditCardNumber.trim());
      //update Hints
      oldCreditCardNumber.setHint(thisClient.getCreditCardNumber());
    }

    int error = 0;
    try {
      if (!(newExpiryDate.isEmpty())) {
        // check if the hyphens are found in between the year-month-day
        // get the substring where the hyphens should be
        String firstHyphen = newExpiryDate.substring(4, 5);
        String secondHyphen = newExpiryDate.substring(7, 8);
        // check if they are indeed hyphens
        Boolean hyphenated = firstHyphen.equals("-") && secondHyphen.equals("-");
        // Check if the substring that denotes month is a value
        // in between 1 and 12
        Integer monthInt = Integer.parseInt(newExpiryDate.substring(5, 7));
        boolean monthCount = (monthInt >= 1) && (monthInt <= 12);
        // Check if the substring that denotes day is a value
        // in between 1 and 31
        Integer dayInt = Integer.parseInt(newExpiryDate.substring(8, newExpiryDate.length()));
        boolean dayCount = (dayInt >= 1) && (dayInt <= 31);
        // if all conditions are true set the new Expiry Data
        if (!(newExpiryDate.isEmpty()) && hyphenated && monthCount && dayCount) {
          thisClient.setExpiryDate(newExpiryDate.trim());
          //update Hints
          oldExpiryDate.setHint(thisClient.getExpiryDate());
          // if error == 1 then the change to expiry date was made
          error = 1;
        } else {
          Toast.makeText(this,R.string.invalid_expiry_date, Toast.LENGTH_SHORT).show();
          // if error == 2 then the change to expiry date was not made
          error = 2;
        }
      }
    } catch (NumberFormatException e) {
      e.printStackTrace();
      Toast.makeText(this,R.string.invalid_expiry_date, Toast.LENGTH_SHORT).show();
    }
    // display a toast saying that the info has been updated
    if (!(noChange) && ((error == 1) || (error == 0)) || !(noChangeButExpiry)) {
      Toast.makeText(this,R.string.client_info_changed, Toast.LENGTH_SHORT).show();
    }

    // Save and update the info to file
    Storage.updateClientAndPassMap(email, thisClient);
    try {
      Storage.saveToFile(this.getApplicationContext().getDir(Constants.PASSWORD_FILE_PATH,
              MODE_PRIVATE).getPath());
    } catch (IOException e) {
      Toast.makeText(this, getString(R.string.cant_output_file), Toast.LENGTH_SHORT).show();
    }
  }
}
