package com.dev.ep02joaquinmoreno.exchange;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExchangeService {

    private final String API = "https://api.exchangerate.host/convert?";
    private RestTemplate restTemplate;


    public ExchangeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getTipoCambio(String from, String  to){
        String URLWithParams  = API + "from=" + from + "&" + "to=" + to;
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(URLWithParams, JsonNode.class);
        JsonNode resultNode = response.getBody().get("result");
        String result = resultNode.asText();
        return result;



    }




}
