package org.crown.project.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Modal window
 *
 * @author Crown
 */
@Controller
@RequestMapping("/demo/modal")
public class DemoDialogController {

    private final String prefix = "demo/modal";

    /**
     * Modal window
     */
    @GetMapping("/dialog")
    public String dialog() {
        return prefix + "/dialog";
    }

    /**
     * Elastic component
     */
    @GetMapping("/layer")
    public String layer() {
        return prefix + "/layer";
    }

    /**
     * Form
     */
    @GetMapping("/form")
    public String form() {
        return prefix + "/form";
    }

    /**
     * Table
     */
    @GetMapping("/table")
    public String table() {
        return prefix + "/table";
    }

    /**
     * Table check
     */
    @GetMapping("/check")
    public String check() {
        return prefix + "/table/check";
    }

    /**
     * Table radio
     */
    @GetMapping("/radio")
    public String radio() {
        return prefix + "/table/radio";
    }

    /**
     * Table returns to parent table
     */
    @GetMapping("/parent")
    public String parent() {
        return prefix + "/table/parent";
    }
}
