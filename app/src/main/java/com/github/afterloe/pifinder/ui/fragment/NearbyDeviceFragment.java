package com.github.afterloe.pifinder.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.esri.arcgisruntime.geometry.CoordinateFormatter;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.github.afterloe.pifinder.R;

import java.io.Serializable;

/**
 * 附近设备
 */
public class NearbyDeviceFragment extends Fragment implements Serializable {

    private MapView mapView;


    public static NearbyDeviceFragment newInstance() {
        return new NearbyDeviceFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nearby_device, container, false);
        mapView = view.findViewById(R.id.gis_map);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ArcGISMap map = new ArcGISMap(Basemap.Type.STREETS_WITH_RELIEF_VECTOR, 26.09049903663615, 119.30582982722777, 19);
        Point pointGeometry0 = CoordinateFormatter.fromLatitudeLongitude("26.09049904163615N 119.30582962722777E", null);
        Graphic pointGraphic1 = new Graphic(pointGeometry0);
        GraphicsOverlay pointGraphicOverlay = new GraphicsOverlay();
        // create simple renderer

        // add graphic to overlay
        pointGraphicOverlay.getGraphics().add(pointGraphic1);
        mapView.getGraphicsOverlays().add(pointGraphicOverlay);
        mapView.setMap(map);
    }

    @Override
    public void onPause() {
        mapView.pause();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.resume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
