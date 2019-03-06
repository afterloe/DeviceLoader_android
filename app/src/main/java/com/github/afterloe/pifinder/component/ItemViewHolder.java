package com.github.afterloe.pifinder.component;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.io.Serializable;

public class ItemViewHolder extends RecyclerView.ViewHolder implements Serializable {

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        initListener(itemView);
    }

    private void initListener(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag = getAdapterPosition();
                Toast.makeText(v.getContext(), "position " + flag, Toast.LENGTH_LONG).show();
            }
        });
    }
}
