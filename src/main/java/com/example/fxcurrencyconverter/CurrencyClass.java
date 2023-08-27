package com.example.fxcurrencyconverter;

import com.example.fxcurrencyconverter.api_credentials.ApiCredentials;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Set;

public class CurrencyClass {
    public static RatesRecord ratesRecord(String baseRate){
        ObjectMapper mapper = new ObjectMapper();
        HttpClient client = HttpClient.newHttpClient();
        String uri = ApiCredentials.URI.concat(baseRate);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header(
                        ApiCredentials.KEY,
                        ApiCredentials.TOKEN)
                .build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            return mapper.readValue(response.body(), new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String rateConversion(String baseRate, double amountToConvert, String toRate)  {
        Map<String, Double> conversionRates;
        conversionRates = ratesRecord(baseRate).conversion_rates();
        double amount = (conversionRates.get(baseRate) * amountToConvert) * conversionRates.get(toRate);
        return String.format("%.2f",amount).replace(",",".");
    }

    public static Set<String > currencyCodes(){
        return ratesRecord("USD").conversion_rates().keySet();
    }


}
