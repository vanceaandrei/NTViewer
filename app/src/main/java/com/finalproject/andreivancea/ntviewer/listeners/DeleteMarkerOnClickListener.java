package com.finalproject.andreivancea.ntviewer.listeners;

import android.view.View;

/**
 * Created by andrei.vancea on 2/7/2017.
 */

public class DeleteMarkerOnClickListener implements View.OnClickListener {

    private MarkerDeleteButtonOnClickListener mListener;
    int position;

    public DeleteMarkerOnClickListener(MarkerDeleteButtonOnClickListener mListener, int position) {
        this.mListener = mListener;
        this.position = position;
    }

    @Override
    public void onClick(View v) {
        mListener.deleteMarkerButtonClicked(position);
    }

    public interface MarkerDeleteButtonOnClickListener {
        void deleteMarkerButtonClicked(int position);
    }

}
