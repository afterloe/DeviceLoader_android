package com.github.afterloe.pifinder.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.afterloe.pifinder.R;
import com.github.afterloe.pifinder.domain.Point;

import java.io.Serializable;
import java.util.List;

public class PointAdapter extends RecyclerView.Adapter<PointAdapter.PointItem> implements Serializable {

    private Context context;
    private List<Point> points;

    public PointAdapter(Context context, List<Point> points) {
        this.context = context;
        this.points = points;
    }

    public void addAll(List<Point> points) {
        this.points.clear();
        this.points.addAll(points);
    }

    @NonNull
    @Override
    public PointItem onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PointItem(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_points, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PointItem pointItem, int i) {
        pointItem.bind(points.get(i));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class PointItem extends RecyclerView.ViewHolder {

        TextView pointRemark;
        TextView pointUrl;

        PointItem(@NonNull View itemView) {
            super(itemView);
            pointRemark = itemView.findViewById(R.id.point_remark);
            pointUrl = itemView.findViewById(R.id.point_url);
        }

        void bind(Point point) {
            pointRemark.setText(point.getRemarks());
            pointUrl.setText("http://" + point.getHost() + "/" + point.getUrl());
        }
    }
}
