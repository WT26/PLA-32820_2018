package alanko.wt.laskukone;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class Logi extends AppCompatActivity {

    private Button Btakaisin;
    private ArrayList<String> log;
    private ListView LV_log;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        Btakaisin = (Button) findViewById(R.id.buttonTakaisin);

        log = new ArrayList<>();
        LV_log = (ListView) findViewById(R.id.listViewLogi);


        loadArray(this);

        Btakaisin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showCalculator();
            }
        });

        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, log);
        LV_log.setAdapter(adapter);
    }

    // Finishes activity and returns.
    private void showCalculator() {
        finish();
    }

    // Loads log array from SharedPreferences
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
}








