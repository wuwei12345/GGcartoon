package ggcartoon.yztc.com.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lidroid.xutils.HttpUtils;

import java.io.IOException;
import java.util.List;

import butterknife.ButterKnife;
import ggcartoon.yztc.com.Adapter.TypeAdapter;
import ggcartoon.yztc.com.Bean.TypeBean;
import ggcartoon.yztc.com.ggcartoon.R;
import ggcartoon.yztc.com.ggcartoon.SelectActivity;
import ggcartoon.yztc.com.ggcartoon.TypeActivity;
import ggcartoon.yztc.com.initerface.Initerface;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FenleiFragment extends Fragment implements Initerface {

    //搜索框，搜索按钮，网格列表
    EditText FLEdit;
    Button FLSelect;
    GridView FLList;

    private HttpUtils httpUtils;
    private String path = "http://csapi.dm300.com:21889/android/search/category";
    private List<TypeBean.DataBean> list;
    private TypeAdapter adapter;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 1:
                    adapter.setData(list);
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
        httpUtils = new HttpUtils();
        adapter = new TypeAdapter();
        FLEdit = (EditText) getActivity().findViewById(R.id.FL_edit);
        FLList = (GridView) getActivity().findViewById(R.id.FL_list);
        FLSelect = (Button) getActivity().findViewById(R.id.FL_select);
        FLList.setSelector(new ColorDrawable(Color.TRANSPARENT));
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
    //Xutils网络访问
//        httpUtils.send(HttpRequest.HttpMethod.GET, path, new RequestCallBack<String>() {
//            @Override
//            public void onSuccess(ResponseInfo<String> responseInfo) {
//                String json = responseInfo.result;
//                String obj = JSONObject.parseObject(json).getString("data");
//                list = JSONArray.parseArray(obj.toString(), TypeBean.DataBean.class);
//                adapter.setData(list);
//            }
//
//            @Override
//            public void onFailure(HttpException e, String s) {
//                Toast.makeText(getActivity(), "获取内容失败，请检查网络", Toast.LENGTH_SHORT).show();
//            }
//        });
        //OKhttp网络访问
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(path).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
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

    }

    @Override
    public void initviewoper() {
        FLList.setAdapter(adapter);
        FLList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(), TypeActivity.class);
                intent.putExtra("cateid",list.get(position).getCateId()+"");
                intent.putExtra("title",list.get(position).getTitle()+"");
                startActivity(intent);
            }
        });
    }

}
