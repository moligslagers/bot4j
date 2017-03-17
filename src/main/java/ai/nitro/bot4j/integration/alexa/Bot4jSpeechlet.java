package ai.nitro.bot4j.integration.alexa;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.speechlet.SpeechletV2;

public interface Bot4jSpeechlet extends SpeechletV2 {

	@Override
	SpeechletResponse onIntent(SpeechletRequestEnvelope<IntentRequest> requestEnvelope);

	@Override
	SpeechletResponse onLaunch(SpeechletRequestEnvelope<LaunchRequest> arg0);

	@Override
	void onSessionEnded(SpeechletRequestEnvelope<SessionEndedRequest> arg0);

	@Override
	void onSessionStarted(SpeechletRequestEnvelope<SessionStartedRequest> arg0);
}