package hslu.cg.cgproject.Shapes;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import hslu.cg.cgproject.MyGLRenderer;

/**
 * Created by Loana on 30.11.2015.
 */
public class Triangle {
    private FloatBuffer vertexBuffer;
    private int mPositionHandle;
    private int mColorHandle;

    private final int vertexCount = tCoordinate.length / COORDINATE_PER_VERTEX;
    private final int vertexStride = COORDINATE_PER_VERTEX * 4; // 4 bytes per vertex

    static final int COORDINATE_PER_VERTEX = 3;
    static float tCoordinate[] =
            {
                    0.0f, 0.9f, 0.0f,      //top
                    -0.3f, 0.2f, 0.0f,   // bottom left
                    0.3f, 0.2f, 0.0f     // bottom right
            };

    //set Color r,g,b,opacity
    float color[] = {1.0f, 0.0f, 0.0f, 1.0f};

    private final String vertexShaderCode =
            "uniform mat4 uMVPMatrix;"+
            "attribute vec4 vPosition;" +
            "void main() {" +
            "  gl_Position = uMVPMatrix*vPosition;" +
            "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
            "uniform vec4 vColor;" +
            "void main() {" +
            "  gl_FragColor = vColor;" +
            "}";

    private final int mProgram;

    public Triangle(){
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(tCoordinate.length *4);
        byteBuffer.order(ByteOrder.nativeOrder());
        //Creating floatpoint buffer from Bytebuffer
        vertexBuffer = byteBuffer.asFloatBuffer();
        //add coordinate to Floatbuffer
        vertexBuffer.put(tCoordinate);
        //set Buffer to read first coordinate
        vertexBuffer.position(0);

        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);

        // create empty OpenGL ES Program
        mProgram = GLES20.glCreateProgram();

        // add the vertex shader to program
        GLES20.glAttachShader(mProgram, vertexShader);

        // add the fragment shader to program
        GLES20.glAttachShader(mProgram, fragmentShader);

        // creates OpenGL ES program executables
        GLES20.glLinkProgram(mProgram);

    }

    public void draw(float[] mvpMatrix) {
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(mProgram);

        // get handle to vertex shader's vPosition member
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(mPositionHandle, COORDINATE_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        // get handle to fragment shader's vColor member
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

        // Set color for drawing the triangle
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        // get handle to shape's transformation matrix
        int mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");

        // Pass the projection and view transformation to the shader
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
}

