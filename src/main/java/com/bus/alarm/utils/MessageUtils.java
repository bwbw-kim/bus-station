package com.bus.alarm.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bus.alarm.config.Config;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;

@Component
public class MessageUtils {
    @Autowired
    Config config;
    
    public void sendMessage(String sender, String receiver, String text){
        DefaultMessageService messageService = NurigoApp.INSTANCE.initialize(config.getMsgApiKey(), config.getMsgApiSecret(), config.getMsgApiUrl());
        Message message = new Message();
        message.setFrom(sender);
        message.setTo(receiver);
        message.setText(text);
        System.out.println(message);
        // SingleMessageSentResponse response = messageService.sendOne(new SingleMessageSendingRequest(message));
    }
}
