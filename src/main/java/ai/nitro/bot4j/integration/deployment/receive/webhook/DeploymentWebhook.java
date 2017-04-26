package ai.nitro.bot4j.integration.deployment.receive.webhook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Markus on 26.04.2017.
 */
public interface DeploymentWebhook {
    String get(HttpServletRequest req, HttpServletResponse res);

    String put(HttpServletRequest req, HttpServletResponse res);

}
