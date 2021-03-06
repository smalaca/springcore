package com.smalaca.messagesender.service;

import com.smalaca.messagesender.domain.Message;
import com.smalaca.messagesender.domain.MessageFactory;
import com.smalaca.messagesender.domain.MessageRepository;
import com.smalaca.messagesender.exceptions.inmemory.MessageDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageCrud {
    private final MessageRepository messageRepository;

    @Autowired
    public MessageCrud(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Response createNew(MessageDto messageDto) {
        Message message = new MessageFactory().createFromWithoutId(messageDto);

        if (!messageRepository.exists(message)) {
            message.setId(new UniqueIdFactory().genarateId());
            messageRepository.add(message);

            return Response.aSuccessfulResponseWith(message.getId());
        }

        return Response.aFailureResponse("Message already exists");
    }

    public Response deleteMessage(String messageId) {
        if (messageRepository.exists(messageId)) {
            messageRepository.delete(messageId);
            return Response.aSuccessfulResponse();
        } else {
            try {
                messageRepository.delete(messageId);
            } catch (MessageDoesNotExistException messageDoesNotExistException) {
                return Response.aFailureResponse(messageDoesNotExistException.getMessage());
            }
            return Response.aFailureResponse("Some unexpected error occurred!");
        }
    }

    public List<Message> getAllMessages() {
        return messageRepository.getMessages();
    }
}
