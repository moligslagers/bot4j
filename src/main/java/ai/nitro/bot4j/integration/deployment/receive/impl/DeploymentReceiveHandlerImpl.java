package ai.nitro.bot4j.integration.deployment.receive.impl;

import ai.nitro.bot4j.integration.deployment.domain.BotSendPayload;
import ai.nitro.bot4j.integration.deployment.domain.BotTypeListSendPayload;
import ai.nitro.bot4j.integration.deployment.receive.DeploymentReceiveHandler;
import ai.nitro.bot4j.integration.deployment.receive.service.DeploymentService;
import ai.nitro.bot4j.integration.facebook.receive.webhook.impl.FacebookWebhookImpl;
import ai.nitro.bot4j.middle.receive.MessageReceiver;
import ai.nitro.bot4j.middle.repo.StatefulBotProviderService;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;
import java.util.Set;

/**
 * Created by Markus on 26.04.2017.
 */

public class DeploymentReceiveHandlerImpl implements DeploymentReceiveHandler{

    private final static Logger LOG = LogManager.getLogger(FacebookWebhookImpl.class);

    @Inject
    DeploymentService deploymentService;

    @Override
    public String getBotTypes() {
        LOG.info("GET: BotTypes", DeploymentReceiveHandler.class);
        Gson gson = new Gson();
        BotTypeListSendPayload botTypeListSendPayload = new BotTypeListSendPayload();
        botTypeListSendPayload.setBotTypes(deploymentService.getBotTypes());
        return gson.toJson(botTypeListSendPayload);
    }

    @Override
    public String handleDeletion(Map<String, String[]> params) {
        String botId = params.get("bot_id")[0];
        LOG.info(String.format("DELETE: Bot %s", botId), DeploymentReceiveHandler.class);
        return deploymentService.handleDeletion(botId);
    }

    @Override
    public String handleDeployment(String json) {
        LOG.info(String.format("PUT: Deploying new Bot"), DeploymentReceiveHandler.class);
        Gson gson = new Gson();
        BotSendPayload botSendPayload = gson.fromJson(json, BotSendPayload.class);
        return deploymentService.handleDeployment(botSendPayload);
    }

    @Override
    public String handleUpdate(String json) {
        LOG.info(String.format("POST: update Bot"), DeploymentReceiveHandler.class);
        Gson gson = new Gson();
        BotSendPayload botSendPayload = gson.fromJson(json, BotSendPayload.class);
        return deploymentService.handleUpdate(botSendPayload);

    }
}
