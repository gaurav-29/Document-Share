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
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.oceanmtech.documentshare.R;
import com.oceanmtech.documentshare.Utils.Constants;
import com.oceanmtech.documentshare.Utils.PreferenceHelper;
import com.oceanmtech.documentshare.databinding.ActivityMainBinding;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import droidninja.filepicker.utils.ContentUriUtils;

public class MainActivity extends AppCompatActivity {

    MainActivity mContext = MainActivity.this;
    ActivityMainBinding mBinding;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(mContext, R.layout.activity_main);
        PreferenceHelper.putString(Constants.ID, "12345");
        PreferenceHelper.putString(Constants.PASSWORD, "123456");
        onClickListeners();
    }

    int selectedId = -1;

    private void onClickListeners() {

        mBinding.ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoPaths.clear();
                Glide.with(mContext).load(getDrawable(R.drawable.img_placeholder)).centerCrop().into(mBinding.ivImage);
                mBinding.ivCancel.setVisibility(View.GONE);
            }
        });
        mBinding.tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoPaths.clear();
                Glide.with(mContext).load(getDrawable(R.drawable.img_placeholder)).centerCrop().into(mBinding.ivImage);
                mBinding.llDocumentType.setVisibility(View.GONE);
                mBinding.ivCancel.setVisibility(View.GONE);

                changeButton(R.drawable.bg_button2, R.drawable.bg_button2, R.drawable.bg_button2, R.drawable.bg_button2,
                        R.drawable.bg_button2, R.drawable.bg_button2, R.drawable.bg_button2,
                        getResources().getColor(R.color.blue), getResources().getColor(R.color.blue), getResources().getColor(R.color.blue),
                        getResources().getColor(R.color.blue), getResources().getColor(R.color.blue),
                        getResources().getColor(R.color.blue),
                        getResources().getColor(R.color.blue));
            }
        });
        mBinding.ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        });
        mBinding.tvUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (photoPaths.size() == 0) {
                    Snackbar.make(mBinding.getRoot(),"Please attach image",Snackbar.LENGTH_SHORT).show();
                }
                else if(selectedId == -1){
                    Snackbar.make(mBinding.getRoot(),"Please select the type of document",Snackbar.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(mContext, "Document uploaded successfully", Toast.LENGTH_LONG).show();
                }
            }
        });
        mBinding.tvAadharF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedId = 0;
                changeButton(R.drawable.bg_button, R.drawable.bg_button2, R.drawable.bg_button2, R.drawable.bg_button2,
                        R.drawable.bg_button2, R.drawable.bg_button2, R.drawable.bg_button2,
                        getResources().getColor(R.color.white), getResources().getColor(R.color.blue), getResources().getColor(R.color.blue),
                        getResources().getColor(R.color.blue), getResources().getColor(R.color.blue),
                        getResources().getColor(R.color.blue),
                        getResources().getColor(R.color.blue));
            }
        });
        mBinding.tvAadharB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedId = 0;
                changeButton(R.drawable.bg_button2, R.drawable.bg_button, R.drawable.bg_button2, R.drawable.bg_button2,
                        R.drawable.bg_button2, R.drawable.bg_button2, R.drawable.bg_button2,
                        getResources().getColor(R.color.blue), getResources().getColor(R.color.white), getResources().getColor(R.color.blue),
                        getResources().getColor(R.color.blue), getResources().getColor(R.color.blue),
                        getResources().getColor(R.color.blue),
                        getResources().getColor(R.color.blue));
            }
        });
        mBinding.tvPanF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedId = 0;
                changeButton(R.drawable.bg_button2, R.drawable.bg_button2, R.drawable.bg_button, R.drawable.bg_button2,
                        R.drawable.bg_button2, R.drawable.bg_button2, R.drawable.bg_button2,
                        getResources().getColor(R.color.blue), getResources().getColor(R.color.blue), getResources().getColor(R.color.white),
                        getResources().getColor(R.color.blue), getResources().getColor(R.color.blue),
                        getResources().getColor(R.color.blue),
                        getResources().getColor(R.color.blue));
            }
        });
        mBinding.tvPanB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedId = 0;
                changeButton(R.drawable.bg_button2, R.drawable.bg_button2, R.drawable.bg_button2, R.drawable.bg_button,
                        R.drawable.bg_button2, R.drawable.bg_button2, R.drawable.bg_button2,
                        getResources().getColor(R.color.blue), getResources().getColor(R.color.blue), getResources().getColor(R.color.blue),
                        getResources().getColor(R.color.white), getResources().getColor(R.color.blue),
                        getResources().getColor(R.color.blue),
                        getResources().getColor(R.color.blue));
            }
        });
        mBinding.tvCancelledCheque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedId = 0;
                changeButton(R.drawable.bg_button2, R.drawable.bg_button2, R.drawable.bg_button2, R.drawable.bg_button2,
                        R.drawable.bg_button, R.drawable.bg_button2, R.drawable.bg_button2,
                        getResources().getColor(R.color.blue), getResources().getColor(R.color.blue), getResources().getColor(R.color.blue),
                        getResources().getColor(R.color.blue), getResources().getColor(R.color.white),
                        getResources().getColor(R.color.blue),
                        getResources().getColor(R.color.blue));
            }
        });
        mBinding.tvPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedId = 0;
                changeButton(R.drawable.bg_button2, R.drawable.bg_button2, R.drawable.bg_button2, R.drawable.bg_button2,
                        R.drawable.bg_button2, R.drawable.bg_button, R.drawable.bg_button2,
                        getResources().getColor(R.color.blue), getResources().getColor(R.color.blue), getResources().getColor(R.color.blue),
                        getResources().getColor(R.color.blue), getResources().getColor(R.color.blue),
                        getResources().getColor(R.color.white),
                        getResources().getColor(R.color.blue));
            }
        });
        mBinding.tvAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedId = 0;
                changeButton(R.drawable.bg_button2, R.drawable.bg_button2, R.drawable.bg_button2, R.drawable.bg_button2,
                        R.drawable.bg_button2, R.drawable.bg_button2, R.drawable.bg_button,
                        getResources().getColor(R.color.blue), getResources().getColor(R.color.blue), getResources().getColor(R.color.blue),
                        getResources().getColor(R.color.blue), getResources().getColor(R.color.blue), getResources().getColor(R.color.blue),
                        getResources().getColor(R.color.white));
            }
        });
    }

    public void changeButton(int bgBtn1, int bgBtn2, int bgBtn3, int bgBtn4, int bgBtn5, int bgBtn6, int bgBtn7,
                             int color1, int color2, int color3, int color4, int color5, int color6, int color7) {
        mBinding.tvAadharF.setBackgroundResource(bgBtn1);
        mBinding.tvAadharB.setBackgroundResource(bgBtn2);
        mBinding.tvPanF.setBackgroundResource(bgBtn3);
        mBinding.tvPanB.setBackgroundResource(bgBtn4);
        mBinding.tvCancelledCheque.setBackgroundResource(bgBtn5);
        mBinding.tvPhoto.setBackgroundResource(bgBtn6);
        mBinding.tvAgreement.setBackgroundResource(bgBtn7);
        mBinding.tvAadharF.setTextColor(color1);
        mBinding.tvAadharB.setTextColor(color2);
        mBinding.tvPanF.setTextColor(color3);
        mBinding.tvPanB.setTextColor(color4);
        mBinding.tvCancelledCheque.setTextColor(color5);
        mBinding.tvPhoto.setTextColor(color6);
        mBinding.tvAgreement.setTextColor(color7);
    }

    private void imagePicker() {
        FilePickerBuilder.getInstance()
                .setMaxCount(1) //optional
                .setActivityTheme(R.style.LibAppTheme) //optional
                .pickPhoto(this, 1);
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
                Log.d("PHOTOPATHS", photoPaths.toString());
                try {
                    path = ContentUriUtils.INSTANCE.getFilePath(mContext, uri);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                Log.d("SHIV", path);
                Glide.with(mContext).load(path).centerCrop().into(mBinding.ivImage);
                Snackbar.make(mBinding.getRoot(),"Image attached",Snackbar.LENGTH_SHORT).show();
                mBinding.ivCancel.setVisibility(View.VISIBLE);
                mBinding.llDocumentType.setVisibility(View.VISIBLE);
            }
        }
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}