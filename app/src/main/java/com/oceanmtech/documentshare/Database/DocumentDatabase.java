package com.oceanmtech.documentshare.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

    @Database(entities = DocumentEntity.class, version = 1)
    public abstract class DocumentDatabase extends RoomDatabase {
        public abstract DocumentDAO documentDao();

        public static DocumentDatabase getInstance(Context context) {
            return Room.databaseBuilder(context, DocumentDatabase.class, "dbDocument")
                    .allowMainThreadQueries()
    //                .addMigrations(MIGRATION_1_2)
                    .build();
        }

        static final Migration MIGRATION_1_2 = new Migration(1, 2) {
            @Override
            public void migrate(SupportSQLiteDatabase database) {
                //database.execSQL("CREATE TABLE `tblDocuments2` (`MemberId` String, `Name` String, `ImagePath` String, _id INTEGER PRIMARY KEY)");
                database.execSQL("CREATE TABLE tblDocuments (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, memberId TEXT, name TEXT, imagePath TEXT, documentType TEXT)");;
            }
        };
    }
