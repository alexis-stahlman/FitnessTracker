package com.example.fitnesstrackerproto;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class MapViewItemizedOverlay extends ItemizedOverlay {

    private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
    public MapViewItemizedOverlay(Drawable defaultMarker, Context context) {
        super(boundCenterBottom(defaultMarker));
    }

    @Override
    protected OverlayItem createItem(int i) {
        return mOverlays.get(i);
    }


    @Override
    public int size() {
        // TODO Auto-generated method stub
        return mOverlays.size();
    }

    public void addOverlay(OverlayItem overlay) {
        mOverlays.add(overlay);
        populate();
    }


    @Override
    public boolean onTap(int index)
    {   
        //do anything when the overlay tapped
    return true;
    }
}

