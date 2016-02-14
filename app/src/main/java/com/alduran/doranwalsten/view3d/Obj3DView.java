package com.alduran.doranwalsten.view3d;

import android.content.ClipData;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

import min3d.core.Object3dContainer;
import min3d.core.RendererActivity;
import min3d.parser.IParser;
import min3d.parser.Parser;
import min3d.vos.Light;

/**
 * Created by doranwalsten on 2/11/16.
 */
public class Obj3DView extends RendererActivity{
    private Object3dContainer faceObject3D;
    double rotation;
    boolean rot_on = false;

    @Override
    public void initScene() {
        scene.lights().add(new Light());
        scene.lights().add(new Light());
        _glSurfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.v("Touch","Down");
                    //new_flap.setTouchpoint(event.getRawX(), event.getRawY());
                    ClipData clipData = ClipData.newPlainText("", "");

                    //Use Bitmap to create Shadow
                    //v.setDrawingCacheEnabled(true);
                    //Bitmap viewCapture = v.getDrawingCache();
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder();
                    //FlapDragShadowBuilder shadowBuilder = new FlapDragShadowBuilder(viewCapture);
                    //shadowBuilder.setDisplacement(new_flap.getDisplacement()[0], new_flap.getDisplacement()[1]);
                    v.startDrag(clipData, shadowBuilder, v, 0);
                    //v.setVisibility(View.GONE);
                } else {
                    //v.setVisibility(View.GONE);
                }

                return true;
            }
        });
        _glSurfaceView.setOnDragListener(new View.OnDragListener() {
            float x;
            float y;
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        Log.v("drag","DRAG STARTED");
                        x = event.getX();
                        y = event.getY();
                        return true;

                    case DragEvent.ACTION_DROP:
                        Log.v("drag","DRAG DROPPED");
                        double x_dist = Math.pow((x - event.getX()),2);
                        double y_dist = Math.pow((y - event.getY()),2);
                        double dist = Math.sqrt(x_dist + y_dist);
                        if (dist >  10) {
                            rotation = 0.5;
                            rot_on = true;
                        }
                        //Try dropping a new image at the point

                        return true;
                    case DragEvent.ACTION_DRAG_ENDED:
                        return true;
                }
                return false;
            }
        });

        Light myLight = new Light();
        myLight.position.setZ(150);
        scene.lights().add(myLight);
        IParser myParser = Parser.createParser(Parser.Type.OBJ, getResources(), "com.alduran.doranwalsten.view3d:raw/face_obj",true);
        myParser.parse();

        faceObject3D = myParser.getParsedObject();
        faceObject3D.position().x = faceObject3D.position().y = faceObject3D.position().z = 0;
        faceObject3D.scale().x = faceObject3D.scale().y = faceObject3D.scale().z = 0.009f;
// Depending on the model you will need to change the scale
        faceObject3D.scale().x = faceObject3D.scale().y = faceObject3D.scale().z = 0.009f;

        scene.addChild(faceObject3D);

    }

    @Override
    public void updateScene() {
        if (rot_on) {
            faceObject3D.rotation().x += rotation;
        }
    }

}
