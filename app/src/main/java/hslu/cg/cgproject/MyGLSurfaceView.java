package hslu.cg.cgproject;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by Loana on 30.11.2015.
 */
public class MyGLSurfaceView extends GLSurfaceView {

    private final MyGLRenderer mRenderer;

    public MyGLSurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        mRenderer = new MyGLRenderer();
        //set the Renderer for drawing on the GLSurfaceView
        setRenderer(mRenderer);
    }
}
