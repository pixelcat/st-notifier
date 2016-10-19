package org.menagerie.stnotifier.video.youtube;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.FileDataStoreFactory;
import org.menagerie.stnotifier.video.controller.PluggableVerificationCodeReceiver;
import org.menagerie.stnotifier.video.controller.PluggableVerificationCodeReceiverImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 10/17/16, 3:13 PM
 */
public class OAuth2Adapter
{
    @Autowired
    PluggableVerificationCodeReceiver pluggableVerificationCodeReceiver;

    /**
     * Define a global instance of the HTTP transport.
     */
    private HttpTransport httpTransport = new NetHttpTransport();

    /**
     * Define a global instance of the JSON factory.
     */
    private JsonFactory jsonFactory = new JacksonFactory();

    /**
     * This is the directory that will be used under the user's home directory where OAuth tokens will be stored.
     */
    private static final String CREDENTIALS_DIRECTORY = ".oauth-credentials";

    /**
     * Authorizes the installed application to access user's protected data.
     *
     * @param scopes              list of scopes needed to run youtube upload.
     * @param credentialDatastore name of the credential datastore to cache OAuth tokens
     */
    public Credential authorize(List<String> scopes, String credentialDatastore) throws IOException
    {

        // Load client secrets.
        Reader clientSecretReader = new InputStreamReader(OAuth2Adapter.class.getResourceAsStream("/client_secrets.json"));
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory, clientSecretReader);

        // Checks that the defaults have been replaced (Default = "Enter X here").
        if (clientSecrets.getDetails().getClientId().startsWith("Enter")
            || clientSecrets.getDetails().getClientSecret().startsWith("Enter ")) {
            System.out.println(
                    "Enter Client ID and Secret from https://console.developers.google.com/project/_/apiui/credential "
                    + "into src/main/resources/client_secrets.json");
            System.exit(1);
        }

        // This creates the credentials datastore at ~/.oauth-credentials/${credentialDatastore}
        FileDataStoreFactory fileDataStoreFactory = new FileDataStoreFactory(new File(System.getProperty("user.home") + "/" + CREDENTIALS_DIRECTORY));
        DataStore<StoredCredential> datastore = fileDataStoreFactory.getDataStore(credentialDatastore);

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, jsonFactory, clientSecrets, scopes).setCredentialDataStore(datastore)
                .build();

        // Authorize.
        return new AuthorizationCodeInstalledApp(flow, pluggableVerificationCodeReceiver).authorize("user");
    }

    public void close() throws IOException
    {

    }
}
