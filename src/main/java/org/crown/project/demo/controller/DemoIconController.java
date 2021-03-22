package org.crown.project.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Icon related
 *
 * @author Crown
 */
@Controller
@RequestMapping("/demo/icon")
public class DemoIconController {

    private final String prefix = "demo/icon";

    /**
     * FontAwesome icon
     */
    @GetMapping("/fontawesome")
    public String fontAwesome() {
        return prefix + "/fontawesome";
    }

    /**
     * Glyphicons icon
     */
    @GetMapping("/glyphicons")
    public String glyphicons() {
        return prefix + "/glyphicons";
    }
}
