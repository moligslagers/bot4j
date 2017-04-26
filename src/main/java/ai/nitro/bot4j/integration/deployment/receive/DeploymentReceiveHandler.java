package ai.nitro.bot4j.integration.deployment.receive;

import ai.nitro.bot4j.integration.deployment.domain.BotSendPayload;

/**
 * Created by Markus on 26.04.2017.
 */
public interface DeploymentReceiveHandler {

    void handleDeployment(String json);

}
