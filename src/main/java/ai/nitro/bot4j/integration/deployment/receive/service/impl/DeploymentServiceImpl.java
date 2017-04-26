package ai.nitro.bot4j.integration.deployment.receive.service.impl;

import ai.nitro.bot4j.integration.deployment.domain.BotSendPayload;
import ai.nitro.bot4j.integration.deployment.receive.service.DeploymentService;
import ai.nitro.bot4j.middle.repo.StatefulBotProviderService;
import com.google.gson.Gson;

import javax.inject.Inject;

/**
 * Created by Markus on 26.04.2017.
 */
public class DeploymentServiceImpl implements DeploymentService {

    @Inject
    StatefulBotProviderService botProviderService;

    @Override
    public void handleDeployment(String json){
        BotSendPayload botSendPayload = parseJson(json);
        botProviderService.putBot(botSendPayload.getId(), botSendPayload.getBotType());
    }

    public BotSendPayload parseJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, BotSendPayload.class);
    }
}
