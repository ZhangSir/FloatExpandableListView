package com.itz.testFEListview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itz.floatexpandablelistview.FloatExpandableListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Model> listModels = null;

    private RelativeLayout rlContent;
    private FloatExpandableListView felv;

    private View floatView;
    private TextView tvFloat;

    private MainAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initData();
        this.initView();
        this.initListener();
        this.refreshView();
    }

    private void initData() {
        listModels = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Model model = new Model();
            model.setId(i);
            model.setName("name: " + i);
            List<SubModel> list = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                SubModel subModel = new SubModel();
                subModel.setId(j);
                subModel.setName("子条目: " + j);
                list.add(subModel);
            }
            model.setListSubModels(list);
            listModels.add(model);
        }
    }

    private void initView(){
        rlContent = findViewById(R.id.rl_main);
        felv = findViewById(R.id.felv_main);
        felv.setHeaderDividersEnabled(false);
        felv.setFooterDividersEnabled(false);
        felv.setGroupIndicator(null);
        this.initFloatView();
    }

    private void initFloatView(){
        floatView = getLayoutInflater().inflate(R.layout.layout_float, null);
        tvFloat = floatView.findViewById(R.id.tv_float);
        rlContent.addView(floatView);
        felv.setFloatView(floatView);
    }

    private void initListener(){
        felv.setListener(new FloatExpandableListView.OnFloatViewListener() {
            @Override
            public void onUpdate(int groupPosition) {
                updateFloatView(groupPosition);
            }

            @Override
            public void onClick(View v) {

            }
        });
    }

    private void refreshView(){
        adapter = new MainAdapter(this, listModels);
        felv.setAdapter(adapter);
    }

    private void updateFloatView(int position){
        Model model = listModels.get(position);
        tvFloat.setText(model.getName());
    }

}
