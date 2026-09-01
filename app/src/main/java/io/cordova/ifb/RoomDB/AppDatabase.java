package io.cordova.ifb.RoomDB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import io.cordova.ifb.Location.LocationModel;

@Database(entities = {LocationModel.class},version = 1)
abstract public class AppDatabase extends RoomDatabase {
    public abstract LocationDao LocationDao();
    public static AppDatabase INSTANCE;

    public static AppDatabase getDatabaseInstance(Context context){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class,
                            "App_Database")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}
