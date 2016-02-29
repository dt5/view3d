package com.alduran.doranwalsten.view3d;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        this.startActivity(new Intent(this,Obj3DView.class));
//    }



    ObjectPickingFragment curr_face;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager manager = getSupportFragmentManager();
        curr_face = (ObjectPickingFragment) manager.findFragmentById(R.id.face);


        /*
        final RajawaliSurfaceView surface = new RajawaliSurfaceView(this);
//        View tempView = findViewById(R.id.placeholder);
//        View linearLayout = findViewById(R.id.placeholder2);

        surface.setFrameRate(60.0);
        surface.setRenderMode(IRajawaliSurface.RENDERMODE_WHEN_DIRTY);

        // Add mSurface to your root view


        surface.setId(ID);
        renderer = new Renderer(this, ID);
        surface.setSurfaceRenderer(renderer);
        surface.setOnTouchListener(this);

        addContentView(surface, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT));
//        ((LinearLayout) linearLayout).addView(surface);
        */

    }

    public void translateButtonPressed(View v) {
        curr_face.switchCameraTranslate();
        Button not_pressed = (Button) findViewById(R.id.rotate_button);
        not_pressed.setEnabled(true);
        Button pressed = (Button) findViewById(R.id.translate_button);
        pressed.setEnabled(false);

    }

    public void rotateButtonPressed(View v) {
        curr_face.switchCameraRotate();
        Button not_pressed = (Button) findViewById(R.id.translate_button);
        not_pressed.setEnabled(true);
        Button pressed = (Button) findViewById(R.id.rotate_button);
        pressed.setEnabled(false);
    }

    /*
    public boolean onTouch(View v, MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float winX = event.getRawX();
            float winY = event.getRawY();
            Log.v("test", "HERE");
            Vector3 model_point = renderer.screenToWorld(winX, winY, renderer.width, renderer.height, 1);
            RajLog.i("touch", "TOUCH!!!!!");
            Log.v("locate", String.format("%.2f %.2f %.2f", model_point.x, model_point.y, model_point.z));
        }
        return super.onTouchEvent(event);
    }
    */
}
