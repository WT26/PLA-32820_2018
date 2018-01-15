package alanko.wt.firebase;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DeleteActivity extends Fragment {

    private TableLayout TL_Delete;
    private StructDataSource datasource;
    private ArrayList<TextView> dbList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_delete,
                container, false);

        TL_Delete = (TableLayout) rootView.findViewById(R.id.tableLayoutDelete);
        dbList = new ArrayList<TextView>();

        datasource = new StructDataSource(getContext());
        datasource.open();

        updateDeleteActivity();

        return rootView;
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
        List<Struct> structs = datasource.getAllStructs();
        datasource.deleteStruct(structs.get(index));
        updateDeleteActivity();
    }

    // Updates the table at initialization and when a struct is deleted.
    private void updateDeleteActivity() {
        List<Struct> structs = datasource.getAllStructs();
        dbList = new ArrayList<TextView>();
        TL_Delete.removeAllViews();

        int i = 0;
        int counter = 0;
        int index = 0;

        if (structs.size() != 0) {
            createNewTableRowView(i);
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
                    createNewTableRowView(i);
                }
            }
            counter += 1;
            index += 1;
        };
    }
}