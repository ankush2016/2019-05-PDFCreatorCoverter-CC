package vehicalsregisteration.com.pdfcreatorcoverter.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;
import hotchemi.android.rate.AppRate;
import hotchemi.android.rate.OnClickButtonListener;
import vehicalsregisteration.com.pdfcreatorcoverter.R;
import vehicalsregisteration.com.pdfcreatorcoverter.fragment.CreatePdfFragment;
import vehicalsregisteration.com.pdfcreatorcoverter.fragment.MorePDFFragment;
import vehicalsregisteration.com.pdfcreatorcoverter.fragment.PDFEditorFragment;
import vehicalsregisteration.com.pdfcreatorcoverter.fragment.PDFPriviryFragment;
import vehicalsregisteration.com.pdfcreatorcoverter.fragment.ViewPdfFragment;
import vehicalsregisteration.com.pdfcreatorcoverter.new_utilities.SetupFacebookAds;

public class BottomNavigation extends AppCompatActivity {

    private BottomNavigationView navigation;
    Fragment fragment = null;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        loadFragment(new CreatePdfFragment());
        AppRate.with(this)
                .setInstallDays(0) // default 10, 0 means install day.
                .setLaunchTimes(2) // default 10
                .setRemindInterval(2) // default 1
                .setShowLaterButton(true) // default true
                .setDebug(false) // default false
                .setOnClickButtonListener(new OnClickButtonListener() { // callback listener.
                    @Override
                    public void onClickButton(int which) {
                        Uri uri = Uri.parse("market://details?id=" + getPackageName());
                        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(myAppLinkToMarket);
                    }
                })
                .monitor();

        // Show a dialog if meets conditions
        AppRate.showRateDialogIfMeetsConditions(this);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.create_pdf:
                        fragment = new CreatePdfFragment();
                        navigation.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                        if (Build.VERSION.SDK_INT >= 21) {
                            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
                        }
                        break;
                    case R.id.pdf_privicy:
                        fragment=new PDFPriviryFragment();
                        navigation.setBackgroundColor(getResources().getColor(R.color.pink_800));
                        if (Build.VERSION.SDK_INT >= 21) {
                            getWindow().setStatusBarColor(getResources().getColor(R.color.pink_800));
                        }
                        break;
                    case R.id.editor_pdf:
                        fragment=new PDFEditorFragment();
                        navigation.setBackgroundColor(getResources().getColor(R.color.grey_700));
                        if (Build.VERSION.SDK_INT >= 21) {
                            getWindow().setStatusBarColor(getResources().getColor(R.color.grey_700));
                        }
                        break;
                    case R.id.view_pdf:
                        navigation.setBackgroundColor(getResources().getColor(R.color.teal_800));
                        if (Build.VERSION.SDK_INT >= 21) {
                            getWindow().setStatusBarColor(getResources().getColor(R.color.teal_800));
                        }
                        fragment=new ViewPdfFragment();
                        break;

                    case R.id.more:
                        navigation.setBackgroundColor(getResources().getColor(R.color.blue_grey_700));
                        if (Build.VERSION.SDK_INT >= 21) {
                            getWindow().setStatusBarColor(getResources().getColor(R.color.blue_grey_700));
                        }
                        fragment=new MorePDFFragment();

                        break;
                }
                return loadFragment(fragment);
            }
        });

        setupFacebookNativeBannerAds();

    }

    private void setupFacebookNativeBannerAds() {
        SetupFacebookAds setupFacebookAds = new SetupFacebookAds(this);
        setupFacebookAds.facebookNativeBannerAds();
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}