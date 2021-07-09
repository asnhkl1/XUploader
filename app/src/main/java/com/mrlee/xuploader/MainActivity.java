package com.mrlee.xuploader;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.mrlee.library.ImageBean;
import com.mrlee.library.ResultData;
import com.mrlee.library.XUploader;

import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        XXPermissions.with(MainActivity.this)
                .permission(Permission.Group.STORAGE)
                .request(new OnPermissionCallback() {

                    @Override
                    public void onGranted(List<String> permissions, boolean all) {

                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        if (never) {
                            toast("被永久拒绝授权，请手动授予权限");
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(MainActivity.this, permissions);
                        } else {
                            toast("获取权限失败");
                        }
                    }
                });
        XUploader uploader = findViewById(R.id.uploader);
        Button getData = findViewById(R.id.getData);
        TextView log = findViewById(R.id.log);

        getData.setOnClickListener(v->{
            StringBuilder sb =new StringBuilder();
            List<ResultData> imageData = uploader.getImageData();
            for (int i = 0; i < imageData.size(); i++) {
                sb.append("图"+i+": "+ imageData.get(i).getThumbnail()+"\r\n" );
            }
            log.setText(sb.toString());
        });

    }
    private void toast(String s) {
        Toast.makeText(this, ""+s, Toast.LENGTH_SHORT).show();
    }
}
