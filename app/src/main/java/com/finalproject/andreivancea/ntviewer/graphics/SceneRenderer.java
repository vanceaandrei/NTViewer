package com.finalproject.andreivancea.ntviewer.graphics;

import android.content.Context;
import android.graphics.Color;
import android.opengl.GLES20;
import android.util.Log;
import android.view.MotionEvent;

import com.finalproject.andreivancea.ntviewer.R;

import org.rajawali3d.Camera;
import org.rajawali3d.lights.DirectionalLight;
import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.methods.DiffuseMethod;
import org.rajawali3d.materials.textures.ATexture;
import org.rajawali3d.materials.textures.Texture;
import org.rajawali3d.math.vector.Vector3;
import org.rajawali3d.primitives.Cube;
import org.rajawali3d.primitives.Line3D;
import org.rajawali3d.primitives.Sphere;
import org.rajawali3d.renderer.RajawaliRenderer;

import java.util.Stack;

/**
 * Created by andrei.vancea on 2/2/2017.
 */

public class SceneRenderer extends RajawaliRenderer {

    private static final String TAG = "SceneRenderer";

    private Context context;
    private DirectionalLight directionalLight;
    private Sphere earthSphere;
    private Cube cube;

    private float xStartPos;
    private float xPos;
    private float yStartPos;
    private float yPos;
    private float xOffset;
    private Camera mCamera;
    private float cLookLastX;
    private float cLookLastY;

    private Vector3 cameraCoords;

    public SceneRenderer(Context context) {
        super(context);
        this.context = context;
        setFrameRate(60);
    }

    @Override
    protected void initScene() {
        directionalLight = new DirectionalLight(1.0f, 0.2f, -1.0f);
        directionalLight.setColor(1.0f, 1.0f, 1.0f);
        directionalLight.setPower(2.0f);
        getCurrentScene().addLight(directionalLight);

        Material material = new Material();
        material.enableLighting(true);
        material.setDiffuseMethod(new DiffuseMethod.Lambert());
        material.setColor(0);

        Texture earthTexture = new Texture("Earth", R.drawable.earthtruecolor_nasa_big);
        try {
            material.addTexture(earthTexture);
        } catch (ATexture.TextureException error) {
            Log.d("DEBUG", "TEXTURE ERROR");
        }

        earthSphere = new Sphere(1, 24, 24);
        earthSphere.setMaterial(material);
        //getCurrentScene().addChild(earthSphere);

        Material simpleMaterial = new Material();
        simpleMaterial.setColor(Color.GREEN);
        cube = new Cube(1);
        cube.setMaterial(simpleMaterial);
        cube.setDrawingMode(GLES20.GL_LINE_STRIP);
        getCurrentScene().addChild(cube);

        Line3D grid = new Line3D(generateGrid(4), 1);
        grid.setMaterial(simpleMaterial);
        grid.setZ(-2f);
        grid.setX(-2f);
        grid.setDrawingMode(GLES20.GL_LINES);
        getCurrentScene().addChild(grid);

        cameraCoords = new Vector3();
        cameraCoords.setAll(0f, 3f, 5.2f);
        getCurrentCamera().setPosition(cameraCoords);
        getCurrentCamera().setLookAt(0.0f, 0.0f, -4.0f);
        mCamera = getCurrentCamera();
        xStartPos = (float) mCamera.getX();
        xPos = xStartPos;
    }

    private Stack<Vector3> generateGrid(float w) {
        float gridWidth = 0.2f;
        Stack<Vector3> points = new Stack<>();
        for (float i = 0; i <= w; i ++) {
            points.add(new Vector3(i * gridWidth, 0, 0));
            points.add(new Vector3(i * gridWidth + gridWidth, 0, 0));
            points.add(new Vector3(i * gridWidth, 0, 0));
            points.add(new Vector3(i * gridWidth, 0, 0 + gridWidth * w));
        }
        for (float j = 0; j <= w; j++) {
            points.add(new Vector3(0, 0, j * gridWidth));
            points.add(new Vector3(0 + gridWidth * w, 0, j * gridWidth));
            points.add(new Vector3(0, 0, j * gridWidth));
            points.add(new Vector3(0, 0, j * gridWidth + gridWidth));
        }
        return points;
    }

    @Override
    protected void onRender(long ellapsedRealtime, double deltaTime) {
        super.onRender(ellapsedRealtime, deltaTime);
        cube.rotate(Vector3.Axis.X, 1.0);
        cube.rotate(Vector3.Axis.Y, 0.5);
        cube.setPosition(cube.getPosition().x, cube.getPosition().y, cube.getPosition().z - 0.01f);
    }

    @Override
    public void onOffsetsChanged(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset) {

    }


    @Override
    public void onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //pointer positions
            xStartPos = event.getX();
            xPos = xStartPos;
            yStartPos = event.getY();
            yPos = xStartPos;
            cLookLastX = xStartPos;
            cLookLastY = yStartPos;

            Log.d(TAG, "Renderer down");
        }
        Vector3 cLookAt = getCurrentCamera().getLookAt();

        float xd;
        float yd;
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            xPos = event.getX();
            yPos = event.getY();
            if (Math.abs(xPos - cLookLastX) > 10) {
                xd = (xStartPos - event.getX()) / 10000f;
                cLookLastX = event.getX();
            } else {
                xd = 0;
            }
            if (Math.abs(yPos - cLookLastY) > 10) {
                yd = (yStartPos - event.getY()) / 10000f;
                cLookLastY = event.getY();
            } else {
                yd = 0;
            }
            lookAt(cLookAt.x + xd, cLookAt.y - yd, cLookAt.z);
        }
    }

    public void lookAt(double x, double y, double z) {
        getCurrentCamera().setLookAt(new Vector3(x, y, z));
    }

    public void rotCamera(float touchOffset) {
        xOffset = touchOffset;
        if (xOffset > 360) xOffset -= 360;

        float x = (float) (5 * Math.sin(Math.toRadians(xOffset)));
        float z = (float) (5 * Math.cos(Math.toRadians(xOffset)));

        mCamera.setPosition(x, cameraCoords.y, z);
    }


}
