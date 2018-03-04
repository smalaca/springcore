package com.smalaca.messagesender.repository.inmemory;

import com.smalaca.messagesender.domain.Message;
import com.smalaca.messagesender.domain.MessageRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryMessageRepository implements MessageRepository {
    private List<Message> messages;

    public InMemoryMessageRepository() {
        this(new ArrayList<>());
    }

    public InMemoryMessageRepository(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public boolean exists(String id) {
        return messages
                .stream()
                .anyMatch(message -> message.hasSameId(id));
    }

    @Override
    public void add(Message message) {
        messages.add(message);
    }

    @Override
    public boolean exists(Message message) {
        return messages.contains(message);
    }

    @Override
    public Message getMessageById(String id) {
        return messages.stream()
                .filter(message -> message.hasSameId(id))
                .findAny()
                .get();

    }

    @Override
    public void delete(String messageId) {
        int index = -1;
        for (Message message : messages) {
            if (message.hasSameId(messageId)) {
                index = messages.indexOf(message);
            }
        }

        messages.remove(index);
    }

    @Override
    public List<Message> getMessages() {
        return messages;
    }
}
