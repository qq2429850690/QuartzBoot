package com.pactera.util.reportTool;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 定义单元格实体类
 * Created by hx on 2020/12/24
 */
@Data
public class Column {
    //单元格内容
    private String content;
    //字段名称，用户导出表格时反射调用
    private String fieldName;
    //这个单元格的集合
    private List<Column> listTpamscolumn = new ArrayList<Column>();

    int totalRow;
    int totalCol;
    int row;//excel第几行
    int col;//excel第几列
    int rLen; //excel 跨多少行
    int cLen;//excel跨多少列
    private boolean HasChilren;//是否有子节点
    private int tree_step;//树的级别 从0开始
    private String id;
    private String pid;
}
