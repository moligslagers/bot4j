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
        LOG.info("Received DELETE", DeploymentWebhook.class);
        String message = deploymentReceiveHandler.handleDeletion(req.getParameterMap());
        return jsonResponse(message);
    }


    @Override
    public String get(HttpServletRequest req, HttpServletResponse res) {
        LOG.info("Received GET", DeploymentWebhook.class);
        String message = deploymentReceiveHandler.getBotTypes();
        return jsonResponse(message);
    }

    @Override
    public String post(HttpServletRequest req, HttpServletResponse res) {
        LOG.info("Received POST", DeploymentWebhook.class);
        String body = getRequestBody(req);
        String message = deploymentReceiveHandler.handleUpdate(body);
        return jsonResponse(message);
    }

    @Override
    public String put(HttpServletRequest req, HttpServletResponse res) {
        LOG.info("Received PUT", DeploymentWebhook.class);
        String body = getRequestBody(req);
        if (body != null) {
            String message = deploymentReceiveHandler.handleDeployment(body);
            return jsonResponse(message);
        } else {
            return jsonResponse("Body is empty");
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

    private HttpServletResponse buildResponse(HttpServletResponse res, int status, String message) {
        try {
            res.setStatus(status);
            res.getWriter().write(message);
        } catch (Exception e) {
            handleException(e);
        }
        return res;
    }

    private String jsonResponse(String message){
        return String.format("{message:'%s'}", message);
    }
}
