package ai.nitro.bot4j.integration.telegram.receive.webhook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface TelegramWebhook {

	public String post(final HttpServletRequest req, final HttpServletResponse res);

}
