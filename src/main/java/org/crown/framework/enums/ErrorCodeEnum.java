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
package org.crown.framework.enums;

import javax.servlet.http.HttpServletResponse;

import org.crown.common.exception.UnknownEnumException;
import org.crown.framework.model.ErrorCode;

/**
 * Business exception enumeration
 *
 * @author Caratacus
 */
public enum ErrorCodeEnum {

    /**
     * 400
     */
    BAD_REQUEST(HttpServletResponse.SC_BAD_REQUEST, "Incorrect request parameters"),
    /**
     * JSON format error
     */
    JSON_FORMAT_ERROR(HttpServletResponse.SC_BAD_REQUEST, "JSON format error"),
    /**
     * 401
     */
    UNAUTHORIZED(HttpServletResponse.SC_UNAUTHORIZED, "Please authenticate first"),
    /**
     * 403
     */
    FORBIDDEN(HttpServletResponse.SC_FORBIDDEN, "No authorization to operate, if you need authorization, please contact the administrator"),
    /**
     * 404
     */
    NOT_FOUND(HttpServletResponse.SC_NOT_FOUND, "Not found"),
    /**
     * 405
     */
    METHOD_NOT_ALLOWED(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Requested method not supported"),
    /**
     * 406
     */
    NOT_ACCEPTABLE(HttpServletResponse.SC_NOT_ACCEPTABLE, "Unacceptable request"),
    /**
     * 411
     */
    LENGTH_REQUIRED(HttpServletResponse.SC_LENGTH_REQUIRED, "Request length is limited"),
    /**
     * 415
     */
    UNSUPPORTED_MEDIA_TYPE(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, "Media type not supported by the request"),
    /**
     * 416
     */
    REQUESTED_RANGE_NOT_SATISFIABLE(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE, "The request is not within the scope of the request"),
    /**
     * 500
     */
    INTERNAL_SERVER_ERROR(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Something went wrong with the server, sorry"),
    /**
     * 503
     */
    SERVICE_UNAVAILABLE(HttpServletResponse.SC_SERVICE_UNAVAILABLE, "Request timed out"),
    /**
     * Message exception
     */
    MSG_EXCEPTION(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Default message exception"),
    //----------------------------------------------------业务异常----------------------------------------------------
    /**
     * Dept
     */
    DEPT_EXISTING_LOWER_LEVEL_DEPT(HttpServletResponse.SC_BAD_REQUEST, "The current department has a child department which must not be deleted"),
    DEPT_EXISTING_USER(HttpServletResponse.SC_BAD_REQUEST, "There are still users in the current department who must not be deleted"),
    DEPT_NAME_EXIST(HttpServletResponse.SC_BAD_REQUEST, "Department name already exists"),
    DEPT_PARENT_DEPT_CANNOT_MYSELF(HttpServletResponse.SC_BAD_REQUEST, "The parent department cannot be the current department"),
    /**
     * User
     */
    USER_OLD_PASSWORD_ERROR(HttpServletResponse.SC_BAD_REQUEST, "Failed to modify the password, the old password is wrong"),
    USER_AVATAR_NOT_EMPTY(HttpServletResponse.SC_BAD_REQUEST, "User avatar cannot be empty"),
    USER_AVATAR_UPLOAD_FAIL(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "User avatar upload failed"),
    USER_CANNOT_UPDATE_SUPER_ADMIN(HttpServletResponse.SC_BAD_REQUEST, "Can not modify the super administrator"),
    USER_ACCOUNT_EXIST(HttpServletResponse.SC_BAD_REQUEST, "Account already exists"),
    USER_PHONE_EXIST(HttpServletResponse.SC_BAD_REQUEST, "Phone number already exists"),
    USER_EMAIL_EXIST(HttpServletResponse.SC_BAD_REQUEST, "Email address already exists"),
    USER_NOT_ONLINE(HttpServletResponse.SC_BAD_REQUEST, "User is offline"),
    USER_CANNOT_RETREAT_CURRENT_ACCOUNT(HttpServletResponse.SC_BAD_REQUEST, "The currently logged-in user cannot be forced to log out"),
    USER_ELSEWHERE_LOGIN(HttpServletResponse.SC_UNAUTHORIZED, "You have logged in elsewhere, please change your password or log in again"),
    USER_USERNAME_OR_PASSWORD_IS_WRONG(HttpServletResponse.SC_BAD_REQUEST, "Username or password is wrong"),

    /**
     * Menu
     */
    MENU_EXISTING_LOWER_LEVEL_MENU(HttpServletResponse.SC_BAD_REQUEST, "There are sub-menus in the current menu, which cannot be deleted"),
    MENU_EXISTING_USING(HttpServletResponse.SC_BAD_REQUEST, "The menu has been used and cannot be deleted"),
    MENU_NAME_EXIST(HttpServletResponse.SC_BAD_REQUEST, "Menu name already exists"),
    /**
     * File
     */
    FILE_UPLOAD_FAIL(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "File upload failed"),
    FILE_DOWNLOAD_FAIL(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "File download failed"),
    FILE_ILLEGAL_FILENAME(HttpServletResponse.SC_BAD_REQUEST, "File name is illegal, download is not allowed"),
    /**
     * Job
     */
    JOB_NOT_FOUND(HttpServletResponse.SC_BAD_REQUEST, "The scheduled task was not found"),
    /**
     * Config
     */
    CONFIG_KEY_EXIST(HttpServletResponse.SC_BAD_REQUEST, "Parameter key already exists"),
    /**
     * Dict
     */
    DICT_TYPE_EXIST(HttpServletResponse.SC_BAD_REQUEST, "Dictionary type already exists"),
    /**
     * Post
     */
    POST_NAME_EXIST(HttpServletResponse.SC_BAD_REQUEST, "Job name already exists"),
    POST_CODE_EXIST(HttpServletResponse.SC_BAD_REQUEST, "Post code already exists"),
    /**
     * Role
     */
    ROLE_NAME_EXIST(HttpServletResponse.SC_BAD_REQUEST, "Role name already exists"),
    ROLE_KEY_EXIST(HttpServletResponse.SC_BAD_REQUEST, "Role permissions already exist"),
    /**
     * Gen
     */
    GEN_IMPORT_TABLE_ERROR(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Generated code table import error"),

    ;

    private final int status;
    private final String msg;

    ErrorCodeEnum(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    /**
     * Convert to ErrorCode (custom return message)
     *
     * @param msg
     * @return
     */
    public ErrorCode overrideMsg(String msg) {
        return ErrorCode.builder().status(status()).error(name()).msg(msg).build();
    }

    /**
     * Convert to ErrorCode
     *
     * @return
     */
    public ErrorCode convert() {
        return ErrorCode.builder().status(status()).error(name()).msg(msg()).build();
    }

    public static ErrorCodeEnum getErrorCode(String errorCode) {
        ErrorCodeEnum[] enums = ErrorCodeEnum.values();
        for (ErrorCodeEnum errorCodeEnum : enums) {
            if (errorCodeEnum.name().equalsIgnoreCase(errorCode)) {
                return errorCodeEnum;
            }
        }
        throw new UnknownEnumException("Error: Unknown errorCode, or do not support changing errorCode!\n");
    }

    public int status() {
        return this.status;
    }

    public String msg() {
        return this.msg;
    }

}
