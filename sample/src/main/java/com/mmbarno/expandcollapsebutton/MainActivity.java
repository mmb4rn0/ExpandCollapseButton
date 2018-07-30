package com.mmbarno.expandcollapsebutton;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mmbarno.expandcollapsebtn.ExpandCollapseButton;

public class MainActivity extends AppCompatActivity {

    private ExpandCollapseButton btnY1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnY1 = findViewById(R.id.btn_y_1);
        findViewById(R.id.invertBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnY1.setInvertFlip(!btnY1.isInvertFlip());
            }
        });

        findViewById(R.id.flipAxesBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flipAxes = btnY1.getFlipAxis() == ExpandCollapseButton.FlipAxis.x ? ExpandCollapseButton.FlipAxis.y : ExpandCollapseButton.FlipAxis.x;
                btnY1.setFlipDrawableResource(flipAxes == ExpandCollapseButton.FlipAxis.x ? R.drawable.ic_checked_x : R.drawable.ic_checked_y);
                btnY1.setFlipAxis(flipAxes);
            }
        });

        findViewById(R.id.checkedChangeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnY1.setChecked(!btnY1.isChecked());
            }
        });
    }
}
