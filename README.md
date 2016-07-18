This is the Alexa skills Plugin!

This plugin helps you create Speechlets/Skills on Grails. It will create a starter class for you
so you can get running quickly making your own skills.

Installation
============

add the following to the dependencies {} block to your build.gradle:
```
dependencies {
     compile "org.grails.plugins:alexa-skills:0.1.1"
}
```

also if you have issues resolving the plugin, try adding my bintray repo (NOT to buildscript block but the lower one):

```

repositories {
    maven { url  "http://dl.bintray.com/rvanderwerf/alexa-skills" }
}

How it works
============
run 'grails create-speechlet <classPrefix>'
This will create a speechlet class file located in grails-app/speechlets. Do not use the suffix 'Speechlet' because that will be automatically appended to the name.


Configuration
=============

Here's the config values you can set in application.yml or application.groovy:


**alexaSkills.supportedApplicationIds (String)**
 
 - this is a comma delimited list of application Ids you speechlet will recognize. 
 - you can get ID this under Skills->create on developer.amazon.com
 
 
**alexaSkills.disableVerificationCheck (boolean)**

 - if you are debugging and sending in canned request (see 'serializeRequests') via CURL turn this on
 - be very careful not to disable this in a public service as it turns off all checking the request is really coming from Amazon.
 
**alexaSkills.serializeRequests (boolean)**

 - if you are having problems wondering what is going on with your skill or want to capture a request and replay it in CURL it will write them to disk

**alexaSkills.serializeRequestsOutputPath (boolean)** 

 - if you turn on serialization requests this is the path the files will be saved to. The files will be names in the format:
 - files will be saved in the format speechlet-${System.currentTimeMillis()}.out
 
 Recommended usage with a Controller
 =============
 
 `class TestController {
 
     def speechletService
     def testSpeechlet
 
 
     def index() {
         speechletService.doSpeechlet(request,response, testSpeechlet)
     }
 
 }`
 
 
Here you see the speechlet is turned into a Spring bean, and you invoke the speechlet service and pass it in.
Likely we could turn the speechlet artefact class into a controller itself in the future to skip this.
If you are using Spring security, make sure to whitelist the urlmapping for the index.
