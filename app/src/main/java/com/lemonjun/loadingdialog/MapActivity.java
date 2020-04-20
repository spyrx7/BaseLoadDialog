package com.lemonjun.loadingdialog;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.tencentmap.mapsdk.map.MapView;
import com.tencent.tencentmap.mapsdk.map.TencentMap;

public class MapActivity extends AppCompatActivity implements TencentLocationListener {

    private MapView mapView;
    private TencentMap tencentMap;

    private TencentLocationManager tencentLocationManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mapView = findViewById(R.id.map_view);

        tencentMap = mapView.getMap();
        tencentMap.setZoom(9);

        tencentLocationManager = TencentLocationManager.getInstance(this);
        TencentLocationRequest request = TencentLocationRequest.create();

        tencentLocationManager.requestLocationUpdates(request,this);
    }

    @Override
    public void onLocationChanged(TencentLocation tencentLocation, int i, String s) {
        Log.e("tt","tencentLocation = " + tencentLocation.getAddress());
        LatLng center = new LatLng(tencentLocation.getLatitude()
                ,tencentLocation.getLongitude());
        tencentMap.setCenter(center);
        tencentMap.setZoom(12);


    }

    @Override
    public void onStatusUpdate(String s, int arg1, String s1) {
        String desc = "";
        switch (arg1) {
            case STATUS_DENIED:
                desc = "权限被禁止";
                break;
            case STATUS_DISABLED:
                desc = "模块关闭";
                break;
            case STATUS_ENABLED:
                desc = "模块开启";
                break;
            case STATUS_GPS_AVAILABLE:
                desc = "GPS可用，代表GPS开关打开，且搜星定位成功";
                break;
            case STATUS_GPS_UNAVAILABLE:
                desc = "GPS不可用，可能 gps 权限被禁止或无法成功搜星";
                break;
            case STATUS_LOCATION_SWITCH_OFF:
                desc = "位置信息开关关闭，在android M系统中，此时禁止进行wifi扫描";
                break;
            case STATUS_UNKNOWN:
                break;
        }
        Log.e("location", "location status:" + s + ", " + s1 + " " + desc);
    }
}
