package ai.nitro.bot4j.integration.deployment.receive.webhook.impl;

import ai.nitro.bot4j.integration.deployment.receive.DeploymentReceiveHandler;
import ai.nitro.bot4j.integration.deployment.receive.service.DeploymentService;
import ai.nitro.bot4j.integration.deployment.receive.webhook.DeploymentWebhook;
import ai.nitro.bot4j.integration.facebook.receive.webhook.impl.FacebookWebhookImpl;
import ai.nitro.bot4j.middle.repo.StatefulBotProviderService;
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

    private final static Logger LOG = LogManager.getLogger(DeploymentWebhook.class);

    @Inject
    DeploymentReceiveHandler deploymentReceiveHandler;

    @Override
    public String delete(HttpServletRequest req, HttpServletResponse res) {
        /**
         * HTTP endpoint to delete bots from the bot respository
         */
        LOG.info("Received DELETE", DeploymentWebhook.class);
        String message = deploymentReceiveHandler.handleDeletion(req.getParameterMap());
        return message;
    }


    @Override
    public String get(HttpServletRequest req, HttpServletResponse res) {
        /**
         * HTTP endpoint to get a list of all bot types in the bot repository
         * Currently unused
         */
        LOG.info("Received GET", DeploymentWebhook.class);
        String message = deploymentReceiveHandler.getBotTypes();
        return message;
    }

    @Override
    public String post(HttpServletRequest req, HttpServletResponse res) {
        /**
         * HTTP endpoint to update the facebook configuration of an existing bot
         * Currently unused
         */
        LOG.info("Received POST", DeploymentWebhook.class);
        String body = getRequestBody(req);
        String message = deploymentReceiveHandler.handleUpdate(body);
        return message;
    }

    @Override
    public String put(HttpServletRequest req, HttpServletResponse res) {
        /**
         * HTTP endpoint to add a new bot and the corresponding facebook configuration to the bot repository
         */
        LOG.info("DEPLOYMENT 1");
        LOG.info("Received PUT", DeploymentWebhook.class);
        String body = getRequestBody(req);
        LOG.info(body);
        //TODO: body != null is not a good indicator. Body is never null.
        if (body != null) {
            String message = deploymentReceiveHandler.handleDeployment(body);
            return message;
            //return jsonResponse(message);
        } else {
            return "Body is empty";
        }
    }

    private void handleException(final Exception e) {
        LOG.error(e.getMessage(), e);
    }

    private String getRequestBody(HttpServletRequest req) {
        try {
            return CharStreams.toString(req.getReader());
        } catch (Exception e) {
            handleException(e);
        }
        return null;
    }
}
