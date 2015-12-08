package hslu.cg.cgproject.Shapes;

import android.opengl.GLES20;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import hslu.cg.cgproject.MyGLRenderer;

/**
 * Created by Loana on 07.12.2015.
 */
public class Square3D {
    private FloatBuffer vertexBuffer;  // Buffer for vertex-array
    private ShortBuffer indexBuffer;
    private FloatBuffer colorBuffer;

    private int numFaces = 6;
    private int colorHandle;
    private final String vertexShaderCode =
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "attribute vec4 aVertexColor;"+
                    "varying vec4 vColor;"+
                    "void main() {" +
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    " vColor = aVertexColor;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";
    private int MVPMatrixHandle;
    private int positionHandle;
    private final int program;

    static final int COORDS_PER_VERTEX = 3;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    private float[][]colors = {  // Colors of the 6 faces
            {1.0f, 0.5f, 0.0f, 1.0f},  // 0. orange
            {1.0f, 0.0f, 1.0f, 1.0f},  // 1. violet
            {0.0f, 1.0f, 0.0f, 1.0f},  // 2. green
            {0.0f, 0.0f, 1.0f, 1.0f},  // 3. blue
            {1.0f, 0.0f, 0.0f, 1.0f},  // 4. red
            {1.0f, 1.0f, 0.0f, 1.0f}   // 5. yellow
    };


    private float[] sCoordinate = {  // Vertices of the 6 faces
            // vordere Fläche
            -0.2f, -0.8f,  0.0f,
            0.2f, -0.8f,  0.0f,
            0.2f,  -0.4f,  0.0f,
            -0.2f,  -0.4f,  0.0f,
            // hintere Fläche
            -0.2f, -0.8f, -0.4f,
            -0.2f,  -0.4f, -0.4f,
            0.2f,  -0.4f, -0.4f,
            0.2f, -0.8f, -0.4f,
            // obere Fläche
            -0.2f,  -0.4f, -0.4f,
            -0.2f,  -0.4f,  0.0f,
            0.2f,  -0.4f,  0.0f,
            0.2f,  -0.4f, -0.4f,
            // untere Fläche
            -0.2f, -0.8f, -0.4f,
            0.2f, -0.8f, -0.4f,
            0.2f, -0.8f, 0.0f,
            -0.2f, -0.8f, 0.0f,
            // rechte Fläche
            0.2f, -0.8f, -0.4f,
            0.2f,  -0.4f, -0.4f,
            0.2f,  -0.4f,  0.0f,
            0.2f, -0.8f,  0.0f,
            // linke Fläche
            -0.2f, -0.8f, -0.4f,
            -0.2f, -0.8f,  0.0f,
            -0.2f,  -0.4f,  0.0f,
            -0.2f,  -0.4f, -0.4f
    };

    short[] indices = {
            0,  1,  2,      0,  2,  3,    // vorne
            4,  5,  6,      4,  6,  7,    // hinten
            8,  9,  10,     8,  10, 11,   // oben
            12, 13, 14,     12, 14, 15,   // unten
            16, 17, 18,     16, 18, 19,   // rechts
            20, 21, 22,     20, 22, 23    // links

    };

    // Constructor - Set up the buffers
    public Square3D() {
        // Setup vertex-array buffer. Vertices in float. An float has 4 bytes
        ByteBuffer vbb = ByteBuffer.allocateDirect(sCoordinate.length * 4);
        vbb.order(ByteOrder.nativeOrder()); // Use native byte order
        vertexBuffer = vbb.asFloatBuffer(); // Convert from byte to float
        vertexBuffer.put(sCoordinate);         // Copy data into buffer
        vertexBuffer.position(0);           // Rewind

       /*ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
        cbb.order(ByteOrder.nativeOrder()); // Use native byte order
        colorBuffer = cbb.asFloatBuffer(); // Convert from byte to float
        colorBuffer.put(colors);         // Copy data into buffer
        colorBuffer.position(0);           // Rewind*/

        indexBuffer = ByteBuffer.allocateDirect(indices.length * 2).order(ByteOrder.nativeOrder()).asShortBuffer();
        indexBuffer.put(indices).position(0);

        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        program = GLES20.glCreateProgram();
        GLES20.glAttachShader(program, vertexShader);
        GLES20.glAttachShader(program, fragmentShader);
        GLES20.glLinkProgram(program);
    }

    // Draw the shape
    public void draw(float[] mvpMatrix) {
        GLES20.glUseProgram(program);

        positionHandle = GLES20.glGetAttribLocation(program, "vPosition");
        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);

        colorHandle = GLES20.glGetAttribLocation(program, "vColor");
        GLES20.glEnableVertexAttribArray(colorHandle);

        for (int i = 0; i < numFaces; i++) {
            // Set the color for each of the faces
            colorHandle = GLES20.glGetUniformLocation(program, "vColor");
            GLES20.glUniform4fv(colorHandle, 1, colors[i], 0);
        }

        MVPMatrixHandle = GLES20.glGetUniformLocation(program, "uMVPMatrix");
        GLES20.glUniformMatrix4fv(MVPMatrixHandle, 1, false, mvpMatrix, 0);

        GLES20.glDrawElements(GLES20.GL_TRIANGLES, 36, GLES20.GL_UNSIGNED_SHORT, indexBuffer);

        GLES20.glDisableVertexAttribArray(positionHandle);
        GLES20.glDisableVertexAttribArray(colorHandle);
    }
}
