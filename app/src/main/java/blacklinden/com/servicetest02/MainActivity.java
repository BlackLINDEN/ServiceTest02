package blacklinden.com.servicetest02;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener, View.OnDragListener {

    Button btnYes, btnNo;
    ImageView imgDestination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnYes = findViewById(R.id.btnYes);
        btnNo = findViewById(R.id.btnNo);
        imgDestination = findViewById(R.id.imgDestination);

        btnYes.setOnTouchListener(this);
        btnNo.setOnTouchListener(this);
        imgDestination.setOnDragListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {


        View.DragShadowBuilder mShadow = new View.DragShadowBuilder(v);
        ClipData.Item item = new ClipData.Item(v.getTag().toString());
        String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
        ClipData data = new ClipData(v.getTag().toString(), mimeTypes, item);

        switch (v.getId()) {
            case R.id.btnYes:


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    v.startDragAndDrop(data, mShadow, null, 0);
                } else {
                    v.startDrag(data, mShadow, null, 0);

                }

                break;
            case R.id.btnNo:

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    v.startDragAndDrop(data, mShadow, null, 0);
                } else {
                    v.startDrag(data, mShadow, null, 0);
                }
                break;

        }

        return false;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:

                ((ImageView) v).setColorFilter(Color.YELLOW);
                btnYes.setVisibility(View.GONE);

                v.invalidate();
                return true;

            case DragEvent.ACTION_DRAG_ENTERED:

                String clipData = event.getClipDescription().getLabel().toString();
                switch (clipData) {
                    case "YES":
                        ((ImageView) v).setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY);
                        break;
                    case "NO":
                        ((ImageView) v).setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorAccent), android.graphics.PorterDuff.Mode.MULTIPLY);
                        break;
                }

                v.invalidate();
                return true;

            case DragEvent.ACTION_DRAG_LOCATION:
                return true;

            case DragEvent.ACTION_DRAG_EXITED:

                ((ImageView) v).clearColorFilter();
                ((ImageView) v).setColorFilter(Color.YELLOW);

                v.invalidate();
                return true;

            case DragEvent.ACTION_DROP:


                clipData = event.getClipDescription().getLabel().toString();
                Toast.makeText(getApplicationContext(),clipData, Toast.LENGTH_SHORT).show();

                v.invalidate();
                return true;

            case DragEvent.ACTION_DRAG_ENDED:


                ((ImageView) v).clearColorFilter();
                if (event.getResult()) {
                    Toast.makeText(MainActivity.this, "Awesome!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MainActivity.this, "Aw Snap! Try dropping it again", Toast.LENGTH_SHORT).show();
                }
                return true;

            default:
                return false;
        }
    }
}
