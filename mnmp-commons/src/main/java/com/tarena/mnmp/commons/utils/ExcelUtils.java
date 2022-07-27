package com.tarena.mnmp.commons.utils;

import java.util.HashSet;
import java.util.Set;
import org.springframework.util.StringUtils;

public class ExcelUtils {

    final static Set<String> EXCEL_SUFFIXS = new HashSet<>();

    static {
        EXCEL_SUFFIXS.add("xls");
        EXCEL_SUFFIXS.add("xlsx");
    }


    public static boolean checkExcel(String name) {
        if (StringUtils.isEmpty(name)) {
            return false;
        }

        int index = name.indexOf(".");
        if (index == -1) {
            return false;
        }
        return EXCEL_SUFFIXS.contains(name.substring(index + 1));
    }

    public static String suffixName(String name) {
        return name.substring(name.indexOf(".") + 1);
    }

}
