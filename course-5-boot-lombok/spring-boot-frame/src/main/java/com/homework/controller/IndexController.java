package com.homework.controller;

import com.homework.exception.MyException;
import com.sun.corba.se.spi.orbutil.threadpool.NoSuchThreadPoolException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class IndexController {



    @GetMapping("/")
    public String index() throws MyException {

        log.info("----------------------> lombok~~");
        throw new RuntimeException("cuo");
//        return "index";
    }

}
