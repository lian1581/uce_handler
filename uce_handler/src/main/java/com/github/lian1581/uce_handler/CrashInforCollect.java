/*
 * 设备信息收集
 * Copyright (C) 2020 lian1581
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.github.lian1581.uce_handler;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import java.util.Locale;

public class CrashInforCollect
{
    static String collectResults;

    /** 返回收集的设备信息 */
    public static Spanned getCollectResults(Context ctx)
    {
        String language=getLanguage(),
            systemVersionNumber=getSystemVersionNumber(),
            modelOfDevice=getModelOfDevice(),
            deviceName=getDeviceName(),
            deviceManufacturer=getDeviceManufacturer(),
            motherboardName=getMotherboardName(),
            deviceManufacturerName=getDeviceManufacturerName();
        return Html.fromHtml(ctx.getString(R.string.format_device_infor, deviceManufacturerName, deviceManufacturer, modelOfDevice,
                                           deviceName, motherboardName, systemVersionNumber, language));
    }

    /** 获取当前语言 */
    public static String getLanguage()
    {
        return Locale.getDefault().getLanguage();
    }

    /** 获取系统版本号 */
    public static String getSystemVersionNumber()
    {
        return android.os.Build.VERSION.RELEASE;
    }

    /** 设备型号 */
    public static String getModelOfDevice()
    {
        return android.os.Build.MODEL;
    }

    /** 设备名 */
    public static String getDeviceName()
    {
        return Build.DEVICE;
    }

    /** 设备厂商 */
    public static String getDeviceManufacturer()
    {
        return android.os.Build.BRAND;
    }

    /** 主板名 */
    public static String getMotherboardName()
    {
        return Build.BOARD;
    }

    /** 设备厂商名 */
    public static String getDeviceManufacturerName()
    {
        return Build.MANUFACTURER;
    }

}
