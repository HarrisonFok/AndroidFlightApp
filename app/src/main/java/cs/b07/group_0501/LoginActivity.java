package cs.b07.group_0501;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import app.Application;
import data.Storage;
import user.Admin;

import java.io.FileNotFoundException;

/**
 * A login screen which will initialize the flight booking app.
 * Administrators and Clients can login from this activity.
 *
 * @author Terrence Hung
 */
public class LoginActivity extends AppCompatActivity {

  private Application application;
  @SuppressWarnings("FieldCanBeLocal")
  private Storage storage;

  /**
   * Creates a LoginActivity, and initializes the application.
   *
   * @param savedInstanceState The saved instance state.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    application = new Application();
    storage = new Storage(this.getApplicationContext().getDir(Constants.PASSWORD_FILE_PATH,
            MODE_PRIVATE).getPath());
    // give the password file to storage
    try {
      Storage.readPassFile(this.getApplicationContext().getDir(Constants.PASSWORD_FILE_PATH,
              MODE_PRIVATE).getPath() + "/passwords.txt");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * Verifies the login credentials and moves to the corresponding activity, either
   * AdminMainActivity or ClientMainActivity.
   *
   * @param view The view that calls the login.
   */
  @SuppressWarnings("ConstantConditions")
  public void login(View view) {
    // close the keyboard
    InputMethodManager inputManager = (InputMethodManager)getSystemService(
            Context.INPUT_METHOD_SERVICE);
    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
            InputMethodManager.HIDE_NOT_ALWAYS);
    // get the info from both edittexts
    String email = ((EditText)findViewById(R.id.email)).getText().toString();
    String password = ((EditText)findViewById(R.id.password)).getText().toString();
    // try to login and see the result
    int success = application.login(email.trim(), password.trim());
    // go to the appropriate activity depending on what the success is
    if (success == Constants.LOGIN_NULL) {
      // create a toast saying invalid login
      Context context = getApplicationContext();
      // get invalid login message from strings.xml
      CharSequence text = getString(R.string.invalid_login);
      int duration = Toast.LENGTH_SHORT;
      Toast toast = Toast.makeText(context, text, duration);
      toast.show();
    } else if (success == Constants.LOGIN_CLIENT) {
      Intent intent = new Intent(this, ClientMainActivity.class);
      //Client client = Storage.getClientMap().get(email);
      intent.putExtra(Constants.EMAIL_KEY, email);
      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK );
      // client can log in so go to client main activity
      startActivity(intent);
    } else {
      // admin can log in so go to admin main activity
      Intent intent = new Intent(this, AdminMainActivity.class);
      Admin admin = Storage.getAdminMap().get(email);
      intent.putExtra(Constants.ADMIN_KEY, admin);
      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
      startActivity(intent);
    }
  }
}
