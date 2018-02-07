package com.itz.testFEListview;

import java.util.List;

/**
 * Created by zhangshuo on 2018/2/6.
 */

public class Model {

    private int id;
    private String name;

    private List<SubModel> listSubModels;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SubModel> getListSubModels() {
        return listSubModels;
    }

    public void setListSubModels(List<SubModel> listSubModels) {
        this.listSubModels = listSubModels;
    }
}
