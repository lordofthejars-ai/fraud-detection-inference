package org.acme;


import ai.djl.inference.Predictor;
import ai.djl.translate.TranslateException;

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;


@Path("/fraud")
public class FraudResource {

    @Inject Predictor<TransactionDetails, Boolean> predictor;

    @POST
    public Boolean detectFraud(TransactionDetails transactionDetails) throws  TranslateException {
        return predictor.predict(transactionDetails);
    }
}
