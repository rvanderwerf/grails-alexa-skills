/*
 * Copyright (c) 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package alexa.skills;

import com.amazon.speech.speechlet.*;
import grails.config.Config;
import grails.core.GrailsClass;
import grails.core.support.GrailsConfigurationAware;

import java.util.Map;

/**
 * Represents a speechlet class in Grails.
 *
 * @author Ryan Vanderwerf
 * @author Lee Fox
 * @since 0.1
 */
public interface GrailsSpeechletClass extends GrailsClass  {


    /**
     * Grails config is injected here for configuration of your speechlet
     * @param co Config
     */
    void setConfiguration(Config co);


    /**
     * this is called by alexa when a session/interaction is started
     * @param request speechlet requet
     * @param session this contains session ID, user (if used linked accounts)
     * @throws SpeechletException
     */
    public void onSessionStarted(final SessionStartedRequest request, final Session session)
            throws SpeechletException;

    /**
     * this is called after a session is established then started
     * @param request speechlet request
     * @param session this contains session ID, user (if used linked accounts)
     * @return SpeechletResponse
     * @throws SpeechletException
     */
    public SpeechletResponse onLaunch(final LaunchRequest request, final Session session)
            throws SpeechletException;

    /**
     * when alexa figured out an intent to run, based on your sample utterances it calls this
     * @param request speechlet request
     * @param session this contains session ID, user (if used linked accounts)
     * @return SpeechletResponse
     * @throws SpeechletException
     */
    public SpeechletResponse onIntent(final IntentRequest request, final Session session)
            throws SpeechletException;

    /**
     * when alexa figures out the interaction is done, this is the cleanup code for db entries and such
     * @param request speechlet request
     * @param session this contains session ID, user (if used linked accounts)
     * @throws SpeechletException
     */
    public void onSessionEnded(final SessionEndedRequest request, final Session session)
            throws SpeechletException;

    /**
     * default responder for a welcome message when skill is started
     * @return SpeechletResponse
     */
    SpeechletResponse getWelcomeResponse();

    /**
     * default responder when a help intent is launched on how to use your speechlet
     * @return SpeechletResponse
     */
    SpeechletResponse getHelpResponse();

    /**
     * if you are using account linking, this is used to send a card with a link to your app to get started
     * @param session
     * @return SpeechletResponse
     */
    SpeechletResponse createLinkCard(Session session);






}
