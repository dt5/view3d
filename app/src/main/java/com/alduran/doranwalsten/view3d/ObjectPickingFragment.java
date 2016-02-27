package com.alduran.doranwalsten.view3d;

import android.content.Context;
import android.graphics.Point;
import android.opengl.GLU;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;

import org.rajawali3d.Object3D;
import org.rajawali3d.lights.DirectionalLight;
import org.rajawali3d.loader.LoaderOBJ;
import org.rajawali3d.loader.ParsingException;
import org.rajawali3d.math.vector.Vector3;
import org.rajawali3d.util.OnObjectPickedListener;
import org.rajawali3d.util.RajLog;

public class ObjectPickingFragment extends BaseFragment implements
    OnTouchListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        ((View) mRajawaliSurface).setOnTouchListener(this);
        mRenderer = createRenderer();

        return mLayout;
    }

    @Override
    public BaseRenderer createRenderer() {
        return new ObjectPickingRenderer(getActivity());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ObjectPickingRenderer renderer = (ObjectPickingRenderer) mRenderer;
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float winX = event.getRawX();
            float winY = event.getRawY();
            Log.v("test", "HERE");
            Vector3 model_point = renderer.screenToWorld(winX, winY, renderer.width, renderer.height, (float) 0.5);
            RajLog.i("touch", "TOUCH!!!!!");
            Log.v("locate", String.format("%.2f %.2f %.2f", model_point.x, model_point.y, model_point.z));
        }

        return getActivity().onTouchEvent(event);
    }

    private final class ObjectPickingRenderer extends BaseRenderer
        implements OnObjectPickedListener {

        public Context context;


        Point dude = new Point();
        int width;
        int height;
        Object3D mObject;
        private DirectionalLight directionalLight;

        public ObjectPickingRenderer(Context context) {
            super(context);
            this.context = context;
            setFrameRate(60);
            WindowManager wm = (WindowManager) this.context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            display.getSize(dude);
            width = dude.x;
            height = dude.y;
        }

        @Override
        protected void initScene() {
            directionalLight = new DirectionalLight(1f, .2f, -1.0f);
            directionalLight.setColor(1.0f, 1.0f, 1.0f);
            directionalLight.setPower(2);
            getCurrentScene().addLight(directionalLight);

            LoaderOBJ objParser = new LoaderOBJ(mContext.getResources(), mTextureManager, R.raw.test_obj);
            try {
                objParser.parse();
            }catch (ParsingException e) {

            }

            mObject = objParser.getParsedObject();
            mObject.setScale(0.01f);
            getCurrentScene().addChild(mObject);
            /*
            ArcballCamera arcball = new ArcballCamera(this.context, ((Activity) this.context).findViewById(R.id.rajwali_surface));
            arcball.setTarget(mObject); //your 3D Object

            arcball.setPosition(0, 0, 4); //optional

            getCurrentScene().replaceAndSwitchCamera(getCurrentCamera(), arcball);
            */
        }

        @Override
        protected void onRender(long ellapsedRealtime, double deltaTime) {
            super.onRender(ellapsedRealtime, deltaTime);

        }


        public Vector3 screenToWorld(float x, float y, int viewportWidth, int viewportHeight, float projectionDepth)
        {
            float[] r1 = new float[16];
            int[] viewport = new int[] { 0, 0, viewportWidth, viewportHeight };

            float[] a = new float[16];
            float[] b = new float[16];
            float[] c = new float[16];
            getCurrentCamera().getModelMatrix().toFloatArray(a);
            getCurrentCamera().getProjectionMatrix().toFloatArray(b);

            GLU.gluUnProject(x, y, 0.5f,a, 0,b, 0,viewport, 0, r1, 0);

            //take the normalized vector from the resultant projection and the camera, and then project by the desired distance from the camera.
            Vector3 result = new Vector3(-r1[0], r1[1], r1[2]);
            result.subtract(getCurrentCamera().getPosition());
            result.normalize();
            result.multiply(projectionDepth);
            result.add(getCurrentCamera().getPosition());
            return result;
        }

        public void onObjectPicked(Object3D object) {
            object.setZ(object.getZ() == 0 ? -2 : 0);
        }

    }

}
