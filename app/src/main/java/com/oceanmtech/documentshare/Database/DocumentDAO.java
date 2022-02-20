package com.oceanmtech.documentshare.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DocumentDAO {

    @Insert
    void insertData(DocumentEntity documentEntity);

    @Update
    void updateEntity(DocumentEntity documentEntity);
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    // return primary key as Long
//    public void insertData(DocumentEntity documentEntity);

//    @Query("UPDATE tblDocuments SET imagePath = :path WHERE id =:id")
//    void update(String path, Integer id);

//    @Query("UPDATE tblDocuments SET documentType = :documentType WHERE id =:id")
//    void updateDocumentTypeInDatabase(String documentType, Integer id);

    @Query("SELECT * FROM tblDocuments where id =:id")
    DocumentEntity checkItemIfExists(int id);
//    @Update
//    void updateQty(DocumentEntity documentEntity);

    @Query("SELECT * FROM tblDocuments")
    List<DocumentEntity> getDataFromDocuments();

    @Query("SELECT * FROM tblDocuments where memberId = :MemberId")
    DocumentEntity getSelectedRow(Integer MemberId);

//    @Query("SELECT * FROM tblDocuments2 where MemberId =:id")
//    DocumentEntity checkMemberIdIfExists(String id);
}
