package alanko.wt.tietokanta;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class AddActivity extends Fragment {

    private EditText ET_name;
    private EditText ET_score;
    private EditText ET_genre;
    private Button B_add;

    private AlertDialog AD_warning;
    private AlertDialog AD_success;

    private StructDataSource datasource;


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

        AD_success = new AlertDialog.Builder(rootView.getContext()).create();
        AD_success.setTitle("Notice:");
        AD_success.setMessage("New Item added to database");

        datasource = new StructDataSource(getContext());
        datasource.open();

        return rootView;
    }

    private void addNewStructToDb() {
        String name = ET_name.getText().toString();
        String score = ET_score.getText().toString();
        String genre = ET_genre.getText().toString();

        if (name.matches("") || score.matches("") || genre.matches("")){
            AD_warning.show();
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