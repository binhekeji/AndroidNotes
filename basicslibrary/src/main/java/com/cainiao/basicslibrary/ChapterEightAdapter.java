package com.cainiao.basicslibrary;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cainiao.baselibrary.listener.OnItemClickListener;

/**
 * @author liangtao
 * @date 2018/11/21 16:41
 * @describe 基础知识适配器
 */
public class ChapterEightAdapter extends RecyclerView.Adapter<ChapterEightAdapter.ViewHolder> {


    private String[] data;
    private OnItemClickListener onItemClickListener;

    public ChapterEightAdapter(String[] data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_recyclerview, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(view);
                }
            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(data[position]);

    }


    @Override
    public int getItemCount() {
        return data.length;
    }

    /**
     * 创建ViewHolder
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
