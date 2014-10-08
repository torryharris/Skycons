Skycons for Android
===================

<p align="center">
  <img src="https://github.com/torryharris/Skycons/blob/master/Skycons/skycons.gif" alt="Skycons"/>
</p>

We set out to create a native weather app on Xamarin but we wanted to build something unique to differentiate ourselves amongst the plethora of weather apps avaialble out there. The [DarkSky](http://darkskyapp.com) app & [Forecast](http://forecast.io) did an excellent job with their [Skycons](http://darkskyapp.github.io/skycons/). 

We wanted to re-use the same icons for our app as well, however, we found that these animated icons don't exist natively on either platforms. This is currently a work-in-progress as the team is finetuning the animation & the performance itself. You can download the complete project 

## Available Icons
- Cloud
- Clear Skies (Sunny)
- Clear Night
- Partly Cloudy (Day & Night)
- Light Showers
- Heavy Showers
- Snow
- Fog
- Wind
- Thunder

  ## How to use:
 - Deployment target should be Api level 14 or above.

 - Import SkyconsLibrary as a library to your project.

  
   ##Adding icons:

    For eg: To add Cloud icon to the activity, add the CloudView xml.
                       
        <com.thbs.skycons.library.CloudView
            android:layout_width="250dp"
            android:layout_height="250dp"
            android:layout_centerHorizontal="true"
            android:layout_centerVertical="true"
            app:isStatic = "true"
            app:strokeColor = "#000000"
            app:bgColor = "#ffffff"/>

 -  For a better experience, keep 'layout_width' & 'layout_height' the same.


   ##Customization
    
    | Options | Value | Description |
    | :------: | :---: | :----------: |
    | layout_width | 100dp (recommended min value) | Width of icon |
    | layout_height | 100dp (recommended min value) | Height of the icon |
    | isStatic | True / False | Boolean Value to enable on-touch animation |
    | strokeColor | HEX | Customize the color of the icon |
    | bgColor | HEX | Customize the background colour |

License
=======

Skycons for Android is being made available in public domain under similar terms like the [original](http://darkskyapp.github.io)
