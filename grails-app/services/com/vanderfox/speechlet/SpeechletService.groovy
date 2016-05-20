package com.vanderfox.speechlet

import com.amazon.speech.Sdk
import com.amazon.speech.speechlet.Speechlet
import com.amazon.speech.speechlet.SpeechletRequestHandler
import com.amazon.speech.speechlet.SpeechletRequestHandlerException
import com.amazon.speech.speechlet.verifier.*
import grails.transaction.Transactional
import org.apache.commons.io.IOUtils
import org.apache.commons.lang.StringUtils
import org.springframework.beans.factory.InitializingBean

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.util.concurrent.TimeUnit

@Transactional
class SpeechletService implements InitializingBean {

    def grailsApplication
    private transient SpeechletRequestHandler speechletRequestHandler;
    boolean disableRequestSignatureCheck = false

    public void afterPropertiesSet() throws Exception {


            // An invalid value or null will turn signature checking on.
            try {
                disableRequestSignatureCheck = grailsApplication.config.getProperty(Sdk.DISABLE_REQUEST_SIGNATURE_CHECK_SYSTEM_PROPERTY)
                if (!disableRequestSignatureCheck) {
                   log.warn("Could't find property ${Sdk.DISABLE_REQUEST_SIGNATURE_CHECK_SYSTEM_PROPERTY} trying System property")
                   disableRequestSignatureCheck =
                        Boolean.parseBoolean(System.getProperty(Sdk.DISABLE_REQUEST_SIGNATURE_CHECK_SYSTEM_PROPERTY))
                }
            } catch (Exception) {
                log.warn("Error getting system property ${Sdk.DISABLE_REQUEST_SIGNATURE_CHECK_SYSTEM_PROPERTY} defaulting to false")
            }

            List<SpeechletRequestVerifier> requestVerifiers = new ArrayList<SpeechletRequestVerifier>()
            requestVerifiers.add(getApplicationIdVerifier())
            TimestampSpeechletRequestVerifier timestampVerifier = getTimetampVerifier()
            if (timestampVerifier != null) {
                requestVerifiers.add(timestampVerifier)
            }

            speechletRequestHandler =
                    new SpeechletRequestHandler(requestVerifiers, Arrays.asList(
                            new ResponseSizeSpeechletResponseVerifier(),
                            new OutputSpeechSpeechletResponseVerifier(),
                            new CardSpeechletResponseVerifier()))
    }


    /**
     * Returns a {@link com.amazon.speech.speechlet.verifier.ApplicationIdSpeechletRequestVerifier} configured using the supported
     * application IDs provided by the system property
     * {@link Sdk#SUPPORTED_APPLICATION_IDS_SYSTEM_PROPERTY}. If the supported application IDs are
     * not defined, then an {@link com.amazon.speech.speechlet.verifier.ApplicationIdSpeechletRequestVerifier} initialized with an empty
     * set is returned.
     *
     * @return a configured {@link com.amazon.speech.speechlet.verifier.ApplicationIdSpeechletRequestVerifier}
     */
    private ApplicationIdSpeechletRequestVerifier getApplicationIdVerifier() {
        // Build an application ID verifier from a comma-delimited system property value
        Set<String> supportedApplicationIds = Collections.emptySet();
        String commaDelimitedListOfSupportedApplicationIds = grailsApplication.config.get("skillsSdk.supportedApplicationIds")
        if (!commaDelimitedListOfSupportedApplicationIds) {
            // try system env if config misssing
            log.warn("Couldn't find valid app Ids in grails config, trying system property ${Sdk.SUPPORTED_APPLICATION_IDS_SYSTEM_PROPERTY}")
            commaDelimitedListOfSupportedApplicationIds = System.getProperty(Sdk.SUPPORTED_APPLICATION_IDS_SYSTEM_PROPERTY);
        }
        //System.getProperty(Sdk.SUPPORTED_APPLICATION_IDS_SYSTEM_PROPERTY);

        if (!StringUtils.isBlank(commaDelimitedListOfSupportedApplicationIds)) {
            supportedApplicationIds =
                    new HashSet<String>(Arrays.asList(commaDelimitedListOfSupportedApplicationIds
                            .split(",")))
        }

        return new ApplicationIdSpeechletRequestVerifier(supportedApplicationIds)
    }

    /**
     * Returns a {@link TimestampSpeechletRequestVerifier} configured using timestamp tolerance
     * defined by the system property {@link Sdk#TIMESTAMP_TOLERANCE_SYSTEM_PROPERTY}. If a valid
     * timestamp tolerance is missing in the system properties, then {@code null} is returned.
     *
     * @return a configured TimestampSpeechletRequestVerifier or null
     */
    private TimestampSpeechletRequestVerifier getTimetampVerifier() {
        String timestampToleranceAsString =
                System.getProperty(Sdk.TIMESTAMP_TOLERANCE_SYSTEM_PROPERTY)

        if (!StringUtils.isBlank(timestampToleranceAsString)) {
            try {
                long timestampTolerance = Long.parseLong(timestampToleranceAsString)
                return new TimestampSpeechletRequestVerifier(timestampTolerance, TimeUnit.SECONDS)
            } catch (NumberFormatException ex) {
                log.warn("The configured timestamp tolerance {} is invalid, "
                        + "disabling timestamp verification", timestampToleranceAsString)
            }
        } else {
            log.warn("No timestamp tolerance has been configured, "
                    + "disabling timestamp verification")
        }

        return null
    }

    /**
     * this should lookup the provided auth token from account linking
     * and return the associated user
     * @param authToken
     * @return
     */
    User getUserForAccessToken(String authToken) {
        def token = AccessToken.findByValue(authToken)
        if (token) {
            return User.findByUsername(token.username)
        }
        return null


    }

    /**
     * Handles a POST request. Based on the request parameters, invokes the right method on the
     * {@code Speechlet}.
     *
     * @param request
     *            the object that contains the request the client has made of the servlet
     * @param response
     *            object that contains the response the servlet sends to the client
     * @throws IOExceptiont
     *             if an input or output error is detected when the servlet handles the request
     */
    void doSpeechlet(final HttpServletRequest request, final HttpServletResponse response, Speechlet speechlet)
            throws IOException {
        byte[] serializedSpeechletRequest = IOUtils.toByteArray(request.getInputStream());
        byte[] outputBytes = null;

        try {
            // this helps with debugging
            if (serializedSpeechletRequest) {
                File serializedRequest = new File("/tmp/speechlet-${System.currentTimeMillis()}.out")
                //ByteArrayOutputStream outputStream = new ByteArrayOutputStream(serializedRequest)
                //outputStream.write(serializedSpeechletRequest)
                serializedRequest.write(new String(serializedSpeechletRequest))

            }


            /*if (speechletService?.disableRequestSignatureCheck) {
                log.warn("Warning: Speechlet request signature verification has been disabled!");
            } else {
                // Verify the authenticity of the request by checking the provided signature &
                // certificate.
                SpeechletRequestSignatureVerifier.checkRequestSignature(serializedSpeechletRequest,
                        request.getHeader(Sdk.SIGNATURE_REQUEST_HEADER),
                        request.getHeader(Sdk.SIGNATURE_CERTIFICATE_CHAIN_URL_REQUEST_HEADER));
            }*/


            outputBytes = speechletRequestHandler.handleSpeechletCall(speechlet,serializedSpeechletRequest);
        } catch (SpeechletRequestHandlerException | NullPointerException | SecurityException ex) {
            int statusCode = HttpServletResponse.SC_BAD_REQUEST;
            log.error("Exception occurred in doPost, returning status code ${statusCode}", ex);
            response.sendError(statusCode, ex.getMessage());
            return;
        } catch (Exception ex) {
            int statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            log.error("Exception occurred in doPost, returning status code ${statusCode}", ex);
            response.sendError(statusCode, ex.getMessage());
            return;
        }

        // Generate JSON and send back the response
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        def out  = response.getOutputStream()
        response.setContentLength(outputBytes.length);
        out.write(outputBytes);

    }
}
