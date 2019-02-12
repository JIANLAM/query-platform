package com.zt.queryplatform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 公用页面控制层
 * created by linzj on 2018/12/18
 **/

@Controller
public class HomeController {
    @GetMapping(value = {"/", "/index"})
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/404")
    public String notFoundPage() {
        return "404";
    }

    @GetMapping("/403")
    public String accessError() {
        return "403";
    }

    @GetMapping("/500")
    public String internalError() {
        return "500";
    }

    @GetMapping("/logout/page")
    public String logoutPage() {
        return "logout";
    }

    /**
     * 图书查询跳转页面
     */
    @GetMapping("/searchBook")
    public String searchBook() {
        return "searchBook";
    }

    /**
     * 图书查询跳转页面
     */
    @GetMapping("/readerRecommendation")
    public String readerRecommendation() {
        return "readerRecommendation";
    }

    /**
     * 最新图书跳转页面
     */
    @GetMapping("/newBook")
    public String newBook() {
        return "newBook";
    }

    /**
     * 我的图书馆跳转页面
     */
    @GetMapping("/myLibrary")
    public String myLibrary() {
        return "myLibrary";
    }

    /**
     * 馆内资讯跳转页面
     */
    @GetMapping("/libraryInformation")
    public String libraryInformation() {
        return "libraryInformation";
    }

    /**
     * 排行榜跳转页面
     */
    @GetMapping("/ranking")
    public String ranking() {
        return "ranking";
    }
}
