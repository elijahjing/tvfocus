package com.example.administrator.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by ZMeng on 2016/10/25.
 */
public interface OnChildSelectedListener {
	public void onChildSelected( View view, int position);
	public void updataParentMove(View view, int dx, int dy);

}
