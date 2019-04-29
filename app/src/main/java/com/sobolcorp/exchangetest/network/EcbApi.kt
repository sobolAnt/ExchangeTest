package com.sobolcorp.exchangetest.network

import com.sobolcorp.exchangetest.models.Envelop
import io.reactivex.Observable
import retrofit2.http.GET

interface EcbApi {

    @GET("/stats/eurofxref/eurofxref-daily.xml")
    fun getEuropeanCentralBankExchangeList(): Observable<Envelop>
}