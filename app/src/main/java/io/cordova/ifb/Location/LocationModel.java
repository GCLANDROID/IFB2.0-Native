package io.cordova.ifb.Location;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "Location_Table")
public class LocationModel {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "Date")
    public String date;
    @ColumnInfo(name = "Longitude")
    public String longitude;
    @ColumnInfo(name = "Latitude")
    public String latitude;

    public LocationModel(int id,String date,String longitude, String latitude) {
        this.id = id;
        this.date = date;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
