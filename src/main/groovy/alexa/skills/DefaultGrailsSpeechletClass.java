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
import org.grails.core.AbstractGrailsClass;
import java.util.HashMap;
import java.util.Map;




/**
 * Grails artifact class which represents a Quartz job.
 *
 * @author Micha?? K??ujszo
 * @author Marcel Overdijk
 * @author Sergey Nebolsin (nebolsin@gmail.com)
 * @since 0.1
 */
public class DefaultGrailsSpeechletClass extends AbstractGrailsClass implements GrailsSpeechletClass {

    public static final String SPEECHLET = "Speechlet";
    private Map triggers = new HashMap();


    public DefaultGrailsSpeechletClass(Class clazz) {
        super(clazz, SPEECHLET);

    }

    @Override
    public void setConfiguration(Config co) {

    }

    @Override
    public void onSessionStarted(SessionStartedRequest request, Session session) throws SpeechletException {

    }

    @Override
    public SpeechletResponse onLaunch(LaunchRequest request, Session session) throws SpeechletException {
        return null;
    }

    @Override
    public SpeechletResponse onIntent(IntentRequest request, Session session) throws SpeechletException {
        return null;
    }

    @Override
    public void onSessionEnded(SessionEndedRequest request, Session session) throws SpeechletException {

    }

    @Override
    public SpeechletResponse getWelcomeResponse() {
        return null;
    }

    @Override
    public SpeechletResponse getHelpResponse() {
        return null;
    }

    @Override
    public SpeechletResponse createLinkCard(Session session) {
        return null;
    }




}
