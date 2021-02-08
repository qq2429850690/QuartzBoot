package com.pactera.controller.param;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hx on 2021/1/29 10:16.
 */
@Data
@Accessors(chain = true)
public class CellDefineDTO {

    @NotNull(message = "单元格内容 不能为空")
    private String title;

    @NotEmpty(message = "单元格所占列数 不能为空")
    private Integer num;

    @NotEmpty(message = "单元格所在行号 不能为空")
    private Integer row;

    @NotEmpty(message = "单元格从第几列开始 不能为空")
    private Integer col;

    @NotEmpty(message = "单元格所占行数 不能为空")
    private Integer zhanRow;

    private Map<String,Object> cellMap = new HashMap<>();

    public Map getCellMap(){
        cellMap.put("title",this.title);
        cellMap.put("num",this.num);
        cellMap.put("row",this.row);
        cellMap.put("col",this.col);
        cellMap.put("zhanRow",this.zhanRow);
        return this.cellMap;
    }
}
