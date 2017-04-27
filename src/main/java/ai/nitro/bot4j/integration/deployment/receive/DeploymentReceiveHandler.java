package ai.nitro.bot4j.integration.deployment.receive;

import ai.nitro.bot4j.integration.deployment.domain.BotSendPayload;
import ai.nitro.bot4j.integration.deployment.domain.BotTypeListSendPayload;

import java.util.Map;
import java.util.Set;

/**
 * Created by Markus on 26.04.2017.
 */
public interface DeploymentReceiveHandler {

    String handleDeployment(String json);

    String getBotTypes();

    String handleDeletion(Map<String, String[]> json);

    String handleUpdate(String json);

}
