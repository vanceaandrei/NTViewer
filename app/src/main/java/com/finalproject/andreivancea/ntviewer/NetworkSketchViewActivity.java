package com.finalproject.andreivancea.ntviewer;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.finalproject.andreivancea.ntviewer.graphics.SceneRenderer;

import org.rajawali3d.surface.IRajawaliSurface;
import org.rajawali3d.surface.RajawaliSurfaceView;

public class NetworkSketchViewActivity extends AppCompatActivity implements View.OnTouchListener {

    private static final String TAG = "SketchViewActivity";
    SceneRenderer renderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_sketch_view);

        final RajawaliSurfaceView surface = new RajawaliSurfaceView(this);
        surface.setFrameRate(60.0);
        surface.setRenderMode(IRajawaliSurface.RENDERMODE_WHEN_DIRTY);
        surface.setOnTouchListener(this);

        // Add mSurface to your root view
        addContentView(surface, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT));

        renderer = new SceneRenderer(this);
        surface.setSurfaceRenderer(renderer);


    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        renderer.onTouchEvent(event);
        return true;
    }


}
