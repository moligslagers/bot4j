package ai.nitro.bot4j.integration.deployment.receive;

import ai.nitro.bot4j.TestBase;
import ai.nitro.bot4j.bot.impl.BotImpl;
import ai.nitro.bot4j.bot.impl.DummyBot;
import ai.nitro.bot4j.integration.deployment.domain.BotTypeListSendPayload;
import ai.nitro.bot4j.middle.repo.StatefulBotProviderService;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Markus on 27.04.2017.
 */
public class DeploymentReceiveHandlerTest extends TestBase{
    @Inject
    DeploymentReceiveHandler deploymentReceiveHandler;

    @Inject
    StatefulBotProviderService botProviderService;

    @Test
    public void handleDeployment() throws Exception {

        botProviderService.registerBot(DummyBot.class, "ExampleBot");

        deploymentReceiveHandler.handleDeployment(jsonPayload);
        assertEquals(1, botProviderService.getBots().size());

    }

    @Test
    public void getBotTypes() throws Exception {
        Gson gson = new Gson();
        botProviderService.registerBot(BotImpl.class, "BotImpl");
        botProviderService.registerBot(DummyBot.class, "Dummy");
        botProviderService.putBot(1L, "Dummy");

        FacebookClient fbClient = new DefaultFacebookClient("fbtoken",
                Version.VERSION_2_8);

        botProviderService.putFacebookClient(1L, fbClient);

        String botTypes = deploymentReceiveHandler.getBotTypes();
        BotTypeListSendPayload botTypeListSendPayload = gson.fromJson(botTypes, BotTypeListSendPayload.class);
        assertEquals(botProviderService.getBotTypes().size(), botTypeListSendPayload.getBotTypes().size());
    }

    @Test
    public void handleDeletion() throws Exception {
        botProviderService.registerBot(BotImpl.class, "BotImpl");
        botProviderService.registerBot(DummyBot.class, "Dummy");
        botProviderService.putBot(1L, "Dummy");

        FacebookClient fbClient = new DefaultFacebookClient("fbtoken",
                Version.VERSION_2_8);

        botProviderService.putFacebookClient(1L, fbClient);

        Map<String, String[]> params = new HashMap<>();
        params.put("bot_id", new String[]{"1"});
        deploymentReceiveHandler.handleDeletion(params);
        assertEquals(0, botProviderService.getBots().size());
    }

    @Test
    public void handleUpdate() throws Exception {
        botProviderService.registerBot(BotImpl.class, "BotImpl");
        botProviderService.registerBot(DummyBot.class, "ExampleBot");
        botProviderService.putBot(1L, "BotImpl");

        FacebookClient fbClient = new DefaultFacebookClient("fbtoken",
                Version.VERSION_2_8);

        botProviderService.putFacebookClient(1L, fbClient);

        deploymentReceiveHandler.handleUpdate(jsonPayload);

        assertEquals(1, botProviderService.getBots().size());
        assertEquals(1, botProviderService.getFacebookClients().size());
        assertEquals(DummyBot.class, botProviderService.getBot(1L).getClass());
        assertNotEquals(fbClient, botProviderService.getFacebookClient(1L));
    }

}