package org.crown.common.utils;

import javax.servlet.http.HttpServletRequest;

import org.crown.framework.spring.ApplicationUtils;
import org.crown.framework.springboot.properties.CrownProperties;
import org.crown.framework.springboot.properties.Email;
import org.crown.framework.springboot.properties.Generator;
import org.crown.framework.springboot.properties.Xss;
import org.crown.framework.utils.RequestUtils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * <p>Crowns</p>
 * <p>Crown project configuration tools</p>
 *
 * @author Caratacus
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class Crowns {

    /**
     * Get the download address according to the file name
     *
     * @param filename file name
     * @return
     */
    public static String getDownloadPath(String filename) {
        CrownProperties property = getProperties();
        return property.getPath().getFilePath() + property.getPath().getPrefix().getDownload() + filename;
    }

    /**
     * Get the upload path of the avatar
     *
     * @return
     */
    public static String getAvatarUploadPath() {
        CrownProperties property = getProperties();
        return property.getPath().getFilePath() + property.getPath().getPrefix().getAvatar();
    }

    /**
     * Get the upload path of the avatar
     *
     * @return
     */
    public static String getUploadPath() {
        CrownProperties property = getProperties();
        return property.getPath().getFilePath() + property.getPath().getPrefix().getUpload();
    }

    /**
     * Get the upload path of the avatar
     *
     * @return
     */
    public static String getUploadResourcePath(String filename) {
        CrownProperties property = getProperties();
        return property.getPath().getResourcePath() + property.getPath().getPrefix().getUpload() + filename;
    }

    /**
     * Get the upload path of the avatar
     *
     * @return
     */
    public static String getUploadUrl(HttpServletRequest request, String filename) {
        return RequestUtils.getDomain(request) + getUploadResourcePath(filename);
    }

    /**
     * Get the maximum number of incorrect input of user password
     *
     * @return
     */
    public static int getMaxRetryCount() {
        return getProperties().getPassword().getMaxRetryCount();
    }

    /**
     * Get related configuration of generated code
     *
     * @return
     */
    public static Generator getGenerator() {
        return getProperties().getGenerator();
    }

    /**
     * Get Xss configuration
     *
     * @return
     */
    public static Xss getXss() {
        return getProperties().getXss();
    }

    /**
     * Get email configuration
     *
     * @return
     */
    public static Email getEmail() {
        return getProperties().getEmail();
    }

    /**
     * Get Crown Properties
     *
     * @return
     */
    public static CrownProperties getProperties() {
        return ApplicationUtils.getBean(CrownProperties.class);
    }

}
