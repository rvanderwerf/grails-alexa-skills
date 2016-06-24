package ${packageName}

import alexa.skills.GrailsSpeechletClass
import com.amazon.speech.slu.Intent
import com.amazon.speech.slu.Slot
import com.amazon.speech.speechlet.IntentRequest
import com.amazon.speech.speechlet.LaunchRequest
import com.amazon.speech.speechlet.Session
import com.amazon.speech.speechlet.SessionEndedRequest
import com.amazon.speech.speechlet.SessionStartedRequest
import com.amazon.speech.speechlet.SpeechletException
import com.amazon.speech.speechlet.SpeechletResponse
import com.amazon.speech.ui.LinkAccountCard
import com.amazon.speech.ui.PlainTextOutputSpeech
import com.amazon.speech.ui.Reprompt
import com.amazon.speech.ui.SimpleCard
import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import com.amazon.speech.speechlet.Speechlet
import groovy.util.logging.Slf4j


@Slf4j
class ${className}Speechlet implements GrailsConfigurationAware, Speechlet {


    def grailsApplication

    Config grailsConfig
    def speechletService


    def index() {
        speechletService.doSpeechlet(request,response, this)
    }

    public void onSessionStarted(final SessionStartedRequest request, final Session session)
            throws SpeechletException {
        log.info("onSessionStarted requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId())
        // any initialization logic goes here
    }


    public SpeechletResponse onLaunch(final LaunchRequest request, final Session session)
            throws SpeechletException {
        log.info("onLaunch requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId())

        return getWelcomeResponse()
    }


    public SpeechletResponse onIntent(final IntentRequest request, final Session session)
            throws SpeechletException {
        log.info("onIntent requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId())

        Intent intent = request.getIntent()


        String intentName = (intent != null) ? intent.getName() : null
        Slot query = intent.getSlot("SearchTerm")
        Slot count = intent.getSlot("Count")

        log.debug("invoking intent:\${intentName}")
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech()

        // Create the Simple card content.
        SimpleCard card = new SimpleCard()
        card.setTitle("Twitter Search Results")


        def speechText = "I will say something"
        def cardText = "I will print something"

        // Create the plain text output.
        speech.setText(speechText)
        card.setContent(cardText)

        return SpeechletResponse.newTellResponse(speech, card)

    }
    /**
     * Grails config is injected here for configuration of your speechlet
     * @param co Config
     */
    void setConfiguration(Config co) {
        this.grailsConfig = co
    }

    public void onSessionEnded(final SessionEndedRequest request, final Session session)
            throws SpeechletException {
        log.info("onSessionEnded requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId())
        // any cleanup logic goes here
    }

    SpeechletResponse getWelcomeResponse()  {
        String speechText = "Say something when the skill starts"

        // Create the Simple card content.
        SimpleCard card = new SimpleCard()
        card.setTitle("YourWelcomeCardTitle")
        card.setContent(speechText)

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech()
        speech.setText(speechText)

        // Create reprompt
        Reprompt reprompt = new Reprompt()
        reprompt.setOutputSpeech(speech)

        return SpeechletResponse.newAskResponse(speech, reprompt, card)
    }

    /**
     * default responder when a help intent is launched on how to use your speechlet
     * @return
     */
    SpeechletResponse getHelpResponse() {
        String speechText = "Say something when the skill need help"

        // Create the Simple card content.
        SimpleCard card = new SimpleCard()
        card.setTitle("YourHelpCardTitle")
        card.setContent(speechText)

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech()
        speech.setText(speechText)

        // Create reprompt
        Reprompt reprompt = new Reprompt()
        reprompt.setOutputSpeech(speech)

        return SpeechletResponse.newAskResponse(speech, reprompt, card)

    }

    /**
     * if you are using account linking, this is used to send a card with a link to your app to get started
     * @param session
     * @return
     */
    SpeechletResponse createLinkCard(Session session) {

        String speechText = "Please use the alexa app to link account."

        // Create the Simple card content.
        LinkAccountCard card = new LinkAccountCard()
        //card.setTitle("LinkAccount")


        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech()
        speech.setText(speechText)
        log.debug("Session ID=\${session.sessionId}")
        // Create reprompt
        Reprompt reprompt = new Reprompt()
        reprompt.setOutputSpeech(speech)
        //session.finalize()

        return SpeechletResponse.newTellResponse(speech, card)
    }

}
