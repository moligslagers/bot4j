package ai.nitro.bot4j.integration.deployment;

import ai.nitro.bot4j.integration.deployment.receive.DeploymentReceiveHandler;
import ai.nitro.bot4j.integration.deployment.receive.impl.DeploymentReceiveHandlerImpl;
import ai.nitro.bot4j.integration.deployment.receive.service.DeploymentService;
import ai.nitro.bot4j.integration.deployment.receive.service.impl.DeploymentServiceImpl;
import ai.nitro.bot4j.integration.deployment.receive.webhook.DeploymentWebhook;
import ai.nitro.bot4j.integration.deployment.receive.webhook.impl.DeploymentWebhookImpl;
import com.google.inject.AbstractModule;

/**
 * Created by Markus on 26.04.2017.
 */
public class DeploymentModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(DeploymentReceiveHandler.class).to(DeploymentReceiveHandlerImpl.class);
        bind(DeploymentService.class).to(DeploymentServiceImpl.class);
        bind(DeploymentWebhook.class).to(DeploymentWebhookImpl.class);
    }
}
