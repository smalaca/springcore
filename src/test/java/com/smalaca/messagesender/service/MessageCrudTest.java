package com.smalaca.messagesender.service;

import com.smalaca.messagesender.domain.MessageFactory;
import com.smalaca.messagesender.repository.inmemory.MessageRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.any;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest public class MessageCrudTest {
    @Autowired private MessageRepository messageRepository;
    @Autowired private MessageCrud messageCrud;

    @Test
    public void shouldCreateNewMessage() {
        String subject = "subject";
        String body = "body";
        String from = "from";
        String to = "to";

        MessageDto messageDto = new MessageDto();
        messageDto.setBody(body);
        messageDto.setSubject(subject);
        messageDto.setFrom(from);
        messageDto.setTo(to);

        Response response = messageCrud.createNew(messageDto);

        Assert.assertTrue(response.isSuccess());
        Assert.assertThat(response.getMessage(), any(String.class));
        Assert.assertTrue(messageRepository.exists(response.getMessage()));
    }

    @Test
    public void shouldNotCreateNewMessage() {
        String subject = "subject";
        String body = "body";
        String from = "from";
        String to = "to";
        MessageDto messageDto = new MessageDto();
        messageDto.setBody(body);
        messageDto.setSubject(subject);
        messageDto.setFrom(from);
        messageDto.setTo(to);

        messageRepository.add(new MessageFactory().createFrom(messageDto));

        Response response = messageCrud.createNew(messageDto);

        Assert.assertFalse(response.isSuccess());
    }
}
