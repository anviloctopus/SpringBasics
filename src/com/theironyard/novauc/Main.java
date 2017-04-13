package com.theironyard.novauc;


import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;
import java.util.*;

public class Main {
    static Users user;

    public static void main(String[] args) {

        Spark.init();

        Spark.get("/", ((request, response) -> {
                    HashMap m = new HashMap();
                    if (user == null || user.userName.length()==0) {
                        return new ModelAndView(m, "index.html");
                    } else {

                        m.put("name", user.userName);
                        m.put("messages", user.messages);
                        return new  ModelAndView(m, "messages.html");}
                }),new MustacheTemplateEngine()

        );
        Spark.post("/create-message", ((request, response) -> {
                    String oldMessage = request.queryParams("message");
                    Message messages = new Message(oldMessage);
                    user.messages.add(messages);
                    response.redirect("/");
                    return "";
                })

        );

        Spark.post("/login", ((request, response) -> {
                    String username = request.queryParams("username");
                    user = new Users(username);
                    response.redirect("/");
                    return "";
                })
        );
    }
}

