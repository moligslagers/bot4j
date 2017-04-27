package ai.nitro.bot4j.integration.deployment.receive.service;

import ai.nitro.bot4j.TestBase;
import ai.nitro.bot4j.bot.impl.BotImpl;
import ai.nitro.bot4j.bot.impl.DummyBot;
import ai.nitro.bot4j.integration.deployment.domain.BotSendPayload;
import ai.nitro.bot4j.integration.deployment.domain.FacebookSpecPayload;
import ai.nitro.bot4j.middle.repo.StatefulBotProviderService;
import com.google.gson.Gson;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import org.junit.Test;

import javax.inject.Inject;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Markus on 27.04.2017.
 */
public class DeploymentServiceTest extends TestBase {

    @Inject
    StatefulBotProviderService botProviderService;

    @Inject
    DeploymentService deploymentService;

    @Test
    public void getBotTypes() throws Exception {
        botProviderService.registerBot(BotImpl.class, "BotImpl");
        botProviderService.registerBot(DummyBot.class, "Dummy");
        botProviderService.putBot(1L, "Dummy");

        FacebookClient fbClient = new DefaultFacebookClient("fbtoken",
                Version.VERSION_2_8);

        botProviderService.putFacebookClient(1L, fbClient);

        Set<String> botTypes = deploymentService.getBotTypes();
        assertEquals(2, botTypes.size());
    }

    @Test
    public void handleDeletion() throws Exception {
        botProviderService.registerBot(BotImpl.class, "BotImpl");
        botProviderService.registerBot(DummyBot.class, "Dummy");
        botProviderService.putBot(1L, "Dummy");

        FacebookClient fbClient = new DefaultFacebookClient("fbtoken",
                Version.VERSION_2_8);

        botProviderService.putFacebookClient(1L, fbClient);

        deploymentService.handleDeletion("1");
        assertEquals(0, botProviderService.getBots().size());
        assertNull(botProviderService.getBot(1L));
    }

    @Test
    public void handleDeployment() throws Exception {

        botProviderService.registerBot(DummyBot.class, "ExampleBot");

        Gson gson = new Gson();

        BotSendPayload botSendPayload = gson.fromJson(jsonPayload, BotSendPayload.class);


        deploymentService.handleDeployment(botSendPayload);
        assertEquals(1, botProviderService.getBots().size());
        assertEquals(1, botProviderService.getFacebookClients().size());
    }

    @Test
    public void handleUpdate() throws Exception {
        botProviderService.registerBot(BotImpl.class, "BotImpl");
        botProviderService.registerBot(DummyBot.class, "Dummy");
        botProviderService.putBot(1L, "Dummy");

        FacebookClient fbClient = new DefaultFacebookClient("fbtoken",
                Version.VERSION_2_8);

        botProviderService.putFacebookClient(1L, fbClient);

        FacebookSpecPayload fbpayload = new FacebookSpecPayload("newfbtoken");

        BotSendPayload botSendPayload = new BotSendPayload(1L, "UpdateBot", "BotImpl","Dest", fbpayload, null, null);

        deploymentService.handleUpdate(botSendPayload);

        assertEquals(1, botProviderService.getBots().size());
        assertEquals(1, botProviderService.getFacebookClients().size());
        assertEquals(BotImpl.class, botProviderService.getBot(1L).getClass());
        assertNotEquals(fbClient, botProviderService.getFacebookClient(1L));
    }

}