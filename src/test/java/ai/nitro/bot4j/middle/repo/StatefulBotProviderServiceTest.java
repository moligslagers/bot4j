package ai.nitro.bot4j.middle.repo;

import ai.nitro.bot4j.TestBase;
import ai.nitro.bot4j.bot.impl.BotImpl;
import ai.nitro.bot4j.bot.impl.DummyBot;
import org.junit.Test;

import javax.inject.Inject;

import static org.junit.Assert.*;

/**
 * Created by Markus on 26.04.2017.
 */
public class StatefulBotProviderServiceTest extends TestBase {

    @Inject
    StatefulBotProviderService botProviderService;

    @Test
    public void registerBot() throws Exception {
        botProviderService.registerBot(BotImpl.class, "DefaultBot");
        botProviderService.putBot(1L, "DefaultBot");
        assertEquals(1, botProviderService.getBots().size());
        assertSame(BotImpl.class, botProviderService.getBot(1L).getClass());
        BotImpl botImpl = (BotImpl) botProviderService.getBot(1L);
        assertNotNull(botImpl);

        botProviderService.registerBot(DummyBot.class, "DummyBot");
        botProviderService.putBot(2L, "DummyBot");
        assertEquals(2, botProviderService.getBots().size());
        DummyBot dummyBot = (DummyBot) botProviderService.getBot(2L);
        assertTrue(dummyBot.test());

        StatefulBotProviderService botProviderService2 = injector.getInstance(StatefulBotProviderService.class);
        assertEquals(2, botProviderService2.getBots().size());
    }

    @Test
    public void getBot() throws Exception {
    }

    @Test
    public void getBots() throws Exception {
    }

    @Test
    public void putBot() throws Exception {

    }


}