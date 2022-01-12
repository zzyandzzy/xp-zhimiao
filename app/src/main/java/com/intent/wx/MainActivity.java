package com.intent.wx;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Switch;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.content.ContextCompat;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONValidator;
import com.intent.wx.api.ApiService;
import com.intent.wx.api.ConfigService;
import com.intent.wx.constant.Const;
import com.intent.wx.model.Config;
import com.intent.wx.model.ServerConfig;
import com.intent.wx.model.User;
import com.intent.wx.util.DecryptUtils;
import com.intent.wx.xposed.XposedUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.intent.wx.constant.Const.TAG;

/**
 * @author intent zzy.main@gmail.com
 * @date 2021/11/7 下午1:51
 * @since 1.0
 */
public class MainActivity extends AppCompatActivity {
    private Switch debugSwitch;
    private AppCompatEditText signatureEditText;
    private AppCompatButton signatureButton;
    private AppCompatEditText cookieEditText;
    private AppCompatButton cookieButton;
    private AppCompatButton getUserButton;
    private AppCompatButton startSeckillButton;
    private AppCompatButton startSeckillButtonMonth;
    private AppCompatButton clearButton;
    private AppCompatSpinner hpvTypeSpinner;
    private AppCompatEditText hospitalIdEditText;
    private AppCompatButton uploadCookieButton;
    //    private AppCompatEditText pidEditText;
    private AppCompatEditText dateEditText;
    private Config config;
    private static final int REQUEST_PERMISSIONS_CODE = 1;
    private volatile static boolean isSubmit = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
        initViewData();
    }

    private void initViews() {
        debugSwitch = findViewById(R.id.switch_debug);
        signatureEditText = findViewById(R.id.edittext_signature);
        signatureButton = findViewById(R.id.button_signature);
        cookieEditText = findViewById(R.id.edittext_cookie);
        cookieButton = findViewById(R.id.button_cookie);
        getUserButton = findViewById(R.id.button_get_user);
        clearButton = findViewById(R.id.button_clear);
        startSeckillButton = findViewById(R.id.button_start_seckill);
        startSeckillButtonMonth = findViewById(R.id.button_start_seckill_all_month);
        hpvTypeSpinner = findViewById(R.id.spinner_hpv_type);
        hospitalIdEditText = findViewById(R.id.edittext_hospital_id);
//        pidEditText = findViewById(R.id.edittext_pid);
        dateEditText = findViewById(R.id.edittext_date);
        uploadCookieButton = findViewById(R.id.button_upload_cookie);

        initViewData();
        signatureButton.setOnClickListener(v -> {
            XposedUtils.copyToClip(config.getSignature());
            Log.d(TAG, config.toString());
            Toast.makeText(MainActivity.this, "复制Signature成功", Toast.LENGTH_SHORT).show();
        });
        cookieButton.setOnClickListener(v -> {
            XposedUtils.copyToClip(config.getCookie());
            Log.d(TAG, config.toString());
            Toast.makeText(MainActivity.this, "复制Cookie成功", Toast.LENGTH_SHORT).show();
        });
        debugSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            config.setDebug(b);
            XposedUtils.writeConfig(config);
        });
        clearButton.setOnClickListener(v -> {
            clearDateEditText();
        });
        hpvTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String hpvTypeStr = (String) parent.getItemAtPosition(position);
                config.setPid(Config.convertHpvType(hpvTypeStr));
                XposedUtils.writeConfig(config);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        getUserButton.setOnClickListener(v -> {
            ApiService.getUser(config)
                    .subscribe(responseBody -> {
                        String json = responseBody.string();
                        Log.d(TAG, json);
                        JSONObject data = JSONObject.parseObject(json);
                        User user = data.getObject(Const.USER_KEY, User.class);
                        if (user != null) {
                            config.setUser(user);
                            config.setHid(Integer.valueOf(hospitalIdEditText.getText().toString()));
                            config.setDate(dateEditText.getText().toString());
                            XposedUtils.writeConfig(config);
                            Toast.makeText(MainActivity.this, "获取用户信息: " + user.toString() + " 成功!", Toast.LENGTH_SHORT).show();
                        }
                    }, throwable -> {
                        throwable.printStackTrace();
                        Toast.makeText(MainActivity.this, "获取用户信息失败!", Toast.LENGTH_SHORT).show();
                    });
        });
        startSeckillButton.setOnClickListener(v -> {
//            if (isSubmit) {
//                showSuccessDialog();
//                return;
//            }
            Editable dateEditable = dateEditText.getText();
            if (dateEditable != null && StringUtils.isNotBlank(dateEditable.toString())) {
                String[] dateArray = dateEditable.toString().split(",");
                for (String date : dateArray) {
                    doSeckill(date);
                }
            } else {
                doGetDateAndSeckill(true);
            }
        });
        startSeckillButtonMonth.setOnClickListener(v -> {
            // 生成今天到本月月底的所有天数
            List<String> dateMonthList = generateMonthDate();
            for (String date : dateMonthList) {
                doSeckill(date);
            }
        });
        uploadCookieButton.setOnClickListener(v -> {
            ServerConfig serverConfig = new ServerConfig();
            serverConfig.setCookie(config.getCookie());
            serverConfig.setSignature(config.getSignature());
            serverConfig.setPid(520);
            Editable dateEditable = dateEditText.getText();
            if (dateEditable != null && StringUtils.isNotBlank(dateEditable.toString())) {
                serverConfig.setInoculationDate(dateEditable.toString());
                serverConfig.setHid(Integer.valueOf(hospitalIdEditText.getText().toString()));
            }
            ConfigService.uploadCookie(serverConfig)
                    .subscribe(responseBody -> {
                        Toast.makeText(MainActivity.this, responseBody.string(), Toast.LENGTH_SHORT).show();
                    });
        });
    }

    private void showSuccessDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("成功")
                .setMessage("提交订单成功!快去小程序看看!")
                .create();
        alertDialog.show();
    }

    private void doGetDateAndSeckill(boolean showToast) {
        ApiService.getSubDateAll(config)
                .subscribe(responseBody -> {
                    String json = responseBody.string();
                    Log.d(TAG, json);
                    JSONObject jsonObject = JSONObject.parseObject(json);
                    JSONArray list = jsonObject.getJSONArray(Const.LIST_KEY);
                    if (list == null || list.isEmpty()) {
                        Log.d(TAG, "没有查询到信息!");
                        if (showToast) {
                            Toast.makeText(MainActivity.this, "没有查询到日期信息!", Toast.LENGTH_SHORT).show();
                        }
                        return;
                    }
                    boolean enable = false;
                    for (int i = 0; i < list.size(); i++) {
                        JSONObject object = list.getJSONObject(i);
                        if (object.getBoolean(Const.ENABLE_KEY)) {
                            String date = object.getString(Const.DATE_KEY);
                            doSeckill(date);
                            enable = true;
                        }
                    }
                    if (!enable) {
                        Toast.makeText(MainActivity.this, "苗被抢完了!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private List<String> generateMonthDate() {
        List<String> dateMonthList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int monthDayCount = Const.MONTH_OF_DAY[month];
        for (int i = dayOfMonth + 1; i <= monthDayCount; i++) {
            dateMonthList.add(year + "-" + (month + 1) + "-" + i);
        }
        return dateMonthList;
    }

    private void initViewData() {
        debugSwitch.setChecked(config.getDebug());
        if (config.getSignature() != null) {
            signatureEditText.setText(config.getSignature());
        }
        if (config.getCookie() != null) {
            String cookie = config.getCookie();
            if (cookie.length() > 40) {
                cookie = cookie.substring(0, 40);
            }
            cookieEditText.setText(cookie);
        }
        hpvTypeSpinner.setSelection(Config.getHpvStrByPosition(config.getPid()), true);
        hospitalIdEditText.setText(String.valueOf(config.getHid()));
//        pidEditText.setText(String.valueOf(config.getPid()));
        if (config.getDate() != null) {
            dateEditText.setText(config.getDate());
        }
    }

    private void doSeckill(String date) {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (isSubmit) {
            return;
        }
        Log.d(TAG, "正在抢购: " + date);
        ApiService.getSubDate(config, date)
                .subscribe(responseBody -> {
                    String encryptedData = responseBody.string();
                    boolean validate = JSONValidator.from(encryptedData).validate();
                    if (validate) {
                        return;
                    }
                    String json = DecryptUtils.decrypt(encryptedData, Const.IV, config.getSignature().substring(0, 16));
                    assert json != null;
                    Log.d(TAG, json);
                    JSONObject jsonObject = JSONObject.parseObject(json);
                    JSONArray list = jsonObject.getJSONArray(Const.LIST_KEY);
                    if (list == null || list.isEmpty()) {
                        Toast.makeText(MainActivity.this, "已经没苗了!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    for (int i = 0; i < list.size(); i++) {
                        JSONObject item = list.getJSONObject(i);
                        if (item.getIntValue(Const.QTY_KEY) > 0 && !isSubmit) {
                            String mxid = item.getString(Const.MXID_KEY);
                            doGetCookie(date, mxid);
                        }
                    }
                });
    }

    private void doGetCookie(String date, String mxid) {
        Log.d(TAG, "mxid: " + mxid);
        if (isSubmit) {
            return;
        }
        ApiService.getCaptcha(config, mxid)
                .subscribe(captchaRes -> {
                    String body = captchaRes.body().string();
                    String newCookie = captchaRes.headers().get("Set-Cookie");
                    if (StringUtils.isNoneBlank(newCookie) && !isSubmit) {
                        config.setCookie(newCookie);
                        doSave20(date, mxid);
                    } else {
                        Log.d(TAG, body);
                        Log.d(TAG, captchaRes.headers().toString());
                        if (body.contains("预约已满")) {
                            Toast.makeText(MainActivity.this, body, Toast.LENGTH_SHORT).show();
                        }
//                        Toast.makeText(MainActivity.this, "获取token失败", Toast.LENGTH_SHORT).show();
//                        doSave20(date, mxid);
                    }
                });
    }

    private void doSave20(String date, String mxid) {
//        Toast.makeText(MainActivity.this, "正在提交订单: " + date, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "doSave20: 正在提交订单");
        ApiService.save20(config, mxid, date)
                .subscribe(body -> {
                    String json = body.string();
                    Log.d(TAG, json);
                    JSONObject jsonObject = JSONObject.parseObject(json);
                    if (jsonObject.getIntValue("status") == 200) {
//                        showSuccessDialog();
                        Toast.makeText(MainActivity.this, "订单提交成功!", Toast.LENGTH_SHORT).show();
//                        isSubmit = true;
//                        startSeckillButton.setEnabled(false);
                    } else if (jsonObject.getIntValue("status") == 201) {
//                        clearDateEditText();
//                        doSave20(date, mxid);
                    }
                });
    }

    private void clearDateEditText() {
        dateEditText.setText("");
        config.setDate(null);
        XposedUtils.writeConfig(config);
    }

    private void init() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS_CODE);
        } else {
            config = XposedUtils.readConfig();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS_CODE) {
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "请授予存储权限!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                config = XposedUtils.readConfig();
            }
        }
    }
}
