package com.xiaofo1022.b5235.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

  @RequestMapping("/")
  public String index() {
    return "index";
  }
  
  @RequestMapping("/showloc")
  public String showloc() {
    return "showloc";
  }
  
  @RequestMapping("/sreport")
  public String sreport() {
    return "sreport";
  }
  
  @RequestMapping("/wxmap")
  public String wxmap() {
    return "wxmap";
  }
  
  @RequestMapping("/whattheydo")
  public String whattheydo() {
    return "whattheydo";
  }
  
  @RequestMapping("/dorecord")
  public String dorecord() {
    return "dorecord";
  }
}
