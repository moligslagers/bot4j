package ai.nitro.bot4j.integration.deployment.receive.impl;

import ai.nitro.bot4j.integration.deployment.domain.BotSendPayload;
import ai.nitro.bot4j.integration.deployment.receive.DeploymentReceiveHandler;
import ai.nitro.bot4j.integration.deployment.receive.service.DeploymentService;
import ai.nitro.bot4j.middle.receive.MessageReceiver;
import ai.nitro.bot4j.middle.repo.StatefulBotProviderService;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Markus on 26.04.2017.
 */

public class DeploymentReceiveHandlerImpl implements DeploymentReceiveHandler{

    @Inject
    StatefulBotProviderService botProviderService;

    @Inject
    DeploymentService deploymentService;

    @Override
    public void handleDeployment(String json) {
        deploymentService.handleDeployment(json);
    }
}
