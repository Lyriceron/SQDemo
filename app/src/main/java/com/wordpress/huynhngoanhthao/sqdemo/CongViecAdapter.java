package com.wordpress.huynhngoanhthao.sqdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.List;

public class CongViecAdapter extends BaseAdapter {

    private List<CongViec> mCongViecs;
    private int mLayout;
    private MainActivity mContext;

    private MainActivity mMainContext;

    public CongViecAdapter(List<CongViec> congViecs, int layout, MainActivity context) {
        mCongViecs = congViecs;
        mLayout = layout;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mCongViecs.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        TextView mTextView;
        ImageButton mImageButtonDelete, mImageButtonEdit;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        mMainContext = new MainActivity();

        final ViewHolder holder;

        if(convertView == null){
            holder = new ViewHolder();

            LayoutInflater inflater =
                    (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mLayout, null);
            holder.mTextView = (TextView) convertView.findViewById(R.id.tenCv);
            holder.mImageButtonDelete = (ImageButton) convertView.findViewById(R.id.imbDelete);
            holder.mImageButtonEdit = (ImageButton) convertView.findViewById(R.id.imbEdit);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final CongViec congViec = mCongViecs.get(position);

        holder.mTextView.setText(congViec.getTenCv());

        holder.mImageButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.updateWork(congViec.getTenCv(), congViec.getIdCv());
            }
        });

        holder.mImageButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.removeRow(congViec.getIdCv(), congViec.getTenCv());
            }
        });

        return convertView;
    }
}
