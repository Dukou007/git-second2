package com.vesus.springbootwebsocket.controller;

import com.vesus.springbootwebsocket.model.RequestMessage;
import com.vesus.springbootwebsocket.model.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description:
 * @Author: vesus
 * @CreateDate: 2018/5/29 下午1:48
 * @Version: 1.0
 */
@Controller
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate ;

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/index2")
    public String index2(){
        return "index2";
    }

    @ResponseBody
    @RequestMapping("/test/{userid}")
    public String test(@PathVariable("userid") String userid, RequestMessage msg){
        this.messagingTemplate.convertAndSendToUser(userid,"/message", msg.getMessage());//" /user/123/message "
        return "发送成功！";
    }

    //  "/app/welcome
    @MessageMapping("/welcome")
    public ResponseMessage toTopic(RequestMessage msg) throws Exception {
        System.out.println("======================"+msg.getMessage());
        this.messagingTemplate.convertAndSend("/api/v1/socket/send", msg.getMessage());

        this.messagingTemplate.convertAndSend("/topic/send", msg.getMessage());

        return new ResponseMessage("欢迎使用webScoket："+msg.getMessage());
    }

    @MessageMapping("/message")
    public ResponseMessage toUser(RequestMessage msg) {
        System.out.println("----------------" + msg.getMessage());

        this.messagingTemplate.convertAndSendToUser(msg.getUserid().toString(),"/message", msg.getMessage());
        this.messagingTemplate.convertAndSendToUser(msg.getToUserId().toString(),"/message", msg.getMessage());

        return new ResponseMessage("欢迎使用webScoket："+msg.getMessage());
    }
}
