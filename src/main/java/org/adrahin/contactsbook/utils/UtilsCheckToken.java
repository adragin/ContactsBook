package org.adrahin.contactsbook.utils;

import org.adrahin.contactsbook.model.ErrorResponse;
import org.adrahin.contactsbook.model.ResponseAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UtilsCheckToken {

    @Autowired
    private ResponseAPI responseAPI;

    public ResponseEntity<ResponseAPI> isTokenCorrect(String token) {
        if (token == null || token.isEmpty()) {
            responseAPI.response = new ErrorResponse(400, "Authorization header is missing.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseAPI);
        }
        return null;
    }
}
