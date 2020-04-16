package com.ruppyrup.springcert;

import com.ruppyrup.springcert.beans.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Scope("prototype")
public class SpringController {

    @Autowired
    private SpringBean1 springBean1;
    @Autowired
    private SpringBean2 springBean2;
    @Autowired
    private SpringBean3 springBean3;
    @Autowired
    private SpringBean4 springBean4;
    @Autowired
    private SpringBean5 springBean5;

    @RequestMapping
    @ResponseBody
    public String index() {
        return "<h1>This is the spring cert test</h1><pre>" +
                springBean1 + " - Singleton\n" +
                springBean2 + " - Prototype\n" +
                springBean3 + " - Request Scope\n" +
                springBean4 + " - Session Scope\n" +
                springBean5 + " - Application Scope\n" +
                "</pre>";
    }
}
