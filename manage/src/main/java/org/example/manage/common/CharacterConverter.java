package org.example.manage.common;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.CellData;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

public class CharacterConverter implements Converter<Character> {
    // 导出时：Character → String（核心修正点）
    @Override
    public WriteCellData<String> convertToExcelData(Character value,
                                                    ExcelContentProperty contentProperty,
                                                    GlobalConfiguration globalConfiguration) throws Exception {
        return new WriteCellData<>(value == null ? "" : value.toString());
    }

//    @Override
//    public Character convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
//        return Converter.super.convertToJavaData(cellData, contentProperty, globalConfiguration);
//    }
//

//     导入时：String → Character（严格校验）


    @Override
    public Character convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        String strValue = cellData.getStringValue();


        int length = strValue.length();

        // 字符串转 Character 的逻辑
        if (length == 0) {
            return null; // 空字符串返回 null
        } else if (length == 1) {
            return strValue.charAt(0); // 单个字符直接返回
        } else {
            // 长度 > 1 的处理策略（常见选择：取第一个字符，或抛出异常）
            // 方案 1：取第一个字符（Excel 常见场景，用户可能误输入多字符）
            return strValue.charAt(0);
            // 方案 2：严格校验（抛出异常，要求必须为单个字符）
            // throw new IllegalArgumentException("String length must be 0 or 1 when converting to Character, but got: " + length);
        }
    }


    @Override
    public Class<Character> supportJavaTypeKey() {
        return Character.class; // 必须与实体类类型完全一致
    }


    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
         return CellDataTypeEnum.STRING;
    }


}