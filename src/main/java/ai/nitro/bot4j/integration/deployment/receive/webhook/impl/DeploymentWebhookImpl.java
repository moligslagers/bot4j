package ai.nitro.bot4j.integration.deployment.receive.webhook.impl;

import ai.nitro.bot4j.integration.deployment.receive.DeploymentReceiveHandler;
import ai.nitro.bot4j.integration.deployment.receive.service.DeploymentService;
import ai.nitro.bot4j.integration.deployment.receive.webhook.DeploymentWebhook;
import ai.nitro.bot4j.integration.facebook.receive.webhook.impl.FacebookWebhookImpl;
import com.google.common.io.CharStreams;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Markus on 26.04.2017.
 */
public class DeploymentWebhookImpl implements DeploymentWebhook {

    private final static Logger LOG = LogManager.getLogger(FacebookWebhookImpl.class);

    @Inject
    DeploymentReceiveHandler deploymentReceiveHandler;

    @Inject
    DeploymentService deploymentService;

    @Override
    public String put(HttpServletRequest req, HttpServletResponse res) {
        try {
            final String body = CharStreams.toString(req.getReader());
            deploymentReceiveHandler.handleDeployment(body);
        } catch (Exception e) {
            handleException(e);
        }
        return null;
    }

    @Override
    public String get(HttpServletRequest req, HttpServletResponse res) {
        return null;
    }

    protected void handleException(final Exception e) {
        LOG.error(e.getMessage(), e);
    }
}
