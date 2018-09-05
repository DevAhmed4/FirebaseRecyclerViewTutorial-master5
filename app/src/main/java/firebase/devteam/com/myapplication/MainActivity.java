package firebase.devteam.com.myapplication;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import android.app.Activity;
import com.google.android.gms.ads.MobileAds;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {


    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout ;
    Home_Fragment home_fragment;
    Jobs_Fragment jobs_fragment;
    AboutUs_Fragment aboutUs_fragment;
    Find_Jobs_Fragment find_jobs_fragment;
    private GestureDetectorCompat detectorCompat;
    int i  = 0;
    int ID = R.id.jobs_id;
    private InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, "ca-app-pub-4235785009971075/3645417920");

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-4235785009971075/4519515894");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        detectorCompat = new GestureDetectorCompat(this, new GestureObject());
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNavigationView);
        frameLayout = (FrameLayout)findViewById(R.id.Frame_Layout);
        home_fragment = new Home_Fragment();
        jobs_fragment = new Jobs_Fragment();
        aboutUs_fragment = new AboutUs_Fragment();
        find_jobs_fragment = new Find_Jobs_Fragment();
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
               // Toast.makeText(MainActivity.this, "Loaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
               // Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
                //Toast.makeText(MainActivity.this, "Opened", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                //Toast.makeText(MainActivity.this, "Left", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the interstitial ad is closed.
               // Toast.makeText(MainActivity.this, "Cloased", Toast.LENGTH_SHORT).show();
            }
        });

        setFragment(jobs_fragment);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int ID;
                ID = item.getItemId();
                switch (ID){
                    case R.id.jobs_id:
                        setFragment(jobs_fragment);
                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        }
                        ID = R.id.jobs_id;
                        return true;
                    case R.id.site_id:
                        setFragment(home_fragment);
                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        }
                        ID = R.id.site_id;
                        return true;
                    case R.id.about_us_id:
                        setFragment(aboutUs_fragment);
                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        }
                        ID = R.id.about_us_id;
                        return true;
                    case R.id.sitesearch_id:
                        setFragment(find_jobs_fragment);
                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        }
                        ID = R.id.sitesearch_id;
                        return true;
                }
                return true;
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(i == 0 ){
            Toast.makeText(this, "Press again to exit ", Toast.LENGTH_SHORT).show();
            i++;
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.detectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }



    private void setFragment(Fragment fragment) {
    FragmentManager manager = getFragmentManager();
    FragmentTransaction fragmentTransaction = manager.beginTransaction();
    fragmentTransaction.replace(R.id.Frame_Layout,fragment);
    fragmentTransaction.commit();
    }




    private class GestureObject extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            if(e2.getX() > e1.getX()){
                //Left to right swap
                if(ID == R.id.jobs_id){
                    //do nothing here
                   // Toast.makeText(MainActivity.this, "1", Toast.LENGTH_SHORT).show();
                }else if(ID == R.id.site_id){
                    setFragment(jobs_fragment);
                    ID = R.id.jobs_id;
                   // Toast.makeText(MainActivity.this, "2", Toast.LENGTH_SHORT).show();
                }else if(ID == R.id.about_us_id){
                    setFragment(home_fragment);
                    ID = R.id.site_id;
                   // Toast.makeText(MainActivity.this, "3", Toast.LENGTH_SHORT).show();
                }
            }else if(e2.getX() < e1.getX()){
                //right to left swap

                if(ID == R.id.site_id){
                    setFragment(aboutUs_fragment);
                    ID = R.id.about_us_id;
                   // Toast.makeText(MainActivity.this, "4", Toast.LENGTH_SHORT).show();
                }else if(ID == R.id.jobs_id){
                    setFragment(home_fragment);
                    ID = R.id.site_id;
                   // Toast.makeText(MainActivity.this, "5", Toast.LENGTH_SHORT).show();
                }else if(ID == R.id.about_us_id){
                    //Do nothing here
                   // Toast.makeText(MainActivity.this, "6", Toast.LENGTH_SHORT).show();
                }
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }
}
