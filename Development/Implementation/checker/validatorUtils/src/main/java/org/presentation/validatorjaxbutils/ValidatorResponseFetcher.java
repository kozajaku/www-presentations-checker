package org.presentation.validatorjaxbutils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This is static factory class offering static method to fetch response from
 * remote w3c validators.
 *
 * @author radio.koza
 */
public class ValidatorResponseFetcher {

    /**
     * Constructor is private - forbidden
     */
    private ValidatorResponseFetcher() {
    }
    
    /**
     * Static method which is cappable to fetch soap xml response from remote w3c validator service.
     * @param validationService URL address in string representation of specific validator service
     * @param uriToValidate URI address in string of the page, which should be validated by validator service
     * @return {@link InputStream} of the successfully fetched soap response
     * @throws IOException If anything went wrong during fetching request or creating connection with validationService.
     */
    public static InputStream fetchSOAPResponse(String validationService, String uriToValidate) throws IOException{
        URL url = new URL(validationService + "?output=soap12&uri=" + uriToValidate);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        return connection.getInputStream();
    }
}
