package com.smalaca.messagesender.service;

import com.smalaca.messagesender.domain.MessageFactory;
import com.smalaca.messagesender.domain.MessageRepository;
import com.smalaca.messagesender.repository.inmemory.InMemoryMessageRepository;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.any;

public class MessageCrudTest {
    private static final String SUBJECT = "subject";
    private static final String BODY = "body";
    private static final String FROM = "from";
    private static final String TO = "to";

    private MessageDto getMessageDto() {
        MessageDto messageDto = new MessageDto();
        messageDto.setBody(BODY);
        messageDto.setSubject(SUBJECT);
        messageDto.setFrom(FROM);
        messageDto.setTo(TO);
        return messageDto;
    }

    private MessageRepository messageRepository = new InMemoryMessageRepository();
    private MessageCrud messageCrud = new MessageCrud(messageRepository);

    @Test
    public void shouldCreateNewMessage() {

        MessageDto messageDto = getMessageDto();

        Response response = messageCrud.createNew(messageDto);

        Assert.assertTrue(response.isSuccess());
        Assert.assertThat(response.getMessage(), any(String.class));
        Assert.assertTrue(messageRepository.exists(response.getMessage()));
    }

    @Test
    public void shouldNotCreateNewMessage() {

        MessageDto messageDto = getMessageDto();

        messageRepository.add(new MessageFactory().createFrom(messageDto));

        Response response = messageCrud.createNew(messageDto);

        Assert.assertFalse(response.isSuccess());
    }

    @Test
    public void shouldDeletePreviouslyCreatedNewMessage() {
        MessageDto messageDto = getMessageDto();
        Response responseFromCreation = messageCrud.createNew(messageDto);

        Response response = messageCrud.deleteMessage(responseFromCreation.getMessage());

        Assert.assertTrue(response.isSuccess());
        Assert.assertFalse(messageRepository.exists(responseFromCreation.getMessage()));
    }

    @Test
    public void shouldNotDeletePreviouslyCreatedNewMessage() {

        MessageDto messageDto = getMessageDto();
        Response responseFromCreation = messageCrud.createNew(messageDto);

        Response response = messageCrud.deleteMessage(responseFromCreation.getMessage().toUpperCase());

        Assert.assertFalse(response.isSuccess());
        Assert.assertTrue(messageRepository.exists(responseFromCreation.getMessage()));
    }

    @Test
    public void shouldReturnListOfMessagesThatIsNotEmpty() {
        MessageDto messageDto = getMessageDto();

        messageCrud.createNew(messageDto);

        Assert.assertFalse(messageRepository.getMessages().isEmpty());
    }

    @Test
    public void shouldReturnListOfMessagesWithNoElement() {
        Assert.assertTrue(messageRepository.getMessages().isEmpty());
    }

    @Test
    public void shouldUpdateMessageWhenExist() {
        MessageDto messageDto = new MessageDto();
        messageDto.setSubject("some subject 2");
        messageDto.setBody("some body 2");
        messageDto.setTo("smalaca");
        messageDto.setFrom("javakrk5");

        Message message = new MessageFactory().createFrom(messageDto, "5");
        messageRepository.add(message);

        Assert.assertTrue(messageCrud.updateMessage("5", messageDto).isSuccess());
        Assert.assertEquals(messageCrud.updateMessage("5", messageDto).getMessage(), "Message updated");
    }

    @Test
    public void shouldNotUpdateMessageWhenNoPresent() {
        MessageDto messageDto = new MessageDto();
        messageDto.setSubject("some subject 2");
        messageDto.setBody("some body 2");
        messageDto.setTo("smalaca");
        messageDto.setFrom("javakrk5");

        Assert.assertFalse(messageCrud.updateMessage("5", messageDto).isSuccess());
        Assert.assertEquals(messageCrud.updateMessage("5", messageDto).getMessage(), "Message doent't exist");
    }
}
