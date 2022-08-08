package org.cmyk.aifocus.utils;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: ZhanX
 * @Date: 2021-04-07 01:33:49
 */
public class ExcelAnswerCodeConverter implements Converter<Integer> {
    static Map<Integer,String> toExcelMap = new HashMap<>();
    static Map<String,Integer> toJavaMap = new HashMap<>();
    static {
        toExcelMap.put(0,"A");
        toExcelMap.put(1,"B");
        toExcelMap.put(2,"C");
        toExcelMap.put(3,"D");
        toExcelMap.put(4,"AB");
        toExcelMap.put(5,"AC");
        toExcelMap.put(6,"AD");
        toExcelMap.put(7,"BC");
        toExcelMap.put(8,"BD");
        toExcelMap.put(9,"CD");
        toExcelMap.put(10,"ABC");
        toExcelMap.put(11,"ABD");
        toExcelMap.put(12,"ACD");
        toExcelMap.put(13,"BCD");
        toExcelMap.put(14,"ABCD");

        toJavaMap.put("A",0);
        toJavaMap.put("B",1);
        toJavaMap.put("C",2);
        toJavaMap.put("D",3);
        toJavaMap.put("AB",4);
        toJavaMap.put("AC",5);
        toJavaMap.put("AD",6);
        toJavaMap.put("BC",7);
        toJavaMap.put("BD",8);
        toJavaMap.put("CD",9);
        toJavaMap.put("ABC",10);
        toJavaMap.put("ABD",11);
        toJavaMap.put("ACD",12);
        toJavaMap.put("BCD",13);
        toJavaMap.put("ABCD",14);
    }

    @Override
    public Class supportJavaTypeKey() {
        return Integer.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Integer convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return toJavaMap.get(cellData.getStringValue());
    }

    @Override
    public CellData convertToExcelData(Integer value, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return new CellData(toExcelMap.get(value));
    }
}
