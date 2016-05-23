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

package alexa.skills

import com.amazon.speech.speechlet.SessionStartedRequest
import grails.core.ArtefactHandlerAdapter
import org.codehaus.groovy.ast.ClassNode
import org.grails.compiler.injection.GrailsASTUtils
import org.springframework.util.ReflectionUtils

import java.lang.reflect.Method
import java.util.regex.Pattern

import static org.grails.io.support.GrailsResourceUtils.GRAILS_APP_DIR
import static org.grails.io.support.GrailsResourceUtils.REGEX_FILE_SEPARATOR

/**
 * Grails artifact handler for speechlet classes.
 *
 * @author Marc Palmer (marc@anyware.co.uk)
 * @author Sergey Nebolsin (nebolsin@gmail.com)
 * @author Ryan Vanderwerf
 * @author Lee Fox
 * @since 0.1
 */
public class SpeechletArtefactHandler extends ArtefactHandlerAdapter {

    public static final String TYPE = "Speechlet"
    public SpeechletArtefactHandler() {
        super(TYPE, GrailsSpeechletClass.class, DefaultGrailsSpeechletClass.class, null);
    }

    public boolean isArtefactClass(Class clazz) {
        return clazz != null && clazz.getName().endsWith(TYPE);
    }
   /* public static Pattern SPEECHLET_PATH_PATTERN = Pattern.compile(".+" + REGEX_FILE_SEPARATOR + GRAILS_APP_DIR + REGEX_FILE_SEPARATOR + "speechlets" + REGEX_FILE_SEPARATOR + "(.+)\\.(groovy)");

    public SpeechletArtefactHandler() {
        super(TYPE, GrailsSpeechletClass.class, DefaultGrailsSpeechletClass.class, TYPE)
    }

    boolean isArtefact(ClassNode classNode) {
        if(classNode == null ||
           !isValidArtefactClassNode(classNode, classNode.getModifiers()) ||
           classNode.getName().endsWith(DefaultGrailsSpeechletClass.SPEECHLET)) {
            return false
        }

        URL url = GrailsASTUtils.getSourceUrl(classNode)

        url &&  SPEECHLET_PATH_PATTERN.matcher(url.getFile()).find()
    }

    boolean isArtefactClass(Class clazz) {
        // class shouldn't be null and should ends with Speechlet suffix
        return  (clazz != null && clazz.getName().endsWith(DefaultGrailsSpeechletClass.SPEECHLET))
    }*/
}
