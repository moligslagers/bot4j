package ai.nitro.bot4j.integration.deployment.receive.service.impl;

import ai.nitro.bot4j.integration.deployment.domain.BotSendPayload;
import ai.nitro.bot4j.integration.deployment.domain.FacebookSpecPayload;
import ai.nitro.bot4j.integration.deployment.receive.service.DeploymentService;
import ai.nitro.bot4j.middle.repo.StatefulBotProviderService;
import com.google.gson.Gson;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;

import javax.inject.Inject;
import java.util.Set;

/**
 * Created by Markus on 26.04.2017.
 */
public class DeploymentServiceImpl implements DeploymentService {

    @Inject
    StatefulBotProviderService botProviderService;

    @Override
    public Set<String> getBotTypes() {
        return botProviderService.getBotTypes();
    }

    @Override
    public String handleDeletion(String botId) {
        // TODO: Handle wrong argument for botId not parseable to Long
        return botProviderService.deleteBot(Long.parseLong(botId));
    }

    @Override
    public String handleUpdate(BotSendPayload botSendPayload) {

        Long botId = botSendPayload.getId();
        String botType = botSendPayload.getBotType();
        String accessToken = botSendPayload.getFacebookSpec().getAccessToken();
        FacebookClient facebookClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_8);
        return botProviderService.updateBot(botId, botType, facebookClient);

    }

    @Override
    public String handleDeployment(BotSendPayload botSendPayload) {

        Long botId = botSendPayload.getId();
        botProviderService.putBot(botId, botSendPayload.getBotType());
        String accessToken = botSendPayload.getFacebookSpec().getAccessToken();
        FacebookClient facebookClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_8);
        return botProviderService.putFacebookClient(botId, facebookClient);

    }
}
