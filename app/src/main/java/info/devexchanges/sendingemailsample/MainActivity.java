package info.devexchanges.sendingemailsample;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText txtTo;
    private EditText txtSubject;
    private EditText txtContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtContent = (EditText) findViewById(R.id.content);
        txtSubject = (EditText) findViewById(R.id.subject);
        txtTo = (EditText) findViewById(R.id.email);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.send) {
            sendingEmail();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String getText(EditText textView) {
        return textView.getText().toString().trim();
    }

    private void sendingEmail() {
        if (!checkEmailAddress(getText(txtTo))) {
            Toast.makeText(this, "Receiver Email is invalid!", Toast.LENGTH_SHORT).show();
        } else if (getText(txtSubject).equals("")) {
            Toast.makeText(this, "Please input Email Subject!", Toast.LENGTH_SHORT).show();
        } else {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);

            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.setType("text/plain");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{getText(txtTo)});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, getText(txtSubject));
            emailIntent.putExtra(Intent.EXTRA_TEXT, getText(txtContent));

            try {
                startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean checkEmailAddress(String emailAddress) {

        String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Boolean b = emailAddress.matches(EMAIL_REGEX);

        return b;
    }
}