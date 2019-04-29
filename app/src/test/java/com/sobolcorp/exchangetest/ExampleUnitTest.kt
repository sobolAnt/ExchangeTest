package com.sobolcorp.exchangetest

import com.sobolcorp.exchangetest.customview.calculateAmount
import com.sobolcorp.exchangetest.models.Currency
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun currency_simple_isCorrect() {
        val request = calculateAmount(Currency("EUR", 1f), Currency("EUR", 1f), 1)
        assertEquals(request, "1.0")
    }

    @Test
    fun currency_complex_isCorrect() {
        val request = calculateAmount(Currency("GBP", 0.8634f), Currency("USD", 1.1133f), 123)
        assertEquals(request, "92.2623")
    }
}
