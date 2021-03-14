package com.example.greenify;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class AQIdata extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "AQI.db";
    private Context con;

    public AQIdata(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        con = context;
    }
    public pollutionParams getParams(Double latitude, Double longitude)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        pollutionParams p=null;
        Integer Station_Id=0;
        String stationname="",State="",City="";
        Double PM2_5, PM_10, NO, NO2, Nox, NH3, CO, SO2, O3, Benzene, Latitude, Longitude,AQI;
        Cursor cursor=null;
        try {
            cursor = db.rawQuery("select * from stations_AQI where latitude=? and longitude=?", new String[]{String.valueOf(latitude), String.valueOf(longitude)});
            if (cursor.getCount() > 0) {
               cursor.moveToFirst();
                Station_Id = cursor.getInt (0);
                stationname = cursor.getString(1);
                State = cursor.getString(2);
                City = cursor.getString(3);
                PM2_5 = cursor.getDouble(4);
                PM_10 = cursor.getDouble(5);
                NO = cursor.getDouble(6);
                NO2 = cursor.getDouble(7);
                Nox = cursor.getDouble(8);
                NH3 = cursor.getDouble(9);
                CO = cursor.getDouble(10);
                SO2 = cursor.getDouble(11);
                O3 = cursor.getDouble(12);
                Benzene = cursor.getDouble(13);
                Latitude = cursor.getDouble(14);
                Longitude = cursor.getDouble(15);
                AQI = cursor.getDouble(16);
                p = new pollutionParams(Station_Id,stationname, State,City,PM2_5, PM_10, NO, NO2, Nox, NH3, CO, SO2, O3, Benzene, Latitude, Longitude,AQI);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally {
            cursor.close();
            db.close();
        }
        return p;
    }
    public List<pollutionParams> getAllRecords()
    //public String getAllRecords()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        List pollutionParamsList = new ArrayList<pollutionParams>();
        Integer Station_Id=0;
        String stationname="",State="",City="";
        Double PM2_5, PM_10, NO, NO2, Nox, NH3, CO, SO2, O3, Benzene, Latitude, Longitude;

        Cursor cursor=null;
        try {
            cursor = db.rawQuery("select * from stations_AQI", null);
            if (cursor.getCount() > 0) {

                cursor.moveToFirst();
               do{
                   Station_Id = cursor.getInt (0);
                   stationname = cursor.getString(1);
                   State = cursor.getString(2);
                   City = cursor.getString(3);
                   PM2_5 = cursor.getDouble(4);
                   PM_10 = cursor.getDouble(5);
                   NO = cursor.getDouble(6);
                   NO2 = cursor.getDouble(7);
                   Nox = cursor.getDouble(8);
                   NH3 = cursor.getDouble(9);
                   CO = cursor.getDouble(10);
                   SO2 = cursor.getDouble(11);
                   O3 = cursor.getDouble(12);
                   Benzene = cursor.getDouble(13);
                   Latitude = cursor.getDouble(14);
                   Longitude = cursor.getDouble(15);
                   pollutionParamsList.add(new pollutionParams(Station_Id,stationname, State,City,PM2_5, PM_10, NO, NO2, Nox, NH3, CO, SO2, O3, Benzene, Latitude, Longitude,null));


               } while (cursor.moveToNext());
            }
            else
            {
               pollutionParamsList.add(new pollutionParams(0,"","","",0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally {
            cursor.close();
            db.close();
        }
        return pollutionParamsList;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
