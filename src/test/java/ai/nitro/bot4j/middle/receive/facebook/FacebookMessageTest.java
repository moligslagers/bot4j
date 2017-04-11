package ai.nitro.bot4j.middle.receive.facebook;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import javax.inject.Inject;

import org.junit.Test;

import com.restfb.types.webhook.messaging.CoordinatesItem;
import com.restfb.types.webhook.messaging.MessageItem;
import com.restfb.types.webhook.messaging.MessagingAttachment;
import com.restfb.types.webhook.messaging.MessagingItem;
import com.restfb.types.webhook.messaging.MessagingParticipant;
import com.restfb.types.webhook.messaging.MessagingPayload;

import ai.nitro.bot4j.TestBase;
import ai.nitro.bot4j.integration.facebook.receive.FacebookReceiveMessageFactory;
import ai.nitro.bot4j.middle.domain.receive.ReceiveMessage;
import ai.nitro.bot4j.middle.domain.receive.payload.AbstractReceivePayload;
import ai.nitro.bot4j.middle.domain.receive.payload.CoordinateReceivePayload;

public class FacebookMessageTest extends TestBase {

	@Inject
	protected FacebookReceiveMessageFactory facebookReceiveMessageFactory;

	@Test
	public void testLocationMessage() {
		final MessagingParticipant recipient = new MessagingParticipant();
		recipient.setId("recipientId");

		final MessagingParticipant sender = new MessagingParticipant();
		sender.setId("senderId");

		final double longVal = 4.5555525555;
		final double latVal = 6.23342;

		final CoordinatesItem coordinatesItem = new CoordinatesItem();
		coordinatesItem.setLongVal(longVal);
		coordinatesItem.setLat(latVal);

		final MessagingPayload messagingPayload = new MessagingPayload();
		messagingPayload.setCoordinates(coordinatesItem);

		final MessagingAttachment messagingAttachment = new MessagingAttachment();
		messagingAttachment.setPayload(messagingPayload);
		messagingAttachment.setType("location");

		final ArrayList<MessagingAttachment> list = new ArrayList<>();
		list.add(messagingAttachment);

		final MessageItem messageItem = new MessageItem();
		messageItem.setAttachments(list);

		final MessagingItem messagingItem = new MessagingItem();
		messagingItem.setMessage(messageItem);
		messagingItem.setRecipient(recipient);
		messagingItem.setSender(sender);

		final ReceiveMessage receiveMessage = facebookReceiveMessageFactory.createReceiveMessage(messagingItem);

		for (final AbstractReceivePayload payload : receiveMessage.getPayloads()) {
			final CoordinateReceivePayload coordinations = (CoordinateReceivePayload) payload;
			assertEquals(latVal, coordinations.getLatVal(), epsilon);
			assertEquals(longVal, coordinations.getLonVal(), epsilon);
		}
	}
}
