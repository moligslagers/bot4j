package ai.nitro.bot4j.integration.deployment.receive.service;

import ai.nitro.bot4j.TestBase;
import ai.nitro.bot4j.bot.impl.DummyBot;
import ai.nitro.bot4j.integration.deployment.domain.BotSendPayload;
import ai.nitro.bot4j.integration.deployment.domain.FacebookSpecPayload;
import ai.nitro.bot4j.integration.deployment.domain.SlackSpecPayload;
import ai.nitro.bot4j.integration.deployment.domain.TelegramSpecPayload;
import ai.nitro.bot4j.middle.repo.StatefulBotProviderService;
import com.google.gson.Gson;
import org.junit.Test;

import javax.inject.Inject;

import static org.junit.Assert.*;

/**
 * Created by Markus on 26.04.2017.
 */
public class DeploymentServiceTest extends TestBase{

    @Inject
    DeploymentService deploymentService;

    @Inject
    StatefulBotProviderService botProviderService;

    @Test
    public void handleDeployment() throws Exception {

        botProviderService.registerBot(DummyBot.class, "ExampleBot");

        Gson gson = new Gson();

        BotSendPayload botSendPayload = gson.fromJson(jsonPayload, BotSendPayload.class);


        deploymentService.handleDeployment(botSendPayload);
        assertEquals(1, botProviderService.getBots().size());
    }


}