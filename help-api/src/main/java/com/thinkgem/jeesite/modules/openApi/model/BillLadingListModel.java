package com.thinkgem.jeesite.modules.openApi.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @Description:
 * @Date: 2018/1/30
 * @Author: wcf
 */
public class BillLadingListModel extends BaseModel{

    @NotNull
    @Size(min = 1,max = 100)
    private List<BillLadingModel> list;

    public List<BillLadingModel> getList() {
        return list;
    }

    public void setList(List<BillLadingModel> list) {
        this.list = list;
    }
}
