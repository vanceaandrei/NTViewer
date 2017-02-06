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

    private Vector3 cLookAt;
    private Vector3 cameraCoords;

    private Line3D cross;

    public SceneRenderer(Context context) {
        super(context);
        this.context = context;
        setFrameRate(60);
    }

    @Override
    protected void initScene() {

        //------LIGHT------
        directionalLight = new DirectionalLight(1.0f, 0.2f, -1.0f);
        directionalLight.setColor(1.0f, 1.0f, 1.0f);
        directionalLight.setPower(2.0f);
        getCurrentScene().addLight(directionalLight);
        //-----------------

        //------CAMERA-----
        cameraCoords = new Vector3(0f, 3f, 5.2f);
        getCurrentCamera().setPosition(cameraCoords);
        getCurrentCamera().setLookAt(0.0f, 0.0f, -4.0f);
        mCamera = getCurrentCamera();
        cLookAt = getCurrentCamera().getLookAt();
        //------------------

        //------MATERIALS---
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
        Material greenMaterial = new Material();
        greenMaterial.setColor(Color.GREEN);

        Material blueMaterial = new Material();
        blueMaterial.setColor(Color.BLUE);

        //-------------------

        //------OBJECTS-----
        earthSphere = new Sphere(1, 24, 24);
        earthSphere.setMaterial(material);
        earthSphere.setPosition(0, 0, -8f);
        getCurrentScene().addChild(earthSphere);

        cube = new Cube(1);
        cube.setMaterial(greenMaterial);
        cube.setDrawingMode(GLES20.GL_LINE_STRIP);
        cube.setPosition(2, 0, -4f);
        getCurrentScene().addChild(cube);

        Line3D grid = new Line3D(generateGrid(1, 12), 1);
        grid.setMaterial(greenMaterial);
        grid.setPosition(0, 0, 0);
        grid.setDrawingMode(GLES20.GL_LINES);
        getCurrentScene().addChild(grid);

        cross = drawCross(cLookAt, greenMaterial);

        drawCube(new Vector3(0, 0, 15f), greenMaterial);
        drawCube(new Vector3(0, 4f, 15f), blueMaterial);
        //-----------------
    }

    private Stack<Vector3> generateGrid(float w, float size) {

        Stack<Vector3> points = new Stack<>();
        for (float i = 0; i <= w; i += w / size) {
            points.add(new Vector3(i, 0, 0));
            points.add(new Vector3(i, 0, w));
            points.add(new Vector3(0, 0, i));
            points.add(new Vector3(w, 0, i));
        }
        return points;
    }

    @Override
    protected void onRender(long ellapsedRealtime, double deltaTime) {
        super.onRender(ellapsedRealtime, deltaTime);
        cube.rotate(Vector3.Axis.X, 1.0);
        cube.rotate(Vector3.Axis.Y, 0.5);
        //cube.setPosition(cube.getPosition().x, cube.getPosition().y, cube.getPosition().z - 0.01f);
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
            yPos = yStartPos;
            cLookLastX = xStartPos;
            cLookLastY = yStartPos;
        }

        float xd;
        float yd;
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            xPos = event.getX();
            yPos = event.getY();
            if (Math.abs(xPos - cLookLastX) > 5) {
                xd = (xStartPos - event.getX()) / 10000f;
                cLookLastX = event.getX();
            } else {
                xd = 0;
            }
            if (Math.abs(yPos - cLookLastY) > 5) {
                yd = (yStartPos - event.getY()) / 10000f;
                cLookLastY = event.getY();
            } else {
                yd = 0;
            }

            if (xd > 360) xd -= 360;
            if (yd > 360) yd -= 360;
            rotateCamera(xd, yd);
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void rotateCamera(float dy, float dx) {
        cross.setPosition(cLookAt);
        cLookAt.x -= mCamera.getX();
        cLookAt.y -= mCamera.getY();
        cLookAt.z -= mCamera.getZ();

        cLookAt.rotateY(-dy);
        if (cLookAt.z < mCamera.getZ()) {
            cLookAt.rotateX(-dx);
        } else {
            cLookAt.rotateX(dx);
        }
        cLookAt.x += mCamera.getX();
        cLookAt.y += mCamera.getY();
        cLookAt.z += mCamera.getZ();
        getCurrentCamera().setLookAt(cLookAt);
    }

    private Line3D drawCross(Vector3 position, Material material) {
        Stack<Vector3> points = new Stack<>();
        points.add(new Vector3(-0.1f, 0, 0));
        points.add(new Vector3(0.1f, 0, 0));
        points.add(new Vector3(0, -0.1f, 0));
        points.add(new Vector3(0, 0.1f, 0));
        points.add(new Vector3(0, 0, -0.1f));
        points.add(new Vector3(0, 0, 0.1f));

        Line3D cross = new Line3D(points, 1);
        cross.setMaterial(material);
        cross.setDrawingMode(GLES20.GL_LINES);
        cross.setPosition(position);
        getCurrentScene().addChild(cross);
        return cross;
    }

    private Cube drawCube(Vector3 position, Material material) {
        Cube cube = new Cube(1);
        cube.setMaterial(material);
        cube.setPosition(position);
        getCurrentScene().addChild(cube);
        return cube;
    }

}
