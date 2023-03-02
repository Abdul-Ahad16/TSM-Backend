package com.stg.tsm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.stg.tsm.service.TaskMasterService;

@Controller
@RequestMapping("/task/excel")
public class ExcelSheetGeneratorController {

    
    @Autowired TaskMasterService service;
   
}
