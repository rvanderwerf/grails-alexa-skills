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
import org.grails.core.AbstractInjectableGrailsClass;

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
public class DefaultGrailsSpeechletClass extends AbstractInjectableGrailsClass implements GrailsSpeechletClass {

    public DefaultGrailsSpeechletClass(Class c) {
        super(c, SpeechletArtefactHandler.TYPE);
    }
  /*  public class DefaultGrailsSpeechletClass extends AbstractInjectableGrailsClass implements GrailsGuardClass {
        public DefaultGrailsGuardClass(Class c) {
            super(c, GuardArtefactHandler.TYPE);
        }
    }*/
   /* public static final String SPEECHLET = "Speechlet";


    public DefaultGrailsSpeechletClass(Class clazz) {
        super(clazz, SPEECHLET);

    }


    public void setConfiguration(Config co) {
            getMetaClass().invokeMethod(getReferenceInstance(), "SetConfiguration", new Object[]{});
    }


    public void onSessionStarted(SessionStartedRequest request, Session session) throws SpeechletException {

            getMetaClass().invokeMethod(getReferenceInstance(), "onSessionStarted", new Object[]{});
    }


    public SpeechletResponse onLaunch(LaunchRequest request, Session session) throws SpeechletException {
           getMetaClass().invokeMethod(getReferenceInstance(), "onLaunch", new Object[]{});
           return null;
    }


    public SpeechletResponse onIntent(IntentRequest request, Session session) throws SpeechletException {
        getMetaClass().invokeMethod(getReferenceInstance(), "onIntent", new Object[]{});
        return null;
    }


    public void onSessionEnded(SessionEndedRequest request, Session session) throws SpeechletException {
        getMetaClass().invokeMethod(getReferenceInstance(), "onSessionEnded", new Object[]{});

    }


    public SpeechletResponse getWelcomeResponse() {
        return null;
    }


    public SpeechletResponse getHelpResponse() {
        return null;
    }


    public SpeechletResponse createLinkCard(Session session) {
        return null;
    }

*/


}
