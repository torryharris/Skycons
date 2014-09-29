package path.thbs.com.path;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;


public class MyActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        // Getting reference to PaintView
        PaintView paintView = (PaintView)findViewById(R.id.paint_view);

        // Getting reference to TextView tv_cooridinate
        TextView tvCoordinates = (TextView)findViewById(R.id.tv_coordinates);

        // Passing reference of textview to PaintView object to update on coordinate changes
        paintView.setTextView(tvCoordinates);

        // Setting touch event listener for the PaintView
        paintView.setOnTouchListener(paintView);

    }


}


