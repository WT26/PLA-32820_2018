package alanko.wt.laskukone;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Set;

public class Logi extends AppCompatActivity {

    private Button Btakaisin;
    private ArrayList<String> log;
    private ListView LV_log;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        Btakaisin = (Button) findViewById(R.id.buttonTakaisin);

        log = new ArrayList<>();
        LV_log = (ListView) findViewById(R.id.listViewLogi);

        //SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        //SharedPreferences.Editor editor = pref.edit();

        loadArray(this);
        fillListView();

        Btakaisin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showCalculator();
            }
        });

        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, log);
        LV_log.setAdapter(adapter);
    }

    private void showCalculator() {
        finish();
    }

    public void loadArray(Context mContext)
    {
        SharedPreferences mSharedPreference1 =   PreferenceManager.getDefaultSharedPreferences(mContext);
        log.clear();
        int size = mSharedPreference1.getInt("Status_size", 0);

        for(int i=0;i<size;i++)
        {
            log.add(mSharedPreference1.getString("Status_" + i, null));
        }
    }

    private void fillListView() {
        //adapter.notifyDataSetChanged();
/*
        for(int i=0;i<log.size();i++)
        {
        }*/
    }
}








