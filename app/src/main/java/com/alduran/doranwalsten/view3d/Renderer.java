package com.alduran.doranwalsten.view3d;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;

import org.rajawali3d.lights.DirectionalLight;
import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.methods.DiffuseMethod;
import org.rajawali3d.materials.textures.ATexture;
import org.rajawali3d.materials.textures.Texture;
import org.rajawali3d.math.vector.Vector3;
import org.rajawali3d.primitives.Sphere;
import org.rajawali3d.renderer.RajawaliRenderer;

import min3d.parser.ObjParser;

/**
 * Created by ron on 2/17/16.
 */
public class Renderer extends RajawaliRenderer {
    public void onTouchEvent(MotionEvent event){
    }

    public void onOffsetsChanged(float x, float y, float z, float w, int i, int j){
    }

    public Context context;
    public int ID;

    public Renderer(Context context, int ID) {
        super(context);
        this.context = context;
        setFrameRate(60);
        this.ID = ID;
    }
    private DirectionalLight directionalLight;
    private Sphere earthSphere;

    public void initScene(){

        ObjParser objParser = new ObjParser(mContext.getResources(), mTextureManager, R.raw.face_obj);
        objParser.parse();
        BaseObject3D mObject = objParser.getParsedObject();
        mObject.setLight(mLight);
        addChild(mObject);

        directionalLight = new DirectionalLight(1f, .2f, -1.0f);
        directionalLight.setColor(1.0f, 1.0f, 1.0f);
        directionalLight.setPower(2);
        getCurrentScene().addLight(directionalLight);

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

        ArcballCamera arcball = new ArcballCamera(this.context, ((Activity) this.context).findViewById(ID));
        arcball.setTarget(earthSphere); //your 3D Object

        arcball.setPosition(0,0,4); //optional

        getCurrentScene().replaceAndSwitchCamera(getCurrentCamera(), arcball);

    }



//    @Override
//    public void onRender(final long elapsedTime, final double deltaTime) {
//        super.onRender(elapsedTime, deltaTime);
//        earthSphere.rotate(Vector3.Axis.Y, 1.0);
//    }

}