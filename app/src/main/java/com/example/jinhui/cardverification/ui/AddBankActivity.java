package com.example.jinhui.cardverification.ui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.jinhui.cardverification.R;
import com.example.jinhui.cardverification.util.BootStepUtils;
import com.example.jinhui.cardverification.view.AddSpaceTextWatcher;

/**
 * 学习下银行卡验证功能，看看有什么猫腻
 * 参考自：https://github.com/wenzhimin/BootStepView
 */
public class AddBankActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AddBinkActivity";
    private Context mContext;
    private Toolbar mToolbar;
    private EditText mEditTextName;
    private EditText mEditTextBinkCode;
    private ImageView mImageViewClear;
    private ImageView mImageViewCamera;
    private CheckBox mCheckBox;
    private Button mButtonNext;
    private AddSpaceTextWatcher addSpaceTextWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        initView();
        initData();
        initListener();
    }

    private void initData() {
        addSpaceTextWatcher = new AddSpaceTextWatcher(AddBankActivity.this, mEditTextBinkCode, 57);
        addSpaceTextWatcher.setImageView(R.id.iv_add_bink_clear);
        addSpaceTextWatcher.setSpaceType(AddSpaceTextWatcher.SpaceType.bankCardNumberType);
    }

    private void initListener() {
        mImageViewClear.setOnClickListener(this);
        mImageViewCamera.setOnClickListener(this);
        mButtonNext.setOnClickListener(this);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mButtonNext.setEnabled(true);
                    mButtonNext.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                } else {
                    mButtonNext.setEnabled(false);
                    mButtonNext.setBackgroundColor(getResources().getColor(R.color.title_text_color_night));
                }
            }
        });

    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_add_bink);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        // 这里不设置图片，不显示图片
        mToolbar.setNavigationIcon(R.mipmap.community_main_details_back);
        mEditTextName = (EditText) findViewById(R.id.et_add_bink_username);
        mEditTextBinkCode = (EditText) findViewById(R.id.et_add_bink_code);
        mImageViewClear = (ImageView) findViewById(R.id.iv_add_bink_clear);
        mImageViewCamera = (ImageView) findViewById(R.id.iv_add_bink_camera);
        mCheckBox = (CheckBox) findViewById(R.id.checkbox_add_bink);
        mButtonNext = (Button) findViewById(R.id.btn_add_bink_next);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_add_bink_clear:
                mEditTextBinkCode.setText("");
                break;
            case R.id.iv_add_bink_camera:
                //拍照
                break;
            case R.id.btn_add_bink_next:
                if (isBankNameCode()) {
                    BootStepUtils.intentToActivityClasss(AddBankActivity.this,
                            AddBankMessageActivity.class, null);
                }
                break;
        }
    }

    private boolean isBankNameCode() {
        String bankCode = mEditTextBinkCode.getText().toString();
        String bankName = mEditTextName.getText().toString();
        Log.d(TAG, "isChinese=" + BootStepUtils.IsChinese(bankName));
        Log.d(TAG, "bankCode=【" + bankCode.replaceAll("\\s*", "") + "】");
        Log.d(TAG, "IsBankCard19=" + BootStepUtils.IsBankCard19(bankCode.replaceAll("\\s*", "")));
        if (!BootStepUtils.IsChinese(bankName)) {
            mEditTextName.setError("输入正确的中文名");
            return false;
        } else if (!BootStepUtils.IsBankCard19(bankCode.replaceAll("\\s*", ""))) {
            mEditTextBinkCode.setError("输入正确的银行卡号");
            return false;
        }
        return true;
    }
}
