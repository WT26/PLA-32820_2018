package alanko.wt.tietokanta;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, new AddActivity()).commit();

/*
        db11 = (TextView) findViewById(R.id.db11);
        db12 = (TextView) findViewById(R.id.db12);
        db13 = (TextView) findViewById(R.id.db13);
        db14 = (TextView) findViewById(R.id.db14);

        db21 = (TextView) findViewById(R.id.db21);
        db22 = (TextView) findViewById(R.id.db22);
        db23 = (TextView) findViewById(R.id.db23);
        db24 = (TextView) findViewById(R.id.db24);

        db31 = (TextView) findViewById(R.id.db31);
        db32 = (TextView) findViewById(R.id.db32);
        db33 = (TextView) findViewById(R.id.db33);
        db34 = (TextView) findViewById(R.id.db34);
*/
        //db11.setText("MOI");


        // Init Bottom Navigation View
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(

                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Log.i("DEBUG", "Navigation item selected");
                        switch (item.getItemId()) {
                            case R.id.navigation_add:
                                switchToFragment1();
                                break;
                            case R.id.navigation_database:
                                switchToFragment2();
                                break;
                            case R.id.navigation_delete:
                                switchToFragment3();
                                break;
                        }
                        return true;
                    }
                });


    }
    public void switchToFragment1() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, new AddActivity()).commit();
        Log.i("DEBUG", "Add *Click*");
    }
    public void switchToFragment2() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, new DatabaseActivity()).commit();
        Log.i("DEBUG", "Database *Click*");
    }
    public void switchToFragment3() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, new DeleteActivity()).commit();
        Log.i("DEBUG", "Delete *Click*");
    }
}
