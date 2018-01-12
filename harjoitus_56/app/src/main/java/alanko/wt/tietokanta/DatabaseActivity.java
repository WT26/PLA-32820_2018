package alanko.wt.tietokanta;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    private StructDataSource datasource;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_database,
                container, false);

        datasource = new StructDataSource(getContext());
        datasource.open();

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

        Struct struct = null;
        struct = datasource.createStruct("Bloodborne", "10", "Action RPG");
        struct = datasource.createStruct("Persona 5", "10", "JRPG");
        struct = datasource.createStruct("Zelda: BotW", "10", "Puzzle/Adventure");
        struct = datasource.createStruct("Super Mario Odyssey", "10", "3D Platformer");

        List<Struct> structs = datasource.getAllStructs();

        db11.setText(String.valueOf(structs.get(0).getId()));
        db12.setText(structs.get(0).getName());
        db13.setText(structs.get(0).getScore());
        db13.setText(structs.get(0).getGenre());

        return rootView;
    }
}
