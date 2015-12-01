package hslu.cg.cgproject;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by Loana on 30.11.2015.
 */
public class Square {
    private FloatBuffer vertexBuffer;
    private ShortBuffer drawListBuffer;

    static  final int COORDINATE_PER_VERTEX = 3;
    static  float sCoordinate[] = {
      -1.0f,1.0f,0.0f,      //top left
      -1.0f,0.0f,0.0f,      //bottom left
      0.0f,0.0f,0.0f,       //bottom right
      1.0f,1.0f,0.0f        //top right
    };

    private short drawOrder[] = {0,1,2,0,2,3};

    public Square(){
        //initialize vertex buffer for square coordinate
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(sCoordinate.length*4);
        byteBuffer.order(ByteOrder.nativeOrder());
        vertexBuffer = byteBuffer.asFloatBuffer();
        vertexBuffer.put(sCoordinate);
        vertexBuffer.position(0);
        //initialize buffer for draw lst
        ByteBuffer drawbyteBuffer = ByteBuffer.allocateDirect(drawOrder.length*2);
        drawListBuffer = drawbyteBuffer.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);
    }
}
