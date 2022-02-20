package com.oceanmtech.documentshare.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.oceanmtech.documentshare.Database.DocumentEntity;
import com.oceanmtech.documentshare.MainModule.MainActivity2;
import com.oceanmtech.documentshare.R;
import com.oceanmtech.documentshare.databinding.RowDataBinding;

import java.util.List;

public class MainActivity2Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    RowDataBinding mBinding;
    public List<DocumentEntity> dataList;
    Context mContext;
    OnClick onClick;
    String documentType;

    public MainActivity2Adapter(List<DocumentEntity> dataList, MainActivity2 mContext, OnClick onClick) {
        this.dataList = dataList;
        this.mContext = mContext;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.row_data, parent, false);
        return new ItemViewHolder(mBinding.getRoot(), mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final ItemViewHolder mViewHolder = (ItemViewHolder) holder;
        mViewHolder.mBinding.tvMemberId.setText(dataList.get(position).MemberId);

        mViewHolder.mBinding.tvName.setVisibility(View.VISIBLE);
        mViewHolder.mBinding.tvName.setText(dataList.get(position).Name);
        mViewHolder.mBinding.ivImage.setVisibility(View.VISIBLE);
        if (dataList.get(position).ImagePath != null && !dataList.get(position).ImagePath.equalsIgnoreCase("")) {
            mViewHolder.mBinding.tvSelectImage.setVisibility(View.GONE);
            mViewHolder.mBinding.llDocumentType.setVisibility(View.VISIBLE);
            mViewHolder.mBinding.tvSelectImage2.setVisibility(View.GONE);

        } else
            mViewHolder.mBinding.llDocumentType.setVisibility(View.GONE);

        if (dataList.get(position).DocumentType != null && !dataList.get(position).DocumentType.equalsIgnoreCase("")) {
            mViewHolder.mBinding.cvSelectedDocument.setVisibility(View.VISIBLE);
            mViewHolder.mBinding.tvSelectedDocument.setText(dataList.get(position).DocumentType);
            mViewHolder.mBinding.tvSelectDocumentType.setVisibility(View.GONE);
            mViewHolder.mBinding.llDocumentTypeList.setVisibility(View.GONE);
        } else {
            mViewHolder.mBinding.tvSelectDocumentType.setVisibility(View.VISIBLE);
            mViewHolder.mBinding.llDocumentTypeList.setVisibility(View.VISIBLE);
            mViewHolder.mBinding.cvSelectedDocument.setVisibility(View.GONE);
        }

        Glide.with(mContext).load(dataList.get(position).ImagePath).into(mViewHolder.mBinding.ivImage);

        mViewHolder.mBinding.ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.OnClickMethod(position, dataList.get(position).id);
            }
        });
        mViewHolder.mBinding.tvAadharF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                changeButton(R.drawable.bg_button, R.drawable.bg_button2, R.drawable.bg_button2, R.drawable.bg_button2,
//                        R.drawable.bg_button2, R.drawable.bg_button2, R.drawable.bg_button2,
//                        mContext.getResources().getColor(R.color.white), mContext.getResources().getColor(R.color.blue), mContext.getResources().getColor(R.color.blue),
//                        mContext.getResources().getColor(R.color.blue), mContext.getResources().getColor(R.color.blue), mContext.getResources().getColor(R.color.blue),
//                        mContext.getResources().getColor(R.color.blue));
//                mViewHolder.mBinding.tvSelectDocumentType.setVisibility(View.GONE);
                documentType = mViewHolder.mBinding.tvAadharF.getText().toString();
                onClick.updateDocumentType(documentType, position);
            }
        });
        mViewHolder.mBinding.tvAadharB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                documentType = mViewHolder.mBinding.tvAadharB.getText().toString();
                onClick.updateDocumentType(documentType, position);
            }
        });
        mViewHolder.mBinding.tvPanF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                documentType = mViewHolder.mBinding.tvPanF.getText().toString();
                onClick.updateDocumentType(documentType, position);
            }
        });
        mViewHolder.mBinding.tvPanB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                documentType = mViewHolder.mBinding.tvPanB.getText().toString();
                onClick.updateDocumentType(documentType, position);
            }
        });
        mViewHolder.mBinding.tvCancelledCheque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                documentType = mViewHolder.mBinding.tvCancelledCheque.getText().toString();
                onClick.updateDocumentType(documentType, position);
            }
        });
        mViewHolder.mBinding.tvPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                documentType = mViewHolder.mBinding.tvPhoto.getText().toString();
                onClick.updateDocumentType(documentType, position);
            }
        });
        mViewHolder.mBinding.tvAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                documentType = mViewHolder.mBinding.tvAgreement.getText().toString();
                onClick.updateDocumentType(documentType, position);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        RowDataBinding mBinding;

        public ItemViewHolder(@NonNull View itemView, RowDataBinding mBinding) {
            super(itemView);
            this.mBinding = mBinding;
        }
    }

    public interface OnClick {
        void OnClickMethod(int position, int id);

        void updateDocumentType(String documentType, int position);
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
}
