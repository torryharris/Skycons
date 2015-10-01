Skycons for Android [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Skycons-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/1043)
===================

<p align="center">
  <img src="https://github.com/torryharris/Skycons/blob/master/Skycons/skycons.gif" alt="Skycons"/>
</p>

We set out to create a native weather app on Xamarin but we wanted to build something unique to differentiate ourselves amongst the plethora of weather apps avaialble out there. The [DarkSky](http://darkskyapp.com) app & [Forecast](http://forecast.io) did an excellent job with their [Skycons](http://darkskyapp.github.io/skycons/). 

We wanted to re-use the same icons for our app as well, however, we found that these animated icons don't exist natively on either platforms. This is currently a work-in-progress as the team is finetuning the animation & the performance itself. You can download the complete repository which also contains the sample project SkyconsDemo.

*Built with Android Studio (1.3.2)*

## Available Icons

| Icon | View |
| :------: | :---: |
| Cloud | CloudView |
| Clear Sky (Sunny) | SunView |
| Clear Night | MoonView |
| Partly Cloudy Day | CloudSunView |
| Partly Cloudy Night | CloudMoonView |
| Heavy Showers | CloudHvRainView |
| Snow | CloudSnowView |
| Light Showers | CloudRainView |
| Fog | CloudFogView |
| Wind | WindView |
| Thunder | CloudThunderView |

## How to use
 - Deployment target should be API level 14 or above.
 - Import 'SkyconsLibrary' as a library to your project.


## Adding icons:

    1) To add a skycon(icon) through the XML:
                     
           <com.thbs.skycons.library.<skycon>
             android:layout_width="250dp"
             android:layout_height="250dp"
             android:layout_centerHorizontal="true"
             android:layout_centerVertical="true"
             app:isStatic = "true"
             app:strokeColor = "#000000"
             app:bgColor = "#ffffff"/>

    where <skycon> represents the particular view/icon. For example "MoonView"
    
    
    2) To add a skycon(For instance, WindView) through Java code, perform following in the activity:
    
          
            LinearLayout layout = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layout.setLayoutParams(params);

            WindView windView = new WindView(this);
            //Using these params, you can control width & height of the icon
            params.width = 200;
            params.height = 200;
            windView.setLayoutParams(params);
          
            layout.addView(windView);
            this.setContentView(layout);
          
          

 -  For a better experience, keep 'layout_width' & 'layout_height' the same.
 
## Customization:

| Options | Value | Description |
| :------: | :---: | :----------: |
| layout_width | 100dp (recommended min value) | Width of icon |
| layout_height | 100dp (recommended min value) | Height of the icon |
| isStatic | True / False | Boolean Value to enable on-touch animation |
| strokeColor | HEX | Customize the color of the icon |
| bgColor | HEX | Customize the background colour |



<B>Pull Requests</B> are welcomed. We are looking forward for your suggestions on new icons & animations.



##Demo
<a href="https://play.google.com/store/apps/details?id=com.thbs.skycons">
  <img alt="Get it on Google Play"
       src="https://developer.android.com/images/brand/en_generic_rgb_wo_60.png" />
</a>


License
=======

Skycons for Android is being made available in public domain under similar terms like the [original](https://github.com/darkskyapp/skycons)
