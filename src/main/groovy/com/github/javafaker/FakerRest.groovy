package com.github.javafaker

import wslite.rest.RESTClient;

public class FakerRest {

    private final Faker faker;

    protected FakerRest(Faker faker) {
        this.faker = faker;
    }

    public String rest(String baseUrl, String path, String xpath) {
        String parseBaseUrl = baseUrl.replace("+",".");
        String parsePath = path.replace("+",".");
        String parseXpath = xpath.replace("+",".");

        // Seteamos el baseUrl que vamos a invocar
        def client = new RESTClient(parseBaseUrl)

        // Desactivamos validacion de ssl
        client.httpClient.sslTrustAllCerts = true

        def headers = [
                'Accept': 'application/json',
                'Accept-Language': 'en-US,en;q=0.9,es;q=0.8',
                'Content-Type' : 'application/json',
                'user-agent': 'Mozilla/5.0'
        ]

        // Seteamos el path
        // Seteamos un map con los queryString
        def response1 = client.get(
                isSSLTrustAllCertsSet:false,
                headers: headers,
                path:parsePath,
                query:[])

        // Decidimos que xpath nos intersa obtener
        String [] xpaths = parseXpath.split('\\.')

        def result = response1.json
        xpaths.each {
            result = result[it]
        }

        return result
    }

}
