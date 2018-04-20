package me.liuningning.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import me.liuningning.inject.ViewInject;
import me.liuniningning.annotation.BindView;



public class MainActivity extends AppCompatActivity {
    private static  final String TAG = "MainActivity";

    @BindView(R.id.id_main_msg_view)
    TextView mMessageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewInject.inject(this);
        mMessageView.setText("annotation findViewById");
    }
}
