package com.example.ihb.aleksandryurkovskiy.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.ihb.aleksandryurkovskiy.R;
import com.example.ihb.aleksandryurkovskiy.utils.ConstantManager;

/**
 * Created by ihb on 14.10.16.
 */

public class BaseActivity extends AppCompatActivity {
    static final String TAG = ConstantManager.TAG_PREFIX+"BaseActivity";
    protected ProgressDialog mProgressDialog;

    public void showProgress(){
        if(mProgressDialog==null){
            mProgressDialog=new ProgressDialog(this, R.style.custom_dialog);
            mProgressDialog.setCancelable(false);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        mProgressDialog.show();
        mProgressDialog.setContentView(R.layout.progress_splash);
    }

    public void hideProgress(){
        if(mProgressDialog!=null&&mProgressDialog.isShowing()){
            mProgressDialog.hide();
        }
    }
}
