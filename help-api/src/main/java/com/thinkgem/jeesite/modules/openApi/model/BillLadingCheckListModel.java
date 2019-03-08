package com.thinkgem.jeesite.modules.openApi.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @Description:
 * @Date: 2018/1/30
 * @Author: wcf
 */
public class BillLadingCheckListModel extends BaseModel{

    @NotNull
    @Size(min = 1,max = 100)
    private List<BillLadingCheckModel> list;

    public List<BillLadingCheckModel> getList() {
        return list;
    }

    public void setList(List<BillLadingCheckModel> list) {
        this.list = list;
    }
}
