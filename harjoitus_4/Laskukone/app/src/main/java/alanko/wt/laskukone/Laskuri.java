package alanko.wt.laskukone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;

public class Laskuri extends AppCompatActivity {


    private EditText ETplus1;
    private EditText ETplus2;
    private Button Bplus;
    private TextView Tplus;

    private EditText ETminus1;
    private EditText ETminus2;
    private Button Bminus;
    private TextView Tminus;

    private EditText ETtimes1;
    private EditText ETtimes2;
    private Button Btimes;
    private TextView Ttimes;

    private EditText ETdivision1;
    private EditText ETdivision2;
    private Button Bdivision;
    private TextView Tdivision;

    private Button Breset;
    private Button Blog;

    private ArrayList<TextView> Tlist;

    private ArrayList<String> log;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laskuri);

        ETplus1 = (EditText) findViewById(R.id.editTextPlus1);
        ETplus2 = (EditText) findViewById(R.id.editTextPlus2);
        Tplus = (TextView) findViewById(R.id.textViewPlus2);
        Bplus = (Button) findViewById(R.id.buttonLaskePlus);

        ETminus1 = (EditText) findViewById(R.id.editTextMinus1);
        ETminus2 = (EditText) findViewById(R.id.editTextMinus2);
        Tminus = (TextView) findViewById(R.id.textViewMinus2);
        Bminus = (Button) findViewById(R.id.buttonLaskeMinus);

        ETtimes1 = (EditText) findViewById(R.id.editTextTimes1);
        ETtimes2 = (EditText) findViewById(R.id.editTextTimes2);
        Ttimes = (TextView) findViewById(R.id.textViewTimes2);
        Btimes = (Button) findViewById(R.id.buttonLaskeTimes);

        ETdivision1 = (EditText) findViewById(R.id.editTextDivision1);
        ETdivision2 = (EditText) findViewById(R.id.editTextDivision2);
        Tdivision = (TextView) findViewById(R.id.textViewDivision2);
        Bdivision = (Button) findViewById(R.id.buttonLaskeDivision);

        Breset = (Button) findViewById(R.id.buttonReset);
        Blog = (Button) findViewById(R.id.buttonLog);

        log = new ArrayList<String>();

        Tlist = new ArrayList<TextView>(){{
            add(Tplus);
            add(Tminus);
            add(Ttimes);
            add(Tdivision);
            add(ETplus1);
            add(ETplus2);
            add(ETminus1);
            add(ETminus2);
            add(ETtimes1);
            add(ETtimes2);
            add(ETdivision1);
            add(ETdivision2);
        }};

        Bplus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                countPlus();
            }
        });

        Bminus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                countMinus();
            }
        });

        Btimes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                countTimes();
            }
        });

        Bdivision.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                countDivision();
            }
        });

        Breset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                resetValues();
            }
        });

        Blog.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showLog();
            }
        });
    }

    // Counts operation, updates answer and updates log. Handles arithmetic errors.
    private void countPlus(){
        String answer = null;
        try {
            answer = String.valueOf(Integer.parseInt(ETplus1.getText().toString()) + Integer.parseInt(ETplus2.getText().toString()));
            Tplus.setText(answer);
            log.add((log.size() + 1) + ":  " + ETplus1.getText().toString() + " + " + ETplus2.getText().toString() + " = " + answer);
        } catch (NumberFormatException | ArithmeticException e) {
            answer = "NaN";
            Tplus.setText(answer);
            log.add((log.size() + 1) + ":  NaN + NaN = NaN");
        }
        saveArray();
    }

    private void countMinus(){
        String answer = null;
        try {
            answer = String.valueOf(Integer.parseInt(ETminus1.getText().toString()) - Integer.parseInt(ETminus2.getText().toString()));
            Tminus.setText(answer);
            log.add((log.size() + 1) + ":  " + ETminus1.getText().toString() + " - " + ETminus2.getText().toString() + " = " + answer);
        } catch (NumberFormatException | ArithmeticException e) {
            answer = "NaN";
            Tminus.setText(answer);
            log.add((log.size() + 1) + ":  NaN - NaN = NaN");
        };
        saveArray();
    }

    private void countTimes(){
        String answer = null;
        try {
            answer = String.valueOf(Integer.parseInt(ETtimes1.getText().toString()) * Integer.parseInt(ETtimes2.getText().toString()));
            Ttimes.setText(answer);
            log.add((log.size() + 1) + ":  " + ETtimes1.getText().toString() + " * " + ETtimes2.getText().toString() + " = " + answer);
        } catch (NumberFormatException | ArithmeticException e) {
            answer = "NaN";
            Ttimes.setText(answer);
            log.add((log.size() + 1) + ":  NaN * NaN = NaN");
        };
        saveArray();
    }

    private void countDivision(){
        String answer = null;
        try {
            answer = String.valueOf(Integer.parseInt(ETdivision1.getText().toString()) / Integer.parseInt(ETdivision2.getText().toString()));
            Tdivision.setText(answer);
            log.add((log.size() + 1) + ":  " + ETdivision1.getText().toString() + " / " + ETdivision2.getText().toString() + " = " + answer);
        } catch (NumberFormatException | ArithmeticException e) {
            answer = "NaN";
            Tdivision.setText(answer);
            log.add((log.size() + 1) + ":  NaN / NaN = NaN");
        }
        saveArray();
    }

    private void resetValues() {
        for (TextView T: Tlist)
        {
            T.setText("");
        }
    }

    // Changes activity to activity_log.
    private void showLog() {
        Intent intentLog = new Intent(this, Logi.class);
        startActivity(intentLog);
    }

    // Save log information to SharedPreferences
    public boolean saveArray()
    {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor mEdit1 = sp.edit();
        /* sKey is an array */
        mEdit1.putInt("Status_size", log.size());

        for(int i=0;i<log.size();i++)
        {
            mEdit1.remove("Status_" + i);
            mEdit1.putString("Status_" + i, log.get(i));
        }

        return mEdit1.commit();
    }
}










