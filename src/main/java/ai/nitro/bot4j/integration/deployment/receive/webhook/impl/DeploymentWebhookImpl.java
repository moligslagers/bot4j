package ai.nitro.bot4j.integration.deployment.receive.webhook.impl;

import ai.nitro.bot4j.integration.deployment.domain.BotTypeListSendPayload;
import ai.nitro.bot4j.integration.deployment.receive.DeploymentReceiveHandler;
import ai.nitro.bot4j.integration.deployment.receive.service.DeploymentService;
import ai.nitro.bot4j.integration.deployment.receive.webhook.DeploymentWebhook;
import ai.nitro.bot4j.integration.facebook.receive.webhook.impl.FacebookWebhookImpl;
import ai.nitro.bot4j.middle.repo.StatefulBotProviderService;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Markus on 26.04.2017.
 */
public class DeploymentWebhookImpl implements DeploymentWebhook {

    private final static Logger LOG = LogManager.getLogger(FacebookWebhookImpl.class);

    @Inject
    DeploymentReceiveHandler deploymentReceiveHandler;

    @Inject
    DeploymentService deploymentService;

    @Inject
    StatefulBotProviderService botProviderService;

    @Override
    public String delete(HttpServletRequest req, HttpServletResponse res) {
        return deploymentReceiveHandler.handleDeletion(req.getParameterMap());
    }

    @Override
    public String get(HttpServletRequest req, HttpServletResponse res) {
        return deploymentReceiveHandler.getBotTypes();
    }

    @Override
    public String post(HttpServletRequest req, HttpServletRequest res) {
        String body = getRequestBody(req);
        return deploymentReceiveHandler.handleUpdate(body);
    }

    @Override
    public HttpServletResponse put(HttpServletRequest req, HttpServletResponse res) {
        String body = getRequestBody(req);
        if(body != null){
            String message = deploymentReceiveHandler.handleDeployment(body);
            res = fillResponse(res, 200, message);
            return res;
        }
        else{
            return fillResponse(res, 400,"Body is empty");
        }
    }

    private void handleException(final Exception e) {
        LOG.error(e.getMessage(), e);
    }

    private String getRequestBody(HttpServletRequest req){
        try {
            return CharStreams.toString(req.getReader());
        } catch (Exception e) {
            handleException(e);
        }
        return null;
    }

    private HttpServletResponse fillResponse(HttpServletResponse res, int status, String message){
        try{
            res.setStatus(status);
            res.getWriter().write(message);
        }catch(Exception e){
            handleException(e);
        }
        return res;
    }
}
