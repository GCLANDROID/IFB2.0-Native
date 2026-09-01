package io.cordova.ifb.RoomDB;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.cordova.ifb.Location.LocationModel;

@Dao
public interface LocationDao {
    @Query("SELECT * FROM Location_Table")
    List<LocationModel> getAllLocation();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(LocationModel locationModel);

    @Query("SELECT COUNT(*) FROM Location_Table")
    int getItemCount();

    @Query("DELETE FROM Location_Table")
    void getDeleteAllRecorde();
}
