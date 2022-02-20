package com.oceanmtech.documentshare.Database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tblDocuments")
public class DocumentEntity {

    @PrimaryKey()
    @ColumnInfo(name = "id")
    public Integer id;

    @ColumnInfo(name = "memberId")
    public String MemberId;

    @ColumnInfo(name = "name")
    public String Name;

    @ColumnInfo(name = "imagePath")
    public String ImagePath;

    @ColumnInfo(name = "documentType")
    public String DocumentType;

    @Override
    public String toString() {
        return "DocumentEntity{" +
                "id=" + id +
                ", MemberId='" + MemberId + '\'' +
                ", Name='" + Name + '\'' +
                ", ImagePath='" + ImagePath + '\'' +
                ", documentType='" + DocumentType + '\'' +
                '}';
    }
}
