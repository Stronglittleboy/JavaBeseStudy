package com.hlw.java_base.util;

import cn.hutool.core.util.RuntimeUtil;

import java.util.List;

/**
 * <p>
 * 使用Hutool工具命令行和切换wifi
 *
 * @author hlw
 * @since 2021-08-09
 */
public class WifiTest {
    public static void main(String[] args) {
        List<String> strings =
                RuntimeUtil.execForLines("netsh wlan show networks mode=bssid");;
                strings.forEach(System.out::println);

    }
}
