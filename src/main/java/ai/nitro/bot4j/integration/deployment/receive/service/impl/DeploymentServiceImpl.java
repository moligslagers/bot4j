package ai.nitro.bot4j.integration.deployment.receive.service.impl;

import ai.nitro.bot4j.integration.deployment.domain.BotSendPayload;
import ai.nitro.bot4j.integration.deployment.domain.FacebookSpecPayload;
import ai.nitro.bot4j.integration.deployment.receive.service.DeploymentService;
import ai.nitro.bot4j.integration.facebook.receive.webhook.impl.FacebookWebhookImpl;
import ai.nitro.bot4j.middle.repo.StatefulBotProviderService;
import com.google.gson.Gson;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.Set;

/**
 * Created by Markus on 26.04.2017.
 */
public class DeploymentServiceImpl implements DeploymentService {

    private final static Logger LOG = LogManager.getLogger(FacebookWebhookImpl.class);


    @Inject
    StatefulBotProviderService botProviderService;

    @Override
    public Set<String> getBotTypes() {
        LOG.info(String.format("GET: BotTypes"), DeploymentService.class);
        return botProviderService.getBotTypes();
    }

    @Override
    public String handleDeletion(String botId) {
        // TODO: Handle wrong argument for botId not parseable to Long
        LOG.info(String.format("DELETE: Bot %s", botId), DeploymentService.class);
        return botProviderService.deleteBot(Long.parseLong(botId));
    }

    @Override
    public String handleUpdate(BotSendPayload botSendPayload) {
        Long botId = botSendPayload.getId();
        String botType = botSendPayload.getBotType();
        String accessToken = botSendPayload.getFacebookSpec().getAccessToken();
        FacebookClient facebookClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_8);
        LOG.info(String.format("POST: Update Bot %s", botId), DeploymentService.class);
        return botProviderService.updateBot(botId, botType, facebookClient);

    }

    @Override
    public String handleDeployment(BotSendPayload botSendPayload) {
        Long botId = botSendPayload.getId();
        botProviderService.putBot(botId, botSendPayload.getBotType());
        String accessToken = botSendPayload.getFacebookSpec().getAccessToken();
        FacebookClient facebookClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_8);
        LOG.info(String.format("PUT: Deploy Bot %s", botId), DeploymentService.class);
        return botProviderService.putFacebookClient(botId, facebookClient);

    }
}
