package ggcartoon.yztc.com.ggcartoon;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import ggcartoon.yztc.com.Bean.Person;
import ggcartoon.yztc.com.View.SwipBackActivityS;
import ggcartoon.yztc.com.initerface.Initerface;

/**
 * 登录界面
 */
public class LoginAcitivyt extends SwipBackActivityS implements Initerface, View.OnClickListener {

    @Bind(R.id.user)
    EditText user;
    @Bind(R.id.inputlayout1)
    TextInputLayout inputlayout1;
    @Bind(R.id.pass)
    EditText pass;
    @Bind(R.id.inputlayout2)
    TextInputLayout inputlayout2;
    @Bind(R.id.login_toolbar)
    Toolbar loginToolbar;
    @Bind(R.id.login_btn)
    Button loginBtn;
    @Bind(R.id.register_btn)
    Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_acitivyt);
        ButterKnife.bind(this);
        initview();
        initdata();
        initviewoper();
    }

    @Override
    public void initview() {
        setSupportActionBar(loginToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loginToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
    }

    @Override
    public void initdata() {

    }

    @Override
    public void initviewoper() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                String username = user.getText().toString().trim();
                String password = pass.getText().toString().trim();
                if (username.equals("") && password.equals("")) {
                    inputlayout1.setError("用户名或密码不能为空");
                    return;
                } else {
                    inputlayout1.setErrorEnabled(false);
                    BmobUser bo=new BmobUser();
                    Log.i("TAG",username+"::::"+password);
                    bo.setUsername(username);
                    bo.setPassword(password);
                    bo.login(new SaveListener<Person>() {
                        @Override
                        public void done(Person person, BmobException e) {
                            if(e==null){
                                Toast.makeText(LoginAcitivyt.this,"登录成功:"+BmobUser.getObjectByKey("title"),Toast.LENGTH_SHORT).show();
                                //返回上一级界面
                                finish();
                            }else{
                                Log.i("TAG",e.toString());
                            }
                        }
                    });
                }
                break;
            case R.id.register_btn:
                BmobUser bo=new BmobUser();
                bo.setUsername(user.getText().toString().trim());
                bo.setPassword(pass.getText().toString().trim());
                bo.signUp(new SaveListener<Person>() {
                    @Override
                    public void done(Person person, BmobException e) {
                        if(e==null){
                            Log.i("TAG","注册成功:" +person.toString());
                        }else{
                            Log.i("TAG",e.toString());
                        }
                    }
                });
                break;
            default:

                break;
        }
    }
}
