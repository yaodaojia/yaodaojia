package com.example.yaodaojia.yaodaojia;

import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;

import com.example.yaodaojia.yaodaojia.base.BaseActivity;
import com.example.yaodaojia.yaodaojia.control.activity.MainActivity;

/**
 * Created by axi on 2017/8/8.
 */

public class App extends Application {
    public static BaseActivity activity;

    @Override
    public void onCreate() {
        super.onCreate();

    }
}
