package org.crown.framework.consolelog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Page console log entity
 *
 * @author Caratacus
 * @link https://cloud.tencent.com/developer/article/1096792
 */
@Setter
@Getter
@AllArgsConstructor
public class ConsoleLog {

    public static final String VIEW_PERM = "monitor:consolelog:view";

    /**
     * Log content
     */
    private String body;
    /**
     * Timestamp
     */
    private String timestamp;
    /**
     * File name
     */
    private String fileName;
    /**
     * Line number
     */
    private int lineNumber;
    /**
     * Thread name
     */
    private String threadName;
    /**
     * Log level
     */
    private String level;
}
