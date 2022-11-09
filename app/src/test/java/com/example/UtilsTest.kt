package com.example
import com.example.realestatemanager.Utils
import com.google.common.truth.Truth.assertThat

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.math.roundToInt


@RunWith(JUnit4::class)
class UtilsTest {





    @Test fun utilsTodayDate_ShouldReturn_dd_MM_yyyy_format(){
        val string = Utils.todayDate
        val matcher = "^([0-2][0-9]|(3)[0-1])(/)(((0)[0-9])|((1)[0-2]))(/)\\d{4}\$"
        assertThat(string).matches(matcher)
    }

    @Test fun convertDollarToEuro_ShouldReturnTheRightConversion(){
        assertThat(Utils.convertDollarToEuro(1)).isEqualTo((1 * Utils.EURO_MULTIPLIER).roundToInt())
    }

    @Test fun convertEuroToDollar_ShouldReturnTheRightConversion(){
        assertThat(Utils.convertEuroToDollar(1)).isEqualTo((1 * Utils.DOLLAR_MULTIPLIER).roundToInt())
    }

}