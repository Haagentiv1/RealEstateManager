package com.example.realestatemanager.ui.utils

import android.content.Context
import android.net.wifi.WifiManager
import android.util.Log
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.roundToInt

/**
 * Created by Philippe on 21/02/2018.
 */
object Utils {

    const val EURO_MULTIPLIER : Double = 1.0
    const val DOLLAR_MULTIPLIER : Double = 1.0
    /**
     * Conversion d'un prix d'un bien immobilier (Dollars vers Euros)
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param dollars
     * @return
     */
    fun convertDollarToEuro(dollars: Int): Int {
        return (dollars * EURO_MULTIPLIER).roundToInt()
    }

    fun convertEuroToDollar(euros : Int) : Int {
        return (euros * DOLLAR_MULTIPLIER).roundToInt()
    }

    /**
     * Conversion de la date d'aujourd'hui en un format plus approprié
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @return
     */
    val todayDate: String
        get() {
            val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)
            return dateFormat.format(Date())
        }

    fun stringToDate(localDate: LocalDate): String? {
        val dt = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return dt.format(localDate)

    }

    /**
     * Vérification de la connexion réseau
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param context
     * @return
     */
    fun isInternetAvailable(context: Context): Boolean {
        val wifi = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        return wifi.isWifiEnabled
    }


}