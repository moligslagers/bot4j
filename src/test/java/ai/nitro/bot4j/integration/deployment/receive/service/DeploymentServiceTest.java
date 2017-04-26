package ai.nitro.bot4j.integration.deployment.receive.service;

import ai.nitro.bot4j.TestBase;
import ai.nitro.bot4j.integration.deployment.domain.BotSendPayload;
import ai.nitro.bot4j.integration.deployment.domain.FacebookSpecPayload;
import ai.nitro.bot4j.integration.deployment.domain.SlackSpecPayload;
import ai.nitro.bot4j.integration.deployment.domain.TelegramSpecPayload;
import ai.nitro.bot4j.middle.repo.StatefulBotProviderService;
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

        deploymentService.handleDeployment(jsonPayload);
        assertEquals(1, botProviderService.getBots().size());
    }

}