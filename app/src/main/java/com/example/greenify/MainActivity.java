package com.example.greenify;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    GoogleMap mMap;
    Double mLatitude, mLongitude;
    LatLng mLatLng;
    private int i;
    List<pollutionParams> AQItable= new ArrayList<pollutionParams>();
    Marker mGoogleMarker;
    AQIdata maqiData;
    PieChart pieChartPollution;
    TextView lblAQI,LblPollution,LblTrees;
    LinearLayout mapParent, treeParent;
    HashMap<String,Double> permissibleVal = new HashMap<String,Double>();
    Double cmpPercent=0.80;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment=(SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        else {
            Toast.makeText(this, "Error - Map Fragment was null!!", Toast.LENGTH_SHORT).show();
        }
     //   View view =  inflater.inflate(R.layout.fragment_home, container,false);
        lblAQI=this.findViewById(R.id.lblAQI);

        pieChartPollution=this.findViewById(R.id.pieChartPollution);
        pieChartPollution.setRotationEnabled(false);
        pieChartPollution.setDescription(new Description());
        pieChartPollution.setTransparentCircleAlpha(0);
        pieChartPollution.setCenterText("Pollutants conc.");
        pieChartPollution.setCenterTextSize(15);
        pieChartPollution.setDrawRoundedSlices(false);
        pieChartPollution.setDrawSlicesUnderHole(false);
        pieChartPollution.setHoleRadius(60.0f);
        // pieMakewise.setDrawHoleEnabled(false);
        pieChartPollution.setFocusableInTouchMode(true);
        pieChartPollution.setEntryLabelColor(Color.WHITE);
        pieChartPollution.setHighlightPerTapEnabled(false);
        pieChartPollution.setUsePercentValues(false);
        pieChartPollution.getLegend().setEnabled(false);
        pieChartPollution.getDescription().setEnabled(false);
        pieChartPollution.setNoDataText("No Data");
        pieChartPollution.setNoDataTextColor(Color.RED);

        maqiData = new AQIdata(this );
        AQItable = maqiData.getAllRecords();

        mapParent = (LinearLayout) this.findViewById(R.id.mapParent);
        treeParent = (LinearLayout) this.findViewById(R.id.treeParent);
        treeParent.setVisibility(View.GONE);
        LblPollution = this.findViewById(R.id.LblPollution);
        LblTrees=this.findViewById((R.id.LblTrees));
            //Toast.makeText(this, var1, Toast.LENGTH_SHORT).show();
         //mapFragment.getMapAsync(this);

        //PM2_5	PM_10	NO	NO2	Nox	NH3	CO	SO2	O3	Benzene
        //60	100	40	80	50	400	2	80	180	5

        permissibleVal.put("PM2_5",60.0);
        permissibleVal.put("PM_10",100.0);
        permissibleVal.put("NO",40.0);
        permissibleVal.put("NO2",80.0);
        permissibleVal.put("Nox",50.0);
        permissibleVal.put("NH3",400.0);
        permissibleVal.put("CO",2.0);
        permissibleVal.put("SO2",80.0);
        permissibleVal.put("O3",180.0);
        permissibleVal.put("Benzene",5.0);

        final FloatingActionButton fab = (FloatingActionButton) this.findViewById(R.id.toggleButton);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mapParent.isShown()){
                    mapParent.setVisibility(View.GONE);
                    fab.setImageDrawable(getResources().getDrawable( R.drawable.icon_map));
                    treeParent.setVisibility(View.VISIBLE);
                }
                else {
                    mapParent.setVisibility(View.VISIBLE);
                    fab.setImageDrawable(getResources().getDrawable( R.drawable.icon_trees));
                    treeParent.setVisibility(View.GONE);
                }
            }
        });


    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setRotateGesturesEnabled(true);
        LatLng mLatLong = new LatLng(12.99886826, 80.19156795);
       // googleMap.addMarker(new MarkerOptions()
              //  .position(mLatLong)
             //   .title("Chennai")
       // );

       // mMap=googleMap;
        googleMap.setOnMarkerClickListener(this);
        if(googleMap!=null)
        {
            for(int i = 0; i <AQItable.toArray().length; i++) {
                mLatitude=AQItable.get(i).getLatitude();
                if (i==1)
                    Toast.makeText(this,String.valueOf(mLatitude),Toast.LENGTH_LONG).show();
                mLongitude=AQItable.get(i).getLongitude();
                mLatLng=new LatLng(mLatitude,mLongitude);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(mLatLng)
                        .title(AQItable.get(i).getCity())
                        .snippet(AQItable.get(i).getstationname());
                      //  .icon((BitmapDescriptorFactory.defaultMarker(219)));
                mGoogleMarker=googleMap.addMarker(markerOptions);

                 //mGoogleMarker.setTag(i);
                    //mGoogleMarker.showInfoWindow();
            }
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mLatLong, 5));
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        pieChartPollution.clear();
        pollutionParams pDisp = null;
        pDisp = maqiData.getParams(marker.getPosition().latitude, marker.getPosition().longitude);
        //Toast.makeText(this, String.valueOf(pDisp.getPM2_5()), Toast.LENGTH_SHORT).show();
        setTitle("ProAir " + pDisp.getCity()+ "-" + pDisp.getstationname());
        lblAQI.setText("AQI : "+String.valueOf(String.format("%.2f", pDisp.getAQI())));
        int indexCtr=0;
        ArrayList<PieEntry> pollutionLevel=new ArrayList<PieEntry>();
        ArrayList<Integer> colors = new ArrayList<>();

        if (pDisp.getPM2_5()>0) {
            pollutionLevel.add(0, new PieEntry(pDisp.getPM2_5().floatValue() ));
            colors.add(Color.parseColor("#FFA500"));    //PM2_5
            indexCtr++;
        }
        if (pDisp.getPM_10()>0) {
            pollutionLevel.add(0, new PieEntry(pDisp.getPM_10().floatValue() ));
            colors.add(Color.parseColor("#0c7cba"));    //PM_10
            indexCtr++;
        }
        if (pDisp.getNO()>0) {
            pollutionLevel.add(0, new PieEntry(pDisp.getNO().floatValue() ));
            colors.add(Color.parseColor("#000000"));    //NO
            indexCtr++;
        }
        if (pDisp.getNO2()>0) {
            pollutionLevel.add(0, new PieEntry(pDisp.getNO2().floatValue() ));
            colors.add(Color.parseColor("#FF0000"));    //No2
            indexCtr++;
        }
        if (pDisp.getNox()>0) {
            pollutionLevel.add(0, new PieEntry(pDisp.getNox().floatValue() ));
            colors.add(Color.parseColor("#00FF00"));    //NOx
            indexCtr++;
        }
        if (pDisp.getNH3()>0) {
            pollutionLevel.add(0, new PieEntry(pDisp.getNH3().floatValue() ));
            colors.add(Color.parseColor("#0000FF"));    //NH3
            indexCtr++;
        }
        if (pDisp.getCO()>0) {
            pollutionLevel.add(0, new PieEntry(pDisp.getCO().floatValue() ));
            colors.add(Color.parseColor("#FFFF00"));    //CO
            indexCtr++;
        }
        if (pDisp.getSO2()>0) {
            pollutionLevel.add(0, new PieEntry(pDisp.getSO2().floatValue() ));
            colors.add(Color.parseColor("#00FFFF"));    //SO2
            indexCtr++;
        }
        if (pDisp.getO3()>0) {
            pollutionLevel.add(0, new PieEntry(pDisp.getO3().floatValue() ));
            colors.add(Color.parseColor("#FF00FF"));    //O3
            indexCtr++;
        }
        if (pDisp.getBenzene()>0) {
            pollutionLevel.add(0, new PieEntry(pDisp.getBenzene().floatValue() ));
            colors.add(Color.parseColor("#AAAAAA"));    //Benzene
            indexCtr++;
        }

        String comments,commentsTrees= "";
        String pollutants = "",trees="";
        if(pDisp.getNox()>cmpPercent* permissibleVal.get("Nox"))
        {
            pollutants += "Nox, ";
            trees +="Flamingo Lily, Bamboo palm, Parlour Palm, Lady Palm, Variegated Snake plant, Heartleaf philodendron, ";
        }
        if(pDisp.getNO()>cmpPercent* permissibleVal.get("NO"))
        {
            pollutants += "NO, ";
            trees+="Boston fern, Kimberley queen fern, Spider plant, Devil's Ivy, Peace Lily, ";
        }
        if(pDisp.getNO2()>cmpPercent* permissibleVal.get("NO2"))
        {
            pollutants += "NO2, ";
            trees +="Silver Maple , Yellow Poplar , English Ivy, Dwarf date palm, Areca palm, ";
        }
        if(pDisp.getNH3()>cmpPercent* permissibleVal.get("NH3"))
        {
            pollutants += "NH3, ";
            trees += "Eucalyptus , Beech , Laurel Oak , London Plane , Red Mulberry, ";
        }
        if(pDisp.getPM2_5()>cmpPercent* permissibleVal.get("PM2_5"))
        {
            pollutants += "PM2_5, ";
            trees += "peepal, saptaparni, jamun, devdar, champa, Walnut plant, Holly Oak,  ";
        }
        if(pDisp.getPM_10()>cmpPercent* permissibleVal.get("PM_10"))
        {
            pollutants += "PM_10, ";
            trees += "red cedar, fig, Himalayan cherry, Neem, Gulmohar, cassia, banyan, ";
        }
        if(pDisp.getCO()>cmpPercent* permissibleVal.get("CO"))
        {
            pollutants += "CO, ";
            trees +="Selloum philodendron, Elephant ear philodendron, Red-edged dracaena, Comstalk dracaena, Weeping fig, Barberton daisy, ";
        }
        if(pDisp.getSO2()>cmpPercent* permissibleVal.get("SO2"))
        {
            pollutants += "SO2, ";
            trees +="Florist chrysanthemum, Rubber plant, Dendrobium orchids, Dumb canes, King of hearts, Moth orchids, ";
        }
        if(pDisp.getO3()>cmpPercent* permissibleVal.get("O3"))
        {
            pollutants += "O3, ";
            trees +="Aloe vea, Janet Craig, Warneckei, Banana, Golden Pothos, Scarlet Star Bromeliad, ";
        }
        if(pDisp.getBenzene()>cmpPercent* permissibleVal.get("Benzene"))
        {
            pollutants += "Benzene, ";
            trees+="Corn Plant, Broadleaf Lady Palm, Dragon Tree, ";
        }
        /*pollutants = pollutants.substring(0,pollutants.length()-2);
        trees = trees.substring(0,trees.length()-2);*/

        if(pollutants!="")
        {
            comments = "The city has croseed has reached atleast "+String.valueOf(cmpPercent)+ " of the permissible limits of the following pollutants : \n\n"+pollutants;
            commentsTrees = "The trees that can be grown to absorb these harmful pollutants are :\n\n"+trees;
        }
        else
        {
            comments = "Keep it up! Your city has been doing good recently";
        }
        LblPollution.setText(comments);
        LblTrees.setText(commentsTrees);

        // Setting values for pieChart
        PieDataSet dataSet = new PieDataSet(pollutionLevel, "");
        ValueFormatter formatter=new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return ((int) value)+"";
            }
        };
        dataSet.setValueFormatter(formatter);
        PieData data = new PieData(dataSet);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(13);




        //  colors.add(Color.parseColor("#F2F436")); // Nortel
        //  colors.add(Color.parseColor("#FC5A01")); // Motorola
        dataSet.setColors(colors);

        pieChartPollution.setData(data);
        pieChartPollution.invalidate();

        return false;
    }
}


