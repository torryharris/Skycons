Skycons for Android (Work-in-Progress)
======================================

<p align="center">
  <img src="https://github.com/torryharris/Skycons/blob/master/Skycons/skycons.gif" alt="Skycons"/>
</p>

We set out to create a native weather app on Xamarin but we wanted to build something unique to differentiate ourselves amongst the plethora of weather apps avaialble out there. The [DarkSky](http://darkskyapp.com) app & [Forecast](http://forecast.io) did an excellent job with their [Skycons](http://darkskyapp.github.io/skycons/). 

We wanted to re-use the same icons for our app as well, however, we found that these animated icons don't exist natively on either platforms. This is currently a work-in-progress as the team is finetuning the animation & the performance itself. You can download the complete project 

##How to use:
1. Deployment target should be Api level 14 or above.

2. Import SkyconsLibrary as a library to your project.

  
3. ##Adding icons:

    For eg: To add Cloud icon to the activity, add the CloudView xml.
                       
        <com.thbs.skycons.library.CloudView
            android:layout_width="250dp"
            android:layout_height="250dp"
            android:layout_centerHorizontal="true"
            android:layout_centerVertical="true"
            app:isStatic = "true"
            app:strokeColor = "#000000"
            app:bgColor = "#ffffff"/>

4. For a better experience, keep 'layout_width' & 'layout_height' the same.


5. ##Customization
    
    - Basic customization can be done in the xml.
    
        
        Static/Animating Icons:
        
                set "app:isStatic" to true, for static icons // PS. On touch, the icon animates once

                set "app:isStatic" to false, for continuous animation.
                
              
           
           
        Colour customization:
        
                app:strokeColor = "#000000";  // Edit this to change the brush color
          
                app:bgColor = "#ffffff";   // Edit this to change the background color
        
        
 



License
=======

Skycons for Android is being made available in public domain under similar terms like the [original](http://darkskyapp.github.io)
