package ggcartoon.yztc.com.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import ggcartoon.yztc.com.Bean.Person;
import ggcartoon.yztc.com.Bean.ShouCang;
import ggcartoon.yztc.com.ggcartoon.LoginAcitivyt;
import ggcartoon.yztc.com.ggcartoon.MainActivity;
import ggcartoon.yztc.com.ggcartoon.ManHuaXiangQingActivity;
import ggcartoon.yztc.com.ggcartoon.R;
import ggcartoon.yztc.com.initerface.Initerface;

import static android.app.Activity.RESULT_OK;

/**
 * 书架
 * A simple {@link Fragment} subclass.
 */
public class ShujiaFragment extends Fragment implements Initerface, View.OnClickListener, AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {

    @Bind(R.id.weishoucang)
    TextView weishoucang;
    @Bind(R.id.shoucang_list)
    ListView shoucangList;
    @Bind(R.id.icon_img)
    ImageView iconImg;
    @Bind(R.id.login)
    TextView login;
    @Bind(R.id.synSC)
    Button synSC;
    @Bind(R.id.QUERT_user)
    TextView QUERTUser;
    //listview
    private List<ShouCang> listS;
    private ShouCangListAdapter shouCangListAdapter;
    //网络列表
    List<Person> personlist;
    private BmobUser user;
    //记录同步收藏数目
    int indexs = 0;
    //标志位
    boolean flag = true;
    int index = 0;
    int finalI;
    String tb = "同步中";
    String dian = ".";
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    synSC.setText(tb);
                    index++;
                    if (index == 3) {
                        tb = "同步中";
                        index = 0;
                    }else if (flag==false){
                        synSC.setText("同步收藏");
                    }
                    break;
                case 2:
                    flag = false;
                    synSC.setText("同步失败");
                    synSC.setClickable(true);
                    Toast.makeText(getActivity(), "同步失败,网络异常", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Person p = new Person();
                    p.setUsername(user.getUsername());
                    p.setTitle(listS.get(finalI).getTitle());
                    p.setComicId(listS.get(finalI).getComicId());
                    p.setLastCharpterTitle(listS.get(finalI).getLastCharpterTitle());
                    p.setThumb(listS.get(finalI).getThumb());
                    p.setUpdateTime(listS.get(finalI).getUpdateTime());
                    p.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                Log.i("TAG", "创建数据成功：" + s);
                            } else {
                                if (e.getErrorCode() != 401) {
                                    indexs++;
                                }
                                Log.i("TAG", "失败：" + e.getMessage() + "," + e.getErrorCode());
                            }
                        }
                    });
                    break;
            }
        }
    };
    private boolean flags;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shujia, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
//    @Override
//    public void onStart() {
//        super.onStart();
//        Log.i("TAG","~~~~~onstart");
//        initview();
//        initdata();
//        initviewoper();
//    }
//

    @Override
    public void onResume() {
        super.onResume();
        Log.i("bmob","----->onresume");
        initview();
    }

    //初始化控件
    @Override
    public void initview() {
        iconImg = (ImageView) getActivity().findViewById(R.id.icon_img);
        weishoucang = (TextView) getActivity().findViewById(R.id.weishoucang);
        shoucangList = (ListView) getActivity().findViewById(R.id.shoucang_list);
        shoucangList.setOnItemClickListener(this);
        shoucangList.setOnItemLongClickListener(this);
        iconImg.setOnClickListener(this);
        synSC.setOnClickListener(this);
        QUERTUser.setOnClickListener(this);
        //获取数据库中收藏的数据，然后判断是否为空，不为空则设置adapter
        try {
            listS = MainActivity.dbUtils.findAll(ShouCang.class);
//            Log.i("TAG","bmoblists:"+listS.size()+"");
            user = BmobUser.getCurrentUser();
            if (user != null) {
                //设置用户名
                login.setText(user.getUsername());
                //改变同步按钮字体
                cartoontb();
                //去网络获取收藏内容
                Querybmob();
            } else {
                login.setText("登陆");
                initdata();
//                Toast.makeText(getActivity(), "未登陆", Toast.LENGTH_SHORT).show();
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
    //改变同步按钮字体内容
    private void cartoontb() {
        synSC.setClickable(false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (flag) {
                    try {
                        tb += dian;
                        handler.sendEmptyMessage(1);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public void initdata() {
        Log.i("bmob","initdata()");
        if (listS != null&&listS.size()!=0) {
            shoucangList.setVisibility(View.VISIBLE);
            shouCangListAdapter = new ShouCangListAdapter();
            shouCangListAdapter.notifyDataSetChanged();
            shoucangList.setAdapter(shouCangListAdapter);
            weishoucang.setVisibility(View.INVISIBLE);
            flag = false;
            Log.i("bmob","initdata()1");
            synSC.setText("同步收藏");
            synSC.setClickable(true);
        } else {
            shoucangList.setVisibility(View.INVISIBLE);
            flag = false;
            Log.i("bmob","initdata()2");
            synSC.setText("同步收藏");
            synSC.setClickable(true);
        }
    }

    //查询用户收藏
    private void Querybmob() {
        if (listS != null&&listS.size()!=0) {
            initdata();
        } else {
            //服务器查询对应用户的收藏数据
            BmobQuery<Person> query = new BmobQuery<>();
            query.addWhereEqualTo("username", user.getUsername());
            query.setLimit(50);
            query.findObjects(new FindListener<Person>() {
                @Override
                public void done(List<Person> list, BmobException e) {
                    personlist = list;
                    try {
                        if (e == null) {
                            if (list != null) {
                                for (int i = 0; i < list.size(); i++) {
                                    ShouCang s = new ShouCang();
                                    s.setTitle(list.get(i).getTitle());
                                    s.setUpdateTime(list.get(i).getUpdateTime());
                                    s.setThumb(list.get(i).getThumb());
                                    s.setComicId(list.get(i).getComicId());
                                    s.setLastCharpterTitle(list.get(i).getLastCharpterTitle());
                                    MainActivity.dbUtils.save(s);
                                }
                                //将收藏的数据放进本地列表
                                listS = MainActivity.dbUtils.findAll(ShouCang.class);
                                initdata();
                            } else {
                                initdata();
                            }
                        } else {
                            Log.i("bmob", "----->失败：" + e.getMessage() + "," + e.getErrorCode());
                            handler.sendEmptyMessage(2);
                        }
                    } catch (DbException e1) {
                        e1.printStackTrace();
                    }
                }
            });
        }
    }

    //将本地数据同步服务器
    private void bmobTB(int i) {

                BmobQuery<Person> query = new BmobQuery<>();
                query.addWhereEqualTo("username", user.getUsername());
                query.addWhereEqualTo("title",listS.get(i).getTitle());
                Log.i("bmob","要查询的数据:"+listS.get(i).getTitle());
                query.setLimit(50);
                finalI = i;
                query.findObjects(new FindListener<Person>() {
                    @Override
                    public void done(List<Person> list, BmobException e) {
                       if (e==null){
                           Log.i("bmob",":查询的数据数量:"+list.size());
                           if (list.size()<1){
                               handler.sendEmptyMessage(3);

                        }
                           flags=false;
                       }else{
                           Toast.makeText(getActivity(),"同步失败",Toast.LENGTH_SHORT).show();
                       }
                    }
                });


    }

    @Override
    public void initviewoper() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    //获取本地相册
    protected void getImageFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, 0);
    }

    //通过相机获取照片
    protected void getImageFromCamera() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
            startActivityForResult(getImageByCamera, 1);
        } else {
            Toast.makeText(getActivity(), "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {        //此处的 RESULT_OK 是系统自定义得一个常量
            Log.i("TAG", "ActivityResult resultCode error");
            return;
        }

        //获取到选择照片的uri然后设置到imageview中
        if (requestCode == 0) {
            Uri imguri = data.getData();
            Picasso.with(getActivity()).load(imguri).into(iconImg);
        }
        if (requestCode == 1) {
            Uri imguris = data.getData();
            if (imguris != null) {
                Picasso.with(getActivity()).load(imguris).into(iconImg);
            } else {
                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");
                iconImg.setImageBitmap(bitmap);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.icon_img:
                if (user != null) {
                    final String[] items = {"拍照", "相册获取"};
                    new AlertDialog.Builder(getActivity()).
                            setItems(items, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (i == 0) {
                                        //通过相机获取图片
                                        getImageFromCamera();
                                    } else {
                                        //通过相册获取图片
                                        getImageFromAlbum();
                                    }
                                }
                            }).show();
                } else {
                    Intent intent = new Intent(getActivity(), LoginAcitivyt.class);
                    startActivity(intent);
                }
                break;
            case R.id.synSC:
                if (synSC.getText().equals("同步失败")) {
                    onResume();
                } else {
                    if (listS != null) {
                        for (int i = 0; i < listS.size(); i++) {
                            bmobTB(i);
                        }
                        if (indexs > 0) {
                            Toast.makeText(getActivity(), "同步失败", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "同步完成", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
            case R.id.QUERT_user:
                try {
                    if (synSC.getText().equals("同步收藏")) {
                        if (user != null) {
                            BmobUser.logOut();
                            for (ShouCang s : listS) {
                                MainActivity.dbUtils.delete(ShouCang.class, WhereBuilder.b("title", "=", s.getTitle()));
                            }
                            onResume();
                            Toast.makeText(getActivity(), "已退出", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "未登陆任何账号", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getActivity(), "当前正在同步收藏,请稍后再试", Toast.LENGTH_SHORT).show();
                    }
                } catch (DbException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("确认删除收藏？");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    BmobQuery<Person> query = new BmobQuery<Person>();
                    Log.i("TAG", "------>" + listS.get(position).getTitle());
                    query.addWhereEqualTo("title", listS.get(position).getTitle());
                    query.addWhereEqualTo("username",user.getUsername());
                    query.setLimit(listS.size());
                    query.findObjects(new FindListener<Person>() {
                        @Override
                        public void done(List<Person> list, BmobException e) {
                            if (e == null) {
                                if (list != null) {
                                    Toast.makeText(getActivity(), "共" + list.size(), Toast.LENGTH_SHORT).show();
                                    Person p2 = new Person();
                                    p2.delete(list.get(0).getObjectId(), new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            if (e == null) {
                                                Log.i("bmob", "成功");
                                            } else {
                                                Log.i("bmob", "失败了：" + e.getMessage() + "," + e.getErrorCode());
                                            }
                                        }

                                    });
                                }
                            } else {
                                Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                            }
                        }
                    });

                    MainActivity.dbUtils.delete(ShouCang.class, WhereBuilder.b("title", "=", listS.get(position).getTitle()));
                    listS.remove(position);
                    Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                    onResume();

                } catch (DbException e) {
                    e.printStackTrace();
                }
//                if (listS.size() == 0) {
//                    shoucangList.setVisibility(View.INVISIBLE);
//                    weishoucang.setVisibility(View.VISIBLE);
//                    shouCangListAdapter.notifyDataSetChanged();
//                }
            }
        });
        builder.show();
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), ManHuaXiangQingActivity.class);
        intent.putExtra("comicId", listS.get(position).getComicId());
        startActivity(intent);
    }


    class ShouCangListAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return listS != null ? listS.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return listS != null ? listS.get(position) : null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shoucang_list_item, null);
                holder.ivThumb = (ImageView) convertView.findViewById(R.id.iv_shoucang_thumb);
                holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_shoucang_title);
                holder.tvUpdateTime = (TextView) convertView.findViewById(R.id.tv_updatetime);
                holder.tvLastCharpterTitle = (TextView) convertView.findViewById(R.id.tv_lastCharpterTitle);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            ShouCang shoucang = listS.get(position);
            holder.tvTitle.setText(shoucang.getTitle());
            holder.tvUpdateTime.setText("更新时间：" + shoucang.getUpdateTime());
            holder.tvLastCharpterTitle.setText("更新到：" + shoucang.getLastCharpterTitle());

            Picasso.with(parent.getContext()).load(shoucang.getThumb()).into(holder.ivThumb);
            return convertView;
        }

        class ViewHolder {
            ImageView ivThumb;
            TextView tvTitle, tvUpdateTime, tvLastCharpterTitle;
        }
    }
}

