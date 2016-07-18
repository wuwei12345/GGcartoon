package ggcartoon.yztc.com.ggcartoon;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import ggcartoon.yztc.com.View.SwipBackActivityS;
import ggcartoon.yztc.com.initerface.Initerface;

/**
 * 登录界面
 */
public class LoginAcitivyt extends SwipBackActivityS implements Initerface ,View.OnClickListener{

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
    }

    @Override
    public void initdata() {

    }

    @Override
    public void initviewoper() {

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.login_btn:
                String username=user.getText().toString().trim();
                String password=pass.getText().toString().trim();
                if (username.equals("")){
                    inputlayout1.setError("用户名不能为空");
                    return;
                }else{
                    inputlayout1.setErrorEnabled(false);
                }
                if (password.equals("")){
                    inputlayout2.setError("密码不能为空");
                    return;
                }else{
                    inputlayout2.setErrorEnabled(false);
                }
             break;
            default:

             break;
        }
    }
}
