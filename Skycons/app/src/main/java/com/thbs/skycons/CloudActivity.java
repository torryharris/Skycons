package com.thbs.skycons;

import android.app.Activity;
import android.os.Bundle;

import com.thbs.skycons.views.CloudView;

/**
 * Created by administrator on 10/09/14.
 */
public class CloudActivity extends Activity {

    CloudView cloudView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cloudView1 = new CloudView(CloudActivity.this);
        setContentView(cloudView1);
    }

}


