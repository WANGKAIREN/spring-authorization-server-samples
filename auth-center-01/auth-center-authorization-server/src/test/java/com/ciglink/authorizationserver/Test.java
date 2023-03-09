package com.ciglink.authorizationserver;

import cn.hutool.core.util.StrUtil;
import org.springframework.util.StringUtils;

/**
 * @author WANGKairen
 * @since 2023-01-30 17:59:33
 **/
public class Test {

    public static void main(String[] args) {
        System.out.println(StringUtils.countOccurrencesOf("token.token", "."));
        System.out.println(StrUtil.count("token.token", "."));
    }
}
