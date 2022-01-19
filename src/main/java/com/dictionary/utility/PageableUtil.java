package com.dictionary.utility;

public class PageableUtil {

    private PageableUtil() {
    }

    public static int getPage(String pageAsString) {
        if (pageAsString == null || pageAsString.isEmpty() || pageAsString.equals("undefined")) {
            return 0;
        }
        int page = Integer.parseInt(pageAsString);
        if (page == 0) {
            return page;
        }
        return page - 1;
    }

    public static int getPageSize(String sizeAsString) {
        if (sizeAsString == null || sizeAsString.isEmpty() || sizeAsString.equals("undefined")) {
            return 10;
        }
        int size = Integer.parseInt(sizeAsString);
        if (size <= 0) {
            return 10;
        }
        return size;
    }

}
