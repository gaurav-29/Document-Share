package com.oceanmtech.documentshare.MainModule;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.oceanmtech.documentshare.Adapters.MainActivity2Adapter;
import com.oceanmtech.documentshare.Database.DocumentDAO;
import com.oceanmtech.documentshare.Database.DocumentDatabase;
import com.oceanmtech.documentshare.Database.DocumentEntity;
import com.oceanmtech.documentshare.R;
import com.oceanmtech.documentshare.Utils.Constants;
import com.oceanmtech.documentshare.Utils.FileUtils;
import com.oceanmtech.documentshare.Utils.PreferenceHelper;
import com.oceanmtech.documentshare.databinding.ActivityMain2Binding;

import java.util.ArrayList;
import java.util.List;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;

public class MainActivity2 extends AppCompatActivity implements MainActivity2Adapter.OnClick {

    MainActivity2 mContext = MainActivity2.this;
    ActivityMain2Binding mBinding;
    String MemberId;
    DocumentDAO documentDAO;
    DocumentEntity mData = new DocumentEntity();
    MainActivity2Adapter mAdapter;
    int Position, Id;
    FileUtils fileUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(mContext, R.layout.activity_main2);
        documentDAO = DocumentDatabase.getInstance(mContext).documentDao();
        fileUtils = new FileUtils(mContext);
        mBinding.toolbar.ivLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        PreferenceHelper.putString(Constants.ID, "12345");
        PreferenceHelper.putString(Constants.PASSWORD, "123456");
        showDataInRecyclerView();
        onClickListeners();
    }

    private void showDataInRecyclerView() {
        mBinding.rvData.setLayoutManager(new GridLayoutManager(mContext, 1));
        mAdapter = new MainActivity2Adapter(documentDAO.getDataFromDocuments(), mContext, MainActivity2.this);
        mBinding.rvData.setAdapter(mAdapter);
        mBinding.rvData.getLayoutManager().scrollToPosition(documentDAO.getDataFromDocuments().size() - 1);
    }

    private void onClickListeners() {
        mBinding.etMemberId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mBinding.ivUpload.setImageResource(R.drawable.ic_send);
                if(mBinding.etMemberId.getText().toString().equalsIgnoreCase(""))
                    mBinding.ivUpload.setImageResource(R.drawable.ic_upload);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mBinding.ivUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MemberId = mBinding.etMemberId.getText().toString();
                if (!MemberId.equalsIgnoreCase(PreferenceHelper.getString(Constants.ID, ""))) {
                    Snackbar.make(mBinding.getRoot(), "Please enter valid Member ID", Snackbar.LENGTH_SHORT).show();
                } else {
                    mData.ImagePath = "";
                    mData.MemberId = MemberId;
                    mData.Name = "Gaurav Rathod";
                    documentDAO.insertData(mData);
                    showDataInRecyclerView();
                    mBinding.etMemberId.setText("");
                    mBinding.ivUpload.setImageResource(R.drawable.ic_upload);
                }
            }
        });
    }

    @Override
    public void OnClickMethod(int position, int id) {
        Position = position;
        Id = id;
        Dexter.withContext(mContext)
                .withPermissions(CAMERA, READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {
                            if (SDK_INT >= android.os.Build.VERSION_CODES.R) {
                                if (!Environment.isExternalStorageManager()) {
                                    Intent intent = null;
                                    intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                                    intent.addCategory("android.intent.category.DEFAULT");
                                    intent.setData(Uri.parse(String.format("package:%s", getPackageName())));
                                    startActivity(intent);
                                } else {
                                    imagePicker();
                                }
                            } else {
                                imagePicker();
                            }
                        }
                        if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                            showRationaleDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    @Override
    public void updateDocumentType(String documentType, int position) {
        DocumentEntity documentEntity = documentDAO.checkItemIfExists(Id);
        if (documentEntity != null) {
            documentEntity.DocumentType = documentType;
            documentDAO.updateEntity(documentEntity);
            Snackbar.make(mBinding.getRoot(), "Document uploaded successfully", Snackbar.LENGTH_SHORT).show();
        }
        mAdapter.dataList.get(position).DocumentType = documentType;
        mAdapter.notifyItemChanged(position);
        Log.d("LIST", String.valueOf(documentDAO.getDataFromDocuments()));
    }

    private void imagePicker() {
        FilePickerBuilder.getInstance()
                .setMaxCount(1) //optional
                .setActivityTheme(R.style.LibAppTheme) //optional
                .pickPhoto(this, 1);
    }

    private void showRationaleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Grant Permission");
        builder.setMessage("Permission is required to access images, files, audios & videos from this device");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

    ArrayList<Uri> photoPaths = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            photoPaths = new ArrayList<>();
            photoPaths.addAll(data.getParcelableArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA));
            if (photoPaths.size() > 0) {
                Uri uri = photoPaths.get(0);
                String path = fileUtils.getPath(uri);
//                Log.d("PHOTOPATHS", photoPaths.toString());
//                try {
//                    path = ContentUriUtils.INSTANCE.getFilePath(mContext, uri);
//                } catch (URISyntaxException e) {
//                    e.printStackTrace();
//                }
                Log.d("SHIV", path);
                Snackbar.make(mBinding.getRoot(), "Image attached", Snackbar.LENGTH_SHORT).show();
                mData.ImagePath = path;
                DocumentEntity documentEntity = documentDAO.checkItemIfExists(Id);
                if (documentEntity != null) {
                    documentEntity.ImagePath = path;
                    documentDAO.updateEntity(documentEntity);
                }
                mAdapter.dataList.get(Position).ImagePath = path;
                mAdapter.notifyItemChanged(Position);
                Log.d("test", documentDAO.getDataFromDocuments().toString());
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}