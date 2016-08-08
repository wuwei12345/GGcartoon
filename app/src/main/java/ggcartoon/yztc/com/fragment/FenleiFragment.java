package ggcartoon.yztc.com.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lidroid.xutils.HttpUtils;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ggcartoon.yztc.com.Adapter.TypeAdapter;
import ggcartoon.yztc.com.Bean.TypeBean;
import ggcartoon.yztc.com.View.OkHttpUtils;
import ggcartoon.yztc.com.ggcartoon.R;
import ggcartoon.yztc.com.ggcartoon.SelectActivity;
import ggcartoon.yztc.com.ggcartoon.TypeActivity;
import ggcartoon.yztc.com.initerface.Initerface;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FenleiFragment extends Fragment implements Initerface {

    //搜索框，搜索按钮，网格列表
    EditText FLEdit;
    Button FLSelect;
    @Bind(R.id.FL_list)
    RecyclerView FLList;
//    GridView FLList;

    private HttpUtils httpUtils;
    private String path = "http://csapi.dm300.com:21889/android/search/category";
    private List<TypeBean.DataBean> list;
    private TypeAdapter adapter;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    adapter = new TypeAdapter(list);
                    FLList.setAdapter(adapter);
                    adapter.setonItemClickLintener(new TypeAdapter.onItemClickLintener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent intent = new Intent(getActivity(), TypeActivity.class);
                            intent.putExtra("cateid", list.get(position).getCateId() + "");
                            intent.putExtra("title", list.get(position).getTitle() + "");
                            startActivity(intent);
                        }

                        @Override
                        public void onItemLongClick(View view, int Position) {

                        }
                    });
                    break;
                case 2:
                    Toast.makeText(getActivity(), "网络获取失败", Toast.LENGTH_SHORT).show();
                    break;
                default:

                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fenlei, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initview();
        initdata();
        initviewoper();
    }

    //初始化控件
    @Override
    public void initview() {
        FLList.setLayoutManager(new GridLayoutManager(getActivity(),4));
        httpUtils = new HttpUtils();
        FLEdit = (EditText) getActivity().findViewById(R.id.FL_edit);
        FLSelect = (Button) getActivity().findViewById(R.id.FL_select);
        FLSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = FLEdit.getText().toString().trim();
                if (str.equals("")) {
                    Toast.makeText(getActivity(), "搜索框不能为空", Toast.LENGTH_SHORT).show();

                } else {
                    Intent intent = new Intent(getActivity(), SelectActivity.class);
                    intent.putExtra("value", str);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void initdata() {
        //OKhttp网络访问
        try {
            OkHttpUtils.run(path).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    handler.sendEmptyMessage(2);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String json = response.body().string();
                    String obj = JSONObject.parseObject(json).getString("data");
                    list = JSONArray.parseArray(obj.toString(), TypeBean.DataBean.class);
                    handler.sendEmptyMessage(1);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initviewoper() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
