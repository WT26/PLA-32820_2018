package alanko.wt.firebase;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.Arrays;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class AddActivity extends Fragment {

    private EditText ET_name;
    private EditText ET_score;
    private EditText ET_genre;
    private Button B_add;

    private AlertDialog AD_warning;
    private AlertDialog AD_warningSigned;
    private AlertDialog AD_success;

    private StructDataSource datasource;


    private static final int RC_SIGN_IN = 123;

    List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
            new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build(),
            new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build());


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_add,
                container, false);

        ET_name = (EditText) rootView.findViewById(R.id.editTextName);
        ET_score = (EditText) rootView.findViewById(R.id.editTextScore);
        ET_genre = (EditText) rootView.findViewById(R.id.editTextGenre);
        B_add = (Button) rootView.findViewById(R.id.buttonAdd);

        B_add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addNewStructToDb();
            }
        });

        AD_warning = new AlertDialog.Builder(rootView.getContext()).create();
        AD_warning.setTitle("Notice:");
        AD_warning.setMessage("One or more of the fields are empty.");

        AD_warningSigned = new AlertDialog.Builder(rootView.getContext()).create();
        AD_warningSigned.setTitle("Notice:");
        AD_warningSigned.setMessage("You need to be signed in to add item to Firebase.");

        AD_success = new AlertDialog.Builder(rootView.getContext()).create();
        AD_success.setTitle("Notice:");
        AD_success.setMessage("New Item added to database");

        datasource = new StructDataSource(getContext());
        datasource.open();


        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            } else {
                // Sign in failed, check response for error code
                Log.d("DEBUG", "Sign In failed");
                AD_warningSigned.show();
            }
        }
    }

    private void addNewStructToDb() {
        String name = ET_name.getText().toString();
        String score = ET_score.getText().toString();
        String genre = ET_genre.getText().toString();

        if (name.matches("") || score.matches("") || genre.matches("")){
            AD_warning.show();
        }
        else if(FirebaseAuth.getInstance().getCurrentUser() == null){
            // Create and launch sign-in intent
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(),
                    RC_SIGN_IN);
        }
        else {
            datasource.createStruct(name, score, genre);
            AD_success.show();
            ET_name.setText("");
            ET_score.setText("");
            ET_genre.setText("");
        }
    }
}