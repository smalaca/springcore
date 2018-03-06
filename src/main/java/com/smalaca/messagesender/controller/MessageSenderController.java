package com.smalaca.messagesender.controller;


import com.smalaca.messagesender.domain.MessageRepository;
import com.smalaca.messagesender.service.MessageCrud;
import com.smalaca.messagesender.service.MessageSenderService;
import com.smalaca.messagesender.service.Response;
import com.sun.xml.internal.ws.client.SenderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
public class MessageSenderController {

    private final MessageSenderService messageSenderService;
    private final MessageCrud messageCrud;
    private final MessageRepository messageRepository;

    @Autowired
    public MessageSenderController(
            MessageSenderService messageSenderService, MessageCrud messageCrud, MessageRepository messageRepository) {
        this.messageSenderService = messageSenderService;
        this.messageCrud = messageCrud;
        this.messageRepository = messageRepository;
    }

    @RequestMapping(value = "/send/{param}")
    public Response sendMessage(@PathVariable(value = "param") String id) {
        try {

            return messageSenderService.sendMessageViaEmail(id);

        } catch (SenderException ex) {
            throw new NoSuchElementException();
        }
    }
}