package ai.nitro.bot4j.integration.alexa.receive.hook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AlexaWebhook {

	String post(HttpServletRequest req, HttpServletResponse res);
}
