package ai.nitro.bot4j.integration.deployment.receive.service;

import ai.nitro.bot4j.integration.deployment.domain.BotSendPayload;

import java.util.Set;

/**
 * Created by Markus on 26.04.2017.
 */
public interface DeploymentService {

    Set<String> getBotTypes();

    String handleDeletion(String botId);

    String handleDeployment(BotSendPayload botSendPayload);

    String handleUpdate(BotSendPayload botSendPayload);
}
