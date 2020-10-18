package com.was.minemvc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.was.core.utils.IntentUtils;
import com.was.minemvc.ui.BannerActivity;
import com.was.minemvc.ui.BottomNavActivity;
import com.was.minemvc.ui.LoginActivity;
import com.was.minemvc.ui.CommonListActivity;
import com.was.minemvc.ui.RefreshListActivity;
import com.was.minemvc.ui.SeletePictureActivity;
import com.was.minemvc.ui.TabLayoutActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickBottomNav(View view) {
        IntentUtils.startActivity(MainActivity.this, BottomNavActivity.class);
    }

    public void clickLogin(View view) {
        IntentUtils.startActivity(MainActivity.this, LoginActivity.class);
    }

    public void clickCommonList(View view) {
        IntentUtils.startActivity(MainActivity.this, CommonListActivity.class);
    }

    public void clickRefresh(View view) {
        IntentUtils.startActivity(MainActivity.this, RefreshListActivity.class);
    }

    public void clickTabLayout(View view) {
        IntentUtils.startActivity(MainActivity.this, TabLayoutActivity.class);
    }

    public void clickSeletePicture(View view) {
        IntentUtils.startActivity(MainActivity.this, SeletePictureActivity.class);
    }
    public void clickBanner(View view) {
        IntentUtils.startActivity(MainActivity.this, BannerActivity.class);
    }
}