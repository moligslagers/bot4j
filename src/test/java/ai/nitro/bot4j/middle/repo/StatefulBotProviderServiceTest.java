package ai.nitro.bot4j.middle.repo;

import ai.nitro.bot4j.TestBase;
import ai.nitro.bot4j.bot.impl.BotImpl;
import ai.nitro.bot4j.bot.impl.DummyBot;
import ai.nitro.bot4j.integration.deployment.domain.BotSendPayload;
import com.google.gson.Gson;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import org.junit.Before;
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
    public void deleteBot() throws Exception {

        botProviderService.registerBot(BotImpl.class, "BotImpl");
        botProviderService.registerBot(DummyBot.class, "Dummy");
        botProviderService.putBot(1L, "Dummy");
        botProviderService.putFacebookClient(1L, new DefaultFacebookClient("fbtoken",
                Version.VERSION_2_8));

        botProviderService.deleteBot(1L);
        assertEquals(0, botProviderService.getBots().size());
        assertEquals(0, botProviderService.getFacebookClients().size());
        assertNull(botProviderService.getBot(1L));
    }

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

        botProviderService.registerBot(BotImpl.class, "BotImpl");
        botProviderService.registerBot(DummyBot.class, "Dummy");
        botProviderService.putBot(1L, "Dummy");
        botProviderService.putFacebookClient(1L, new DefaultFacebookClient("fbtoken",
                Version.VERSION_2_8));

        assertSame(DummyBot.class, botProviderService.getBot(1L).getClass());
    }

    @Test
    public void getBots() throws Exception {

        botProviderService.registerBot(BotImpl.class, "BotImpl");
        botProviderService.registerBot(DummyBot.class, "Dummy");
        botProviderService.putBot(1L, "Dummy");
        botProviderService.putFacebookClient(1L, new DefaultFacebookClient("fbtoken",
                Version.VERSION_2_8));

        assertEquals(1, botProviderService.getBots().size());
    }

    @Test
    public void putBot() throws Exception {

        botProviderService.registerBot(BotImpl.class, "BotImpl");
        botProviderService.registerBot(DummyBot.class, "Dummy");
        botProviderService.putBot(1L, "Dummy");
        botProviderService.putFacebookClient(1L, new DefaultFacebookClient("fbtoken",
                Version.VERSION_2_8));

        botProviderService.putBot(2L, "BotImpl");
        assertEquals(2, botProviderService.getBots().size());
        assertSame(BotImpl.class, botProviderService.getBot(2L).getClass());

    }

    @Test
    public void putFacebookClient() throws Exception {
        FacebookClient facebookClient = new DefaultFacebookClient("abc123", Version.VERSION_2_8);
        botProviderService.putFacebookClient(1L, facebookClient);
        assertEquals(1, botProviderService.getFacebookClients().size());
    }

    @Test
    public void getFacebookClient() throws Exception {
        FacebookClient facebookClient = new DefaultFacebookClient("abc123", Version.VERSION_2_8);
        botProviderService.putFacebookClient(1L, facebookClient);
        assertSame(DefaultFacebookClient.class, botProviderService.getFacebookClient(1L).getClass());
    }

    @Test
    public void updateBot() throws Exception {

        botProviderService.registerBot(BotImpl.class, "BotImpl");
        botProviderService.registerBot(DummyBot.class, "Dummy");
        botProviderService.putBot(1L, "Dummy");

        FacebookClient fbClient = new DefaultFacebookClient("fbtoken",
                Version.VERSION_2_8);

        botProviderService.putFacebookClient(1L, fbClient);

        FacebookClient newFbClient = new DefaultFacebookClient("fbtoken_new",
                Version.VERSION_2_8);

        botProviderService.updateBot(1L, "BotImpl", newFbClient);

        assertEquals(1, botProviderService.getBots().size());
        assertEquals(1, botProviderService.getFacebookClients().size());
        assertEquals(BotImpl.class, botProviderService.getBot(1L).getClass());
        assertEquals(newFbClient, botProviderService.getFacebookClient(1L));

    }


}