package com.dev.ep02joaquinmoreno.secrets;

import com.dev.ep02joaquinmoreno.shared.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecretService {

    private static final String KEY = "api-key";
    @Autowired
    private SecretRepository secretRepository;

    public String getApiKey() throws SecretException {
        Optional<Secret> secretToFind = secretRepository.findByKey(KEY);
        if(secretToFind.isEmpty()){
            throw new SecretException(Constants.CODE_WHEN_EXCEPTION_FETCHING_SECRET);
        }else{
            return secretToFind.get().getValue();
        }
    }
}
