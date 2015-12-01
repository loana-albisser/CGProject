package hslu.cg.cgproject;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by Loana on 30.11.2015.
 */
public class Triangle {
    private FloatBuffer vertexBuffer;

    static final int COORDINATE_PER_VERTEX = 3;
    static float tCoordinate[] =
            {
                    0.0f, 0.6f, 0.0f,      //top
                    -0.5f, -0.3f, 0.0f,   // bottom left
                    0.5f, -0.3f, 0.0f     // bottom right
            };

    //set Color r,g,b,opacity
    float color[] = {1.0f, 0.0f, 0.0f, 1.0f};

    public Triangle(){
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(tCoordinate.length *4);
        byteBuffer.order(ByteOrder.nativeOrder());
        //Creating floatpoint buffer from Bytebuffer
        vertexBuffer = byteBuffer.asFloatBuffer();
        //add coordinate to Floatbuffer
        vertexBuffer.put(tCoordinate);
        //set Buffer to read first coordinate
        vertexBuffer.position(0);
    }
}

