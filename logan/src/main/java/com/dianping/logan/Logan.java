/*
 * Copyright (c) 2018-present, 美团点评
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.dianping.logan;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Logan {

    private static OnLoganProtocolStatus sLoganProtocolStatus;
    private static LoganControlCenter sLoganControlCenter;
    static boolean sDebug = false;

    /**
     * @brief Logan初始化
     */
    public static void init(LoganConfig loganConfig) {
        sLoganControlCenter = LoganControlCenter.instance(loganConfig);
    }

    /**
     * @param log  表示日志内容
     * @param type 表示日志类型
     * @brief Logan写入日志
     */
    public static void w(String log, int type) {
        sLoganControlCenter.write(log, type);
    }

    /**
     * @brief 立即写入日志文件
     */
    public static void f() {
        sLoganControlCenter.flush();
    }

    /**
     * @param dates    日期数组，格式：“2018-07-27”
     * @param runnable 发送操作
     * @brief 发送日志
     */
    public static void s(String dates[], SendLogRunnable runnable) {
        sLoganControlCenter.send(dates, runnable);
    }

    /**
     * @brief 返回所有日志文件信息
     */
    public static Map<String, Long> getAllFilesInfo() {
        File dir = sLoganControlCenter.getDir();
        if (!dir.exists()) {
            return null;
        }
        File[] files = dir.listFiles();
        if (files == null) {
            return null;
        }
        Map<String, Long> allFilesInfo = new HashMap<>();
        for (File file : files) {
            try {
                allFilesInfo.put(Util.getDateStr(Long.parseLong(file.getName())), file.length());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return allFilesInfo;
    }

    /**
     * @brief Logan Debug开关
     */
    public static void setDebug(boolean debug) {
        Logan.sDebug = debug;
    }

    static void onListenerLogWriteStatus(String name, int status) {
        if (sLoganProtocolStatus != null) {
            sLoganProtocolStatus.loganProtocolStatus(name, status);
        }
    }

    public static void setOnLoganProtocolStatus(OnLoganProtocolStatus listener) {
        sLoganProtocolStatus = listener;
    }
}
