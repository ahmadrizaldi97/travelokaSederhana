package joss.polinema.ahmadrizaldi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {

    private ImageView icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        icon = (ImageView)findViewById(R.id.iv);

        //Membuat animasi dan mengambil resource dari resource pada file my_transition
        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.my_transition);

        //memulai animasi pada icon
        icon.startAnimation(myanim);

        //Membuat intent untuk berpindah ke activity Halaman Utama
        final Intent intent = new Intent(this, Login.class);
        //Mengatur alur jalannya program
        Thread timer = new Thread(){
            @Override
            public void run() {
                try{
                    //Memberikan waktu pending dalam satuan milisecond
                    sleep(5000);
                }catch (InterruptedException e){
                    //Print error pada console
                    e.printStackTrace();
                }finally {
                    //Memulai activity Halaman Utama dan menakhiri activity splash
                    startActivity(intent);
                    finish();
                }
            }
        };
        //memulai Tread
        timer.start();
    }
}
