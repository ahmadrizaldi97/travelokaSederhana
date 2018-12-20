package joss.polinema.ahmadrizaldi;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import joss.polinema.ahmadrizaldi.Fragments.FragmentMap;
import joss.polinema.ahmadrizaldi.Fragments.HomeFragment;
import joss.polinema.ahmadrizaldi.Fragments.RoomFragment;
import joss.polinema.ahmadrizaldi.Fragments.UserFragment;

public class MainActivity extends AppCompatActivity {


    //Memberikan event pada navigation view
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    FragmentMap homeFragment = new FragmentMap();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content, homeFragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_dashboard:

                    RoomFragment homeFragments = new RoomFragment();
                    FragmentTransaction fragmentTransactions = getSupportFragmentManager().beginTransaction();
                    fragmentTransactions.replace(R.id.content, homeFragments);
                    fragmentTransactions.commit();
                    return true;

                case R.id.navigation_notifications:

                    UserFragment homeFragmentss = new UserFragment();
                    SharedPreferences sharedPref2 = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

                    String emaill2 = "dadsa";
                    homeFragmentss.setData(emaill2);
                    FragmentTransaction fragmentTransactionss = getSupportFragmentManager().beginTransaction();
                    fragmentTransactionss.replace(R.id.content, homeFragmentss);
                    fragmentTransactionss.commit();
                    return true;

            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inisialisasi fragment maps
        FragmentMap homeFragment = new FragmentMap();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, homeFragment);
        fragmentTransaction.commit();

        //inisialisasi BottomNavigationView
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        //membuat event click dengan memanggil function listener mOnNavigationItemSelectedListener diatas
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }
}
