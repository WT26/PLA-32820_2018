package alanko.wt.firebase;

import android.os.Bundle;
import android.os.Debug;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DatabaseActivity extends Fragment {

    private TableLayout TLDB;
    private ArrayList<TextView> dbList;
    private StructDataSource datasource;

    private List<Struct> Structures;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_database,
                container, false);

        datasource = new StructDataSource(getContext());
        datasource.open();

        dbList = new ArrayList<TextView>();

        TLDB = (TableLayout) rootView.findViewById(R.id.tableLayoutDB) ;

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
                    updateDBlist(structs);
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


        return rootView;
    }

    // Creates dynamically new row for the table. Rows includes ID, Name, Score and genre.
    private void createNewTableRowView(){
        TableRow row = new TableRow(getActivity());
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);

        TextView tv1 = new TextView(getActivity());
        tv1.setLayoutParams(new TableRow.LayoutParams(1));
        tv1.setPadding(3,0,0,0);
        dbList.add(tv1);

        TextView tv2 = new TextView(getActivity());
        tv2.setLayoutParams(new TableRow.LayoutParams(3));
        tv2.setPadding(3,0,0,0);
        dbList.add(tv2);

        TextView tv3 = new TextView(getActivity());
        tv3.setLayoutParams(new TableRow.LayoutParams(5));
        tv3.setPadding(3,0,0,0);
        dbList.add(tv3);

        TextView tv4 = new TextView(getActivity());
        tv4.setLayoutParams(new TableRow.LayoutParams(7));
        tv4.setPadding(3,0,0,0);
        dbList.add(tv4);

        row.addView(tv1);
        row.addView(tv2);
        row.addView(tv3);
        row.addView(tv4);

        TLDB.addView(row);
    }

    private void updateDBlist(List<Struct> structs){

        dbList.clear();

        // If there are no rows created yet, create one.
        if(structs.size() > dbList.size() / 4){
            createNewTableRowView();
        }

        int i = 0;
        int counter = 0;
        int index = 0;

        // Searches through the Database and writes ID, name, score and genre to appropriate
        // TextViews
        while (i < structs.size()){
            TextView t = dbList.get(index);
            if(counter == 0){
                t.setText(String.valueOf(structs.get(i).getId()));
            }
            else if(counter == 1){
                t.setText(structs.get(i).getName());
            }
            else if(counter == 2){
                t.setText(structs.get(i).getScore());
            }
            else if(counter == 3){
                t.setText(structs.get(i).getGenre());
                i += 1;
                counter = -1;
                if(i < structs.size()){
                    createNewTableRowView();
                }
            }
            counter += 1;
            index += 1;
        };
    }
}
