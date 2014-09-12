package com.thbs.skycons;

import android.app.Activity;
import android.os.Bundle;
import com.thbs.skycons.views.CloudSnowView;

/**
 * Created by administrator on 10/09/14.
 */
public class CloudSnowActivity extends Activity {

    CloudSnowView cloudSnowView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cloud_snow);
    }

}
