package org.crown.framework.springboot.properties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Generator {

    /**
     * Author
     */
    private String author;
    /**
     * Default generated package path
     */
    private String packagePath;
}
