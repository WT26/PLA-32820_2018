package alanko.wt.tietokanta;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.MenuItem;

public class MainActivity extends FragmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, new AddActivity()).commit();

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

    // Methods for switching between fragments.
    public void switchToFragment1() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, new AddActivity()).commit();
    }

    public void switchToFragment2() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, new DatabaseActivity()).commit();
    }

    public void switchToFragment3() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, new DeleteActivity()).commit();
    }
}
