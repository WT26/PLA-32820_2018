package alanko.wt.tietokanta;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DatabaseActivity extends Fragment {
    private TextView db11;
    private TextView db12;
    private TextView db13;
    private TextView db14;

    private TextView db21;
    private TextView db22;
    private TextView db23;
    private TextView db24;

    private TextView db31;
    private TextView db32;
    private TextView db33;
    private TextView db34;

    private TableLayout TLDB;

    private ArrayList<TextView> dbList;

    private StructDataSource datasource;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_database,
                container, false);

        datasource = new StructDataSource(getContext());
        datasource.open();
/*
        db11 = (TextView) rootView.findViewById(R.id.db11);
        db12 = (TextView) rootView.findViewById(R.id.db12);
        db13 = (TextView) rootView.findViewById(R.id.db13);
        db14 = (TextView) rootView.findViewById(R.id.db14);

        db21 = (TextView) rootView.findViewById(R.id.db21);
        db22 = (TextView) rootView.findViewById(R.id.db22);
        db23 = (TextView) rootView.findViewById(R.id.db23);
        db24 = (TextView) rootView.findViewById(R.id.db24);

        db31 = (TextView) rootView.findViewById(R.id.db31);
        db32 = (TextView) rootView.findViewById(R.id.db32);
        db33 = (TextView) rootView.findViewById(R.id.db33);
        db34 = (TextView) rootView.findViewById(R.id.db34);
*/
        dbList = new ArrayList<TextView>();
        /*
        dbList.add(db11);
        dbList.add(db12);
        dbList.add(db13);
        dbList.add(db14);
        dbList.add(db21);
        dbList.add(db22);
        dbList.add(db23);
        dbList.add(db24);
        dbList.add(db31);
        dbList.add(db32);
        dbList.add(db33);
        dbList.add(db34);
*/
        TLDB = (TableLayout) rootView.findViewById(R.id.tableLayoutDB) ;

        Struct struct = null;
  /*
        datasource.createStruct("Bloodborne", "10", "Action RPG");
        datasource.createStruct("Persona 5", "10", "JRPG");
        datasource.createStruct("Zelda: BotW", "10", "Puzzle/Adventure");
        datasource.createStruct("Super Mario Odyssey", "10", "3D Platformer");
*/
        List<Struct> structs = datasource.getAllStructs();

        if(structs.size() > dbList.size() / 4){
            createNewTableRowView();
        }

        int i = 0;
        int counter = 0;
        int index = 0;

        while (i < structs.size()){
            TextView t = dbList.get(index);
            Log.d("DEBUG", String.valueOf(dbList.size()));
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

        for(TextView t: dbList){
        }
        return rootView;
    }

    private void createNewTableRowView(){

        TableRow row = new TableRow(getContext());
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);

        TextView tv1 = new TextView(getContext());
        tv1.setLayoutParams(new TableRow.LayoutParams(1));
        tv1.setPadding(3,0,0,0);
        dbList.add(tv1);
/*
        View v1 = new View(getContext());
        v1.setBackgroundColor(Color.parseColor("#FF909090"));
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(100, ViewGroup.LayoutParams.MATCH_PARENT);
        v1.setLayoutParams(params);
*/
        TextView tv2 = new TextView(getContext());
        tv2.setLayoutParams(new TableRow.LayoutParams(3));
        tv2.setPadding(3,0,0,0);
        dbList.add(tv2);
/*
        View v2 = new View(getContext());
        v2.setBackgroundColor(Color.parseColor("#FF909090"));
        v2.setLayoutParams(params);
*/
        TextView tv3 = new TextView(getContext());
        tv3.setLayoutParams(new TableRow.LayoutParams(5));
        tv3.setPadding(3,0,0,0);
        dbList.add(tv3);
/*
        View v3 = new View(getContext());
        v3.setBackgroundColor(Color.parseColor("#FF909090"));
        v3.setLayoutParams(params);

        */
        TextView tv4 = new TextView(getContext());
        tv4.setLayoutParams(new TableRow.LayoutParams(7));
        tv4.setPadding(3,0,0,0);
        dbList.add(tv4);


        row.addView(tv1);
        //row.addView(v1);
        row.addView(tv2);
        //row.addView(v2);
        row.addView(tv3);
        //row.addView(v3);
        row.addView(tv4);

        //tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        TLDB.addView(row);
    }
}
