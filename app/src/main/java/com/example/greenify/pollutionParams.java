package com.example.greenify;

public class pollutionParams {

        Integer Station_Id;
        String stationname;
        String State;
        String City;
        Double PM2_5;
        Double PM_10;
        Double NO;
        Double NO2;
        Double Nox;
        Double NH3;
        Double CO;
        Double SO2;
        Double O3;
        Double Benzene;
        Double Latitude;
        Double Longitude;
        Double AQI;

        public pollutionParams(Integer Station_Id, String stationname, String State, String City, Double PM2_5, Double PM_10, Double NO, Double NO2, Double Nox, Double NH3, Double CO, Double SO2, Double O3, Double Benzene, Double Latitude, Double Longitude, Double AQI){
            this.Station_Id = Station_Id;
            this.stationname = stationname;
            this.State = State;
            this.City = City ;
            this.PM2_5 = PM2_5;
            this.PM_10 = PM_10 ;
            this.NO = NO;
            this.NO2 = NO2 ;
            this.Nox = Nox;
            this.NH3 =NH3 ;
            this.CO = CO;
            this.SO2 =SO2 ;
            this.O3 = O3;
            this.Benzene = Benzene;
            this. Latitude=Latitude ;
            this.Longitude =Longitude ;
            this.AQI = AQI;
        }

        public Integer getStation_Id(){return Station_Id;}
        public String getstationname(){return stationname;}
        public String getState(){return State;}
        public String getCity(){return City;}
        public Double getPM2_5(){return PM2_5;}
        public Double getPM_10(){return PM_10;}
        public Double getNO(){return NO;}
        public Double getNO2(){return NO2;}
        public Double getNox(){return Nox;}
        public Double getNH3(){return NH3;}
        public Double getCO(){return CO;}
        public Double getSO2(){return SO2;}
        public Double getO3(){return O3;}
        public Double getBenzene(){return Benzene;}
        public Double getLatitude(){return Latitude;}
        public Double getLongitude(){return Longitude;}
        public Double getAQI(){return AQI;}

}


