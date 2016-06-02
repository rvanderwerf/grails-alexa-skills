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


import grails.core.ArtefactHandlerAdapter
import groovy.util.logging.Slf4j
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.InnerClassNode
import org.grails.compiler.injection.GrailsASTUtils
import java.lang.reflect.Modifier
import java.util.regex.Pattern
import static org.grails.io.support.GrailsResourceUtils.GRAILS_APP_DIR
import static org.grails.io.support.GrailsResourceUtils.REGEX_FILE_SEPARATOR

/**
 * Grails artifact handler for speechlet classes.
 *
 * @author Ryan Vanderwerf
 */
@Slf4j
public class SpeechletArtefactHandler extends ArtefactHandlerAdapter {

    public static final String TYPE = "Speechlet"
    public SpeechletArtefactHandler() {
        super(TYPE, GrailsSpeechletClass.class, DefaultGrailsSpeechletClass.class, TYPE);
    }

    public boolean isArtefactClass(Class clazz) {
        return clazz != null && clazz.getName().endsWith(TYPE);
    }
    public static Pattern SPEECHLET_PATH_PATTERN = Pattern.compile(".+" + REGEX_FILE_SEPARATOR + GRAILS_APP_DIR + REGEX_FILE_SEPARATOR + "speechlets" + REGEX_FILE_SEPARATOR + "(.+)\\.(groovy)");



    boolean isArtefact(ClassNode classNode) {
        log.debug("eval artifact ${classNode.getName()}")
        if(classNode == null ||
           !isValidArtefactClassNode(classNode, classNode.getModifiers()) ||
           !classNode.getName().endsWith(TYPE)) {
            log.debug("!classNode.isEnum()=${classNode.isEnum()} !classNode.isInterface()=${classNode.isInterface()} !Modifier.isAbstract(modifiers)=${Modifier.isAbstract(classNode.getModifiers())} !(classNode instanceof InnerClassNode)=${((classNode instanceof InnerClassNode))} innerClassNode=${InnerClassNode}")
            log.debug("********* is artefact false, isValidArtefactClassNode=${isValidArtefactClassNode(classNode, classNode.getModifiers())} or class name ${classNode.getName()} does not end with ${SpeechletArtefactHandler.TYPE}")

            return false
        }

        URL url = GrailsASTUtils.getSourceUrl(classNode)
        log.debug("url=${url.toString()} url.getFile()=${url.getFile().toString()}")
        url &&  SPEECHLET_PATH_PATTERN.matcher(url.getFile()).find()
    }


}
