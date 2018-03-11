package com.smalaca.messagesender.repository.inmemory.jpa;


import com.smalaca.messagesender.domain.MessageFactory;
import com.smalaca.messagesender.domain.SlackStat;
import com.smalaca.messagesender.service.MessageDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SlackStatsRepositoryTestQuerry {

    @Autowired
    private SlackStatsRepository slackStatsRepository;


    @Before
    public void fillUpRepositoryWithBadStats() {
        MessageDto messageDto = new MessageDto();
        messageDto.setTo("message to");
        messageDto.setFrom("message from");

        SlackStat slackStat = new SlackStat(new MessageFactory().createWithoutId(messageDto));
        SlackStat slackStat1 = new SlackStat(new MessageFactory().createWithoutId(messageDto));
        SlackStat slackStat2 = new SlackStat(new MessageFactory().createWithoutId(messageDto));
        SlackStat slackStat3 = new SlackStat(new MessageFactory().createWithoutId(messageDto));
        SlackStat slackStat4 = new SlackStat(new MessageFactory().createWithoutId(messageDto));

        slackStatsRepository.save(slackStat);
        slackStatsRepository.save(slackStat1);
        slackStatsRepository.save(slackStat2);
        slackStatsRepository.save(slackStat3);
        slackStatsRepository.save(slackStat4);
    }

    @Test
    public void shouldFind5MatchesWhenExist() {
        MessageDto messageDto = new MessageDto();
        messageDto.setTo("andi");
        messageDto.setFrom("seba");

        SlackStat slackStat = new SlackStat(new MessageFactory().createWithoutId(messageDto));
        SlackStat slackStat1 = new SlackStat(new MessageFactory().createWithoutId(messageDto));
        SlackStat slackStat2 = new SlackStat(new MessageFactory().createWithoutId(messageDto));
        SlackStat slackStat3 = new SlackStat(new MessageFactory().createWithoutId(messageDto));
        SlackStat slackStat4 = new SlackStat(new MessageFactory().createWithoutId(messageDto));

        slackStatsRepository.save(slackStat);
        slackStatsRepository.save(slackStat1);
        slackStatsRepository.save(slackStat2);
        slackStatsRepository.save(slackStat3);
        slackStatsRepository.save(slackStat4);
        List<SlackStat> listOfStatistics = slackStatsRepository.findFirst3ByMessageFromContainsAndMessageToContainsOrderByMessageToDesc("seba", "andi");

        Assert.assertEquals(listOfStatistics.size(), 3);
        Assert.assertEquals(listOfStatistics.get(0), slackStat4);
        Assert.assertEquals(listOfStatistics.get(1), slackStat3);
        Assert.assertEquals(listOfStatistics.get(2), slackStat2);

    }
    /*@Test
    public void shouldFind2MatchesWhenExist() {
        SlackStat slackStat = new SlackStat("seba", "andi", "message id");
        SlackStat slackStat1 = new SlackStat("seba1", "andi1", "message id1");
        SlackStat slackStat2 = new SlackStat("dude", "andi2", "message id2");
        SlackStat slackStat3 = new SlackStat("ronaldo", "andi3", "message id3");
        SlackStat slackStat4 = new SlackStat("mymyslefandi", "andi4", "message id4");
        slackStatsRepository.save(slackStat);
        slackStatsRepository.save(slackStat1);
        slackStatsRepository.save(slackStat2);
        slackStatsRepository.save(slackStat3);
        slackStatsRepository.save(slackStat4);
        List<SlackStat> listOfStatistics = slackStatsRepository.findFirst3ByMessageFromContainsAndMessageToContainsOrderByMessageToDesc("seba", "andi");

        Assert.assertEquals(listOfStatistics.size(), 2);
        Assert.assertEquals(listOfStatistics.get(0), slackStat1);
        Assert.assertEquals(listOfStatistics.get(1), slackStat);

    }


    @Test
    public void shouldNotFindAnyMatchesWhenExist() {
        List<SlackStat> listOfStatistics = slackStatsRepository.findFirst3ByMessageFromContainsAndMessageToContainsOrderByMessageToDesc("seba", "andi");

        Assert.assertEquals(listOfStatistics.size(), 0);

    }

    @Test
    public void shouldReturnMessageFromByMessageId() {
        SlackStat slackStat = new SlackStat("seba", "andi", "78");
        slackStatsRepository.save(slackStat);

        String messageFrom = slackStatsRepository.findFromByMessageId("78");

        Assert.assertEquals(messageFrom, slackStat.getMessageFrom());
    }*/
}
