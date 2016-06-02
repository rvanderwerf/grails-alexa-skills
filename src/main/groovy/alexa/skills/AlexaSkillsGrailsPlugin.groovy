package alexa.skills

import grails.plugins.*


class AlexaSkillsGrailsPlugin extends Plugin {

    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "3.0 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    def version = "0.1.0"

    def title = "Alexa Skills" // Headline display name of the plugin
    def author = "Ryan Vanderwerf and Lee Fox"
    def authorEmail = "rvanderwerf@gmail.com"
    def description = '''\
This plugin helps you make Alexa skills/speechlets in Grails.
'''
    def profiles = ['web']

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugins/alexa-skills"


    // License: one of 'APACHE', 'GPL2', 'GPL3'
    def license = "APACHE2"



    Closure doWithSpring() { {->
        application.speechletClasses.each { GrailsSpeechletClass speechletClass ->
            configureSpeechletBeans.delegate = delegate
            configureSpeechletBeans(speechletClass)
        }

        }
    }

    void doWithDynamicMethods() {

    }

    void doWithApplicationContext() {

    }

    void onChange(Map<String, Object> event) {

    }

    void onConfigChange(Map<String, Object> event) {

    }

    void onShutdown(Map<String, Object> event) {

    }

    /**
     * Configure speechlet beans.
     */
    def configureSpeechletBeans = { GrailsSpeechletClass speechletClass ->
        def fullName = speechletClass.fullName

        try {
                "${speechletClass.propertyName}"(speechletClass.clazz) { bean ->
                    bean.autowire = "byName"
                }
        } catch (Exception e) {
            log.error("Error declaring ${fullName}Detail bean in context", e)
        }
    }
}
