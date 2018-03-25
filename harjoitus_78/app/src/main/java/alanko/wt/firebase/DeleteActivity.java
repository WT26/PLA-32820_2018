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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class DeleteActivity extends Fragment {

    private TableLayout TL_Delete;
    private StructDataSource datasource;
    private ArrayList<TextView> dbList;
    private AlertDialog AD_warningSigned;

    private static final int RC_SIGN_IN = 123;

    List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
            new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build(),
            new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_delete,
                container, false);

        TL_Delete = (TableLayout) rootView.findViewById(R.id.tableLayoutDelete);


        AD_warningSigned = new AlertDialog.Builder(rootView.getContext()).create();
        AD_warningSigned.setTitle("Notice:");
        AD_warningSigned.setMessage("You need to be signed in to add item to Firebase.");


        dbList = new ArrayList<TextView>();

        datasource = new StructDataSource(getContext());
        datasource.open();

        final List<Struct> structs = datasource.getAllStructs();
        structs.clear();

        // Firebase db
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference UID = database.getReference("users");
        DatabaseReference gamerankDB = UID.child(user.getUid());

        ValueEventListener structListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI

                if(getActivity() != null){
                    structs.clear();

                    Log.w(" ", String.valueOf(dataSnapshot.hasChildren()));
                    for (DataSnapshot s : dataSnapshot.getChildren()){
                        Struct strukti  = s.getValue(Struct.class);
                        structs.add(strukti);
                    }
                    updateDeleteActivity(structs);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(" ", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        gamerankDB.addValueEventListener(structListener);


        //updateDeleteActivity();

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

    // Initialize new row for the table. Row consists of ID, NAME and delete button.
    private void createNewTableRowView(int index){

        TableRow row = new TableRow(getContext());
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);

        TextView tv1 = new TextView(getContext());
        tv1.setLayoutParams(new TableRow.LayoutParams(1));
        tv1.setPadding(20,0,20,0);
        dbList.add(tv1);

        TextView tv2 = new TextView(getContext());
        tv2.setLayoutParams(new TableRow.LayoutParams(2));
        tv2.setPadding(20,0,20,0);
        dbList.add(tv2);

        Button b1 = new Button(getContext());
        b1.setLayoutParams(new TableRow.LayoutParams(3));
        b1.setText("Delete");
        b1.setId(index);
        final int id_ = b1.getId();

        row.addView(tv1);
        row.addView(tv2);
        row.addView(b1);

        Button btn1 = ((Button) row.findViewById(id_));
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                deleteDatabaseStruct(id_);
            }
        });

        TL_Delete.addView(row);
    }

    private void deleteDatabaseStruct (int index) {
        datasource.deleteStruct(index);
    }

    // Updates the table at initialization and when a struct is deleted.
    private void updateDeleteActivity(List<Struct> structs) {
        dbList = new ArrayList<TextView>();
        TL_Delete.removeAllViews();

        dbList.clear();


        int i = 0;
        int counter = 0;
        int index = 0;

        if (structs.size() != 0) {
            createNewTableRowView((int) structs.get(i).getId());
        }
        while (i < structs.size()){
            TextView t = dbList.get(index);

            if(counter == 0){
                t.setText(String.valueOf(structs.get(i).getId()));
            }
            else if(counter == 1){
                t.setText(structs.get(i).getName());
                i += 1;
                counter = -1;
                if(i < structs.size()){
                    createNewTableRowView((int) structs.get(i).getId());
                }
            }
            counter += 1;
            index += 1;
        };
    }
}