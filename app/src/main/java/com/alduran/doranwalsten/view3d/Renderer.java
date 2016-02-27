package com.alduran.doranwalsten.view3d;

import android.app.Activity;
import android.content.Context;
<<<<<<< HEAD
=======
import android.graphics.Point;
import android.opengl.GLU;
import android.util.Log;
import android.view.Display;
>>>>>>> 443fe9e67b780a69a72eba7d2336841f9362efff
import android.view.MotionEvent;
import android.view.WindowManager;

import org.rajawali3d.Object3D;
import org.rajawali3d.lights.DirectionalLight;
import org.rajawali3d.loader.LoaderOBJ;
import org.rajawali3d.loader.ParsingException;
import org.rajawali3d.materials.Material;
<<<<<<< HEAD
import org.rajawali3d.materials.textures.Texture;
=======
import org.rajawali3d.math.vector.Vector3;
>>>>>>> 443fe9e67b780a69a72eba7d2336841f9362efff
import org.rajawali3d.primitives.Sphere;
import org.rajawali3d.renderer.RajawaliRenderer;


/**
 * Created by ron on 2/17/16.
 */

public class Renderer extends RajawaliRenderer {

    public void onTouchEvent(MotionEvent event){

        float winX =  event.getRawX();
        float winY = event.getRawY();

        Vector3 model_point = screenToWorld(winX,winY,width,height,1);
        Log.d("locate", String.format("%.2f %.2f %.2f", model_point.x, model_point.y, model_point.z));
    }

    private Vector3 screenToWorld(float x, float y, int viewportWidth, int viewportHeight, float projectionDepth)
    {
        float[] r1 = new float[16];
        int[] viewport = new int[] { 0, 0, viewportWidth, viewportHeight };

        GLU.gluUnProject(x, viewportHeight - y, 0.0f, getCurrentCamera().getViewMatrix().getFloatValues(), 0, getCurrentCamera().getProjectionMatrix().getFloatValues(), 0, viewport, 0, r1, 0);

        //take the normalized vector from the resultant projection and the camera, and then project by the desired distance from the camera.
        Vector3 result = new Vector3(-r1[0], r1[1], r1[2]);
        result.subtract(getCurrentCamera().getPosition());
        result.normalize();
        result.multiply(projectionDepth);
        result.add(getCurrentCamera().getPosition());
        return result;
    }
    public void onOffsetsChanged(float x, float y, float z, float w, int i, int j){
    }

    public Context context;
    public int ID;
    WindowManager wm = (WindowManager) this.context.getSystemService(Context.WINDOW_SERVICE);
    Display display = wm.getDefaultDisplay();
    Point dude = new Point();
    int width;
    int height;
    Object3D mObject;
    public Renderer(Context context, int ID) {
        super(context);
        this.context = context;
        setFrameRate(60);
        this.ID = ID;
        display.getSize(dude);
        width = dude.x;
        height = dude.y;
    }
    private DirectionalLight directionalLight;
    private Sphere earthSphere;


    public void initScene(){

        directionalLight = new DirectionalLight(1f, .2f, -1.0f);
        directionalLight.setColor(1.0f, 1.0f, 1.0f);
        directionalLight.setPower(2);
        getCurrentScene().addLight(directionalLight);

<<<<<<< HEAD

        LoaderOBJ objParser = new LoaderOBJ(mContext.getResources(), mTextureManager, R.raw.test_obj);
=======
        LoaderOBJ objParser = new LoaderOBJ(mContext.getResources(), mTextureManager, R.raw.test_obj);

>>>>>>> 443fe9e67b780a69a72eba7d2336841f9362efff

        try {
            objParser.parse();
        }catch (ParsingException e) {

        }

<<<<<<< HEAD
        Object3D mObject = objParser.getParsedObject();
        //mObject.setColor(Color.TRANSPARENT);
        mObject.setScale(5.f);
        Material material = new Material();
        Texture texture = new Texture("Teja",R.drawable.patrick_texture);
        //Texture texture_2 = new Texture("shape",R.drawable.image3);
        //texture_2.setWrapType(ATexture.WrapType.CLAMP);
        //texture_2.setWidth(5);
        //texture_2.setHeight(5);

        /*
        try {
            //material.addTexture(texture);
            //material.addTexture(texture_2);
        } catch (ATexture.TextureException error){
            Log.d("DEBUG", "TEXTURE ERROR");
        }
        */
=======
        mObject = objParser.getParsedObject();
//      mObject.setColor(Color.TRANSPARENT);
        mObject.setScale(1.f);
        Material material = new Material();
//        Texture texture = new Texture("Teja",R.drawable.patrick_texture);
//        try {
//            material.addTexture(texture);
//        } catch (ATexture.TextureException error){
//            Log.d("DEBUG", "TEXTURE ERROR");
//        }
>>>>>>> 443fe9e67b780a69a72eba7d2336841f9362efff

        //mObject.setMaterial(material);
        //getCurrentScene().setBackgroundColor(R.color.colorPrimary);
        getCurrentScene().addChild(mObject);

        //mObject.getModelMatrix();
        /*
        Material material = new Material();
        material.enableLighting(true);
        material.setDiffuseMethod(new DiffuseMethod.Lambert());
        material.setColor(0);

        Texture earthTexture = new Texture("Earth", R.drawable.earthtruecolor_nasa_big);
        try{
            material.addTexture(earthTexture);

        } catch (ATexture.TextureException error){
            Log.d("DEBUG", "TEXTURE ERROR");
        }


        earthSphere = new Sphere(1, 24, 24);
        earthSphere.setMaterial(material);
        getCurrentScene().addChild(earthSphere);
        getCurrentCamera().setZ(4.2f);
        */
        ArcballCamera arcball = new ArcballCamera(this.context, ((Activity) this.context).findViewById(ID));
        arcball.setTarget(mObject); //your 3D Object

        arcball.setPosition(0,0,4); //optional

        //getCurrentScene().replaceAndSwitchCamera(getCurrentCamera(), arcball);

    }



//    @Override
//    public void onRender(final long elapsedTime, final double deltaTime) {
//        super.onRender(elapsedTime, deltaTime);
//        earthSphere.rotate(Vector3.Axis.Y, 1.0);
//    }

}
