package ggcartoon.yztc.com.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ggcartoon.yztc.com.ggcartoon.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class demofragment extends Fragment {





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=LayoutInflater.from(ViewGroup.getcontext()).inflate(R.layout.fragment_demofragment, container, false);

        return view;
    }
    oncreateActivity(){
       //初始化控件
        //异步访问，handler设置Adapter
        //
    }
}
