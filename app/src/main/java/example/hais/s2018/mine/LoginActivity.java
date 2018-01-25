package example.hais.s2018.mine;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import example.hais.s2018.R;
import example.hais.s2018.base.BaseActivity;

public class LoginActivity extends BaseActivity {

    @Bind(R.id.et_login_account)
    EditText etLoginAccount;
    @Bind(R.id.til_account)
    TextInputLayout tilAccount;
    @Bind(R.id.et_login_pwd)
    TextInputEditText etLoginPwd;
    @Bind(R.id.til_password)
    TextInputLayout tilPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_login);
        ButterKnife.bind(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        initEvent();
    }

    private void initEvent() {
        etLoginAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() <= 0) {
                    showError(tilAccount, "用户名不能为空");
                }
            }
        });
        etLoginPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() <= 0) {
                    showError(tilPassword, "密码不能为空");
                } else if (s.length() < 6 || s.length() > 18) {
                    showError(tilPassword, "密码为6-18位");
                }
            }
        });

        tilAccount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                    showError(tilAccount);
            }
        });
        tilPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                    showError(tilPassword);
            }
        });
    }

    /**
     * 显示错误
     *
     * @param textInputLayout
     * @param error
     */
    private void showError(TextInputLayout textInputLayout, String error) {
        textInputLayout.setError(error);
        textInputLayout.getEditText().setFocusable(true);
        textInputLayout.getEditText().setFocusableInTouchMode(true);
        textInputLayout.getEditText().requestFocus();
    }

    /**
     * 取消显示错误
     *
     * @param textInputLayout
     */
    private void showError(TextInputLayout textInputLayout) {
        textInputLayout.setError("");
        textInputLayout.getEditText().setFocusable(false);
    }

    /**
     * 验证用户名
     *
     * @param account
     * @return
     */
    private boolean checkAccount(String account) {
        if (TextUtils.isEmpty(account)) {
            showError(tilAccount, "用户名不能为空");
            return false;
        }
        return true;
    }

    /**
     * 验证密码
     *
     * @param password
     * @return
     */
    private boolean checkPassword(String password) {
        if (TextUtils.isEmpty(password)) {
            showError(tilPassword, "密码不能为空");
            return false;
        }

        if (password.length() < 6 || password.length() > 18) {
            showError(tilPassword, "密码长度为6-18位");
            return false;
        }

        return true;
    }


}
