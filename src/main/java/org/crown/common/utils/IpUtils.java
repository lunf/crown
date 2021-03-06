/*
 * Copyright (c) 2018-2022 Caratacus, (caratacus@qq.com).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.crown.common.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.crown.common.utils.http.OkHttps;

import com.alibaba.fastjson.JSONObject;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * Get IP address class
 * </p>
 *
 * @author Caratacus
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public abstract class IpUtils {

    /**
     * Get the address interface path of the IP address
     */
    public static final String IP_URL = "http://ip.taobao.com/service/getIpInfo.php";

    /**
     * <p>
     * The method to obtain the client's IP address is: requestBody.getRemoteAddr(), which is effective in most cases.
     * But after passing Apache, Squid's reverse proxy software cannot obtain the real IP address of the client. If it passes the multi-level reverse proxy,
     * X-Forwarded-For has more than one value, but a string of IP values. Which one is the real IP of the real client?
     * The answer is to take the first non-unknown valid IP string in X-Forwarded-For???
     * E.g.???X-Forwarded-For???192.168.1.110, 192.168.1.120,
     * 192.168.1.130, 192.168.1.100 The user's real IP is??? 192.168.1.110
     * </p>
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        // Real user ip obtained by nginx proxy
        String ip = request.getHeader("X-Real-IP");
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        /*
         In the case of multiple proxies, the first IP is the real IP of the client, and multiple IPs are divided according to',' "***.***.***.***".length() =
          15
         */
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }

    public static String getHostIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ignored) {
        }
        return "127.0.0.1";
    }

    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException ignored) {
        }
        return "unknown";
    }

    public static boolean internalIp(String ip) {
        byte[] addr = textToNumericFormatV4(ip);
        return isLocalIp(ip) || internalIp(Objects.requireNonNull(addr));
    }

    /**
     * Whether it is a local ip
     *
     * @param ipAddr
     * @return
     */
    public static boolean isLocalIp(String ipAddr) {
        return "127.0.0.1".equals(ipAddr) || "0:0:0:0:0:0:0:1".equals(ipAddr);
    }

    private static boolean internalIp(byte[] addr) {
        final byte b0 = addr[0];
        final byte b1 = addr[1];
        // 10.x.x.x/8
        final byte SECTION_1 = 0x0A;
        // 172.16.x.x/12
        final byte SECTION_2 = (byte) 0xAC;
        final byte SECTION_3 = (byte) 0x10;
        final byte SECTION_4 = (byte) 0x1F;
        // 192.168.x.x/16
        final byte SECTION_5 = (byte) 0xC0;
        final byte SECTION_6 = (byte) 0xA8;
        switch (b0) {
            case SECTION_1:
                return true;
            case SECTION_2:
                if (b1 >= SECTION_3 && b1 <= SECTION_4) {
                    return true;
                }
            case SECTION_5:
                if (b1 == SECTION_6) {
                    return true;
                }
            default:
                return false;
        }
    }

    /**
     * Convert IPv4 address to bytes
     *
     * @param text IPv4 address
     * @return byte byte
     */
    public static byte[] textToNumericFormatV4(String text) {
        if (text.length() == 0) {
            return null;
        }

        byte[] bytes = new byte[4];
        String[] elements = text.split("\\.", -1);
        try {
            long l;
            int i;
            switch (elements.length) {
                case 1:
                    l = Long.parseLong(elements[0]);
                    if ((l < 0L) || (l > 4294967295L))
                        return null;
                    bytes[0] = (byte) (int) (l >> 24 & 0xFF);
                    bytes[1] = (byte) (int) ((l & 0xFFFFFF) >> 16 & 0xFF);
                    bytes[2] = (byte) (int) ((l & 0xFFFF) >> 8 & 0xFF);
                    bytes[3] = (byte) (int) (l & 0xFF);
                    break;
                case 2:
                    l = Integer.parseInt(elements[0]);
                    if ((l < 0L) || (l > 255L))
                        return null;
                    bytes[0] = (byte) (int) (l & 0xFF);
                    l = Integer.parseInt(elements[1]);
                    if ((l < 0L) || (l > 16777215L))
                        return null;
                    bytes[1] = (byte) (int) (l >> 16 & 0xFF);
                    bytes[2] = (byte) (int) ((l & 0xFFFF) >> 8 & 0xFF);
                    bytes[3] = (byte) (int) (l & 0xFF);
                    break;
                case 3:
                    for (i = 0; i < 2; ++i) {
                        l = Integer.parseInt(elements[i]);
                        if ((l < 0L) || (l > 255L))
                            return null;
                        bytes[i] = (byte) (int) (l & 0xFF);
                    }
                    l = Integer.parseInt(elements[2]);
                    if ((l < 0L) || (l > 65535L))
                        return null;
                    bytes[2] = (byte) (int) (l >> 8 & 0xFF);
                    bytes[3] = (byte) (int) (l & 0xFF);
                    break;
                case 4:
                    for (i = 0; i < 4; ++i) {
                        l = Integer.parseInt(elements[i]);
                        if ((l < 0L) || (l > 255L))
                            return null;
                        bytes[i] = (byte) (int) (l & 0xFF);
                    }
                    break;
                default:
                    return null;
            }
        } catch (NumberFormatException e) {
            return null;
        }
        return bytes;
    }

    /**
     * Get the address of the IP address
     *
     * @param ip
     * @return
     */
    public static String getRealAddress(String ip) {
        String address = "Address not obtained";
        // Intranet does not query
        if (IpUtils.internalIp(ip)) {
            return "Intranet IP";
        }
        String repoStr = OkHttps.post(IP_URL,
                Maps.<String, String>builder()
                        .put("accept", "*/*")
                        .put("connection", "Keep-Alive")
                        .put("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)")
                        .put("Accept-Charset", "utf-8")
                        .put("contentType", "utf-8")
                        .build(),
                Maps.<String, String>builder()
                        .put("ip", ip)
                        .build());
        if (org.crown.common.utils.StringUtils.isNotEmpty(repoStr)) {
            JSONObject jsonObject = JSONObject.parseObject(repoStr);
            if (jsonObject.getIntValue("code") == 0) {
                JSONObject data = jsonObject.getJSONObject("data");
                String country = data.getString("country");
                String region = data.getString("region");
                String city = data.getString("city");
                address = country + "-" + region + "-" + city;
            }
        }
        return address;
    }
}