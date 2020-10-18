package com.was.minemvc.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.was.core.common.http.ProgressSubscriber;
import com.was.core.utils.ValidateUtils;
import com.was.minemvc.R;
import com.was.minemvc.bean.HttpResult;
import com.was.minemvc.bean.UserBean;
import com.was.minemvc.common.HttpHelper;
import com.was.minemvc.common.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.etUsername)
    EditText etUsername;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        setBack();
        setTitleText("登录");
    }


    @OnClick(R.id.btnLogin)
    public void onViewClicked() {

        String userName = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        if (!ValidateUtils.checkPhoneNumber(userName) ||
                !ValidateUtils.checkPassword(password, 6)) {
            return;
        }

        Observable<HttpResult<UserBean>> observable = HttpHelper.getApi().login(userName, password);

        HttpHelper.toSubscribe(observable, new ProgressSubscriber<UserBean>(this) {
            @Override
            public void onNext(UserBean data) {

                toast("登录成功");
            }

            @Override
            public void onFail(Throwable e) {
                super.onFail(e);
            }
        });

    }
}