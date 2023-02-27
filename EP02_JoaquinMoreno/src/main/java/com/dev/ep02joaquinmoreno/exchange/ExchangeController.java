package com.dev.ep02joaquinmoreno.exchange;

import com.dev.ep02joaquinmoreno.shared.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ExchangeController {
    @Autowired
    private TokenService currencyService;
    @Autowired
    private ExchangeService exchangeService;


    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @GetMapping("/tipo-cambio")
    public ResponseEntity<String> getExchange(@RequestHeader("Authorization") String authorization) {
        try {

            String Currency1 =  currencyService.getClaimsFromJWT(authorization).get("from");
            String Currency2 =  currencyService.getClaimsFromJWT(authorization).get("to");

            Float result =  Float.valueOf(exchangeService.getTipoCambio(Currency1, Currency2)) ;

            return ResponseEntity.ok().body(result.toString());


        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }
    @GetMapping("/valor-cambio")
    public ResponseEntity<String> getExchangeAmount(@RequestHeader("Authorization") String authorization) throws Exception {


        try {
            if(currencyService.getClaimsFromJWT(authorization).get("from") == null ||
                    currencyService.getClaimsFromJWT(authorization).get("to")== null ||
                    currencyService.getClaimsFromJWT(authorization).get("amount")== null){
                return ResponseEntity.status(400).body("Missing Claims");
            }

            String Currency1 =  currencyService.getClaimsFromJWT(authorization).get("from");
            String Currency2 =  currencyService.getClaimsFromJWT(authorization).get("to");
            Float amount  = Float.valueOf(currencyService.getClaimsFromJWT(authorization).get("amount"));
            Float result =  Float.valueOf(exchangeService.getTipoCambio(Currency1, Currency2))  * amount;

            return ResponseEntity.ok().body(result.toString());


        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }





    @GetMapping("/tipo-cambio/token")
    public ResponseEntity<String> generateTokenCambio(@RequestParam String from, @RequestParam String to){
        try{
            if(from.isEmpty() || to.isEmpty()  )
            {
                return ResponseEntity.badRequest().body(Constants.ERROR_MESSAGE_WHEN_INCORRECT_REQUEST);
            }
            return ResponseEntity.ok().body(
                    currencyService.generateTokenCambio(from,to));
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/valor-cambio/token")
    public ResponseEntity<String> generateTokenValor(@RequestParam String from, @RequestParam String to, @RequestParam Float amount){
        try{
            if(from.isBlank() || to.isBlank()  ||   amount == null)
            {
                return ResponseEntity.badRequest().body(Constants.ERROR_MESSAGE_WHEN_INCORRECT_REQUEST);
            }
            return ResponseEntity.ok().body(
                    currencyService.generateTokenValor(from,to,amount));

        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @ExceptionHandler(MissingServletRequestParameterException.class)
    public String handleMissingParams(MissingServletRequestParameterException ex) {
        String name = ex.getParameterName();
        return "ERROR: '" + name + "' parameter not found";
        // Actual exception handling
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public String handleMissingParams(MissingRequestHeaderException ex) {
        String name = ex.getHeaderName();
        return "ERROR: '" + name + "' header not found";
        // Actual exception handling
    }






}
