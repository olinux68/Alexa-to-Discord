package net.bancey.speechlets;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.*;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;
import net.bancey.intents.AlexaDiscordIntent;
import net.bancey.intents.GetGuildsIntent;
import net.bancey.intents.GetTextChannelsFromGuildIntent;

/**
 * Created by abance on 13/12/2016.
 */
public class DiscordSpeechlet implements Speechlet {

    private AlexaDiscordIntent[] intents = {new GetTextChannelsFromGuildIntent("GetTextChannelsFromGuildIntent"), new GetGuildsIntent("GetGuildsIntent")};

    @Override
    public void onSessionStarted(SessionStartedRequest sessionStartedRequest, Session session) throws SpeechletException {
        //Startup login
    }

    @Override
    public SpeechletResponse onLaunch(LaunchRequest launchRequest, Session session) throws SpeechletException {
        return onLaunchResponse();
    }

    @Override
    public SpeechletResponse onIntent(IntentRequest intentRequest, Session session) throws SpeechletException {
        Intent intent = intentRequest.getIntent();
        if("AMAZON.StopIntent".equals(intent.getName())) {
            return onExitResponse();
        } else if("AMAZON.CancelIntent".equals(intent.getName())) {
            return onExitResponse();
        } else if("AMAZON.HelpIntent".equals(intent.getName())) {
            return onLaunchResponse();
        } else {
            for(AlexaDiscordIntent alexaDiscordIntent: intents) {
                if(alexaDiscordIntent.getName().equals(intent.getName())) {
                    return alexaDiscordIntent.handle("s u c c m y f u c c");
                }
            }
        }
        return onErrorResponse();
    }

    @Override
    public void onSessionEnded(SessionEndedRequest sessionEndedRequest, Session session) throws SpeechletException {
        //Shutdown login
    }

    private SpeechletResponse onLaunchResponse() {
        String speechText = "Welcome to the Alexa Discord Skill, you can say get channels or get guilds";

        SimpleCard card = new SimpleCard();
        card.setTitle("HelloWorld");
        card.setContent(speechText);

        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(speech);

        return SpeechletResponse.newAskResponse(speech, reprompt, card);
    }

    private SpeechletResponse onErrorResponse() {
        String speechText = "I encountered an error while processing your request.";
        String repromptText = "Please try again.";

        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        PlainTextOutputSpeech repromptSpeech = new PlainTextOutputSpeech();
        repromptSpeech.setText(repromptText);

        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(repromptSpeech);

        return SpeechletResponse.newAskResponse(speech, reprompt);
    }

    private SpeechletResponse onExitResponse() {
        String speechText = "Goodbye!";

        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        return SpeechletResponse.newTellResponse(speech);
    }
}