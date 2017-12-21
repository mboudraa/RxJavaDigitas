package io.smily.rxjavadigitas

import android.net.ConnectivityManager
import android.os.Looper
import android.os.NetworkOnMainThreadException
import org.threeten.bp.ZonedDateTime
import java.util.Random

class SearchApi(private val connectivityManager: ConnectivityManager, private val activateFailure: Boolean) {


    private val isOnline: Boolean
        get() {
            val info = connectivityManager.activeNetworkInfo
            return info != null && info.isAvailable && info.isConnected
        }

    fun search(text: String, validity: ZonedDateTime): List<Product> {
        if (!isOnline) throw Exception("Not Connected to Internet")
        if (Looper.getMainLooper() == Looper.myLooper()) throw NetworkOnMainThreadException()

        Thread.sleep(1000)
        if (activateFailure && text.equals("ban", true)) throw Exception("Search failed with query 'ban'")

        return (1..10).fold(arrayListOf<Pair<Int, String>>()) { names, index -> names.apply { addAll(data.map { name -> index to "$name $index" }) } }
                .filter { (_, name) -> name.contains(text, ignoreCase = true) }
                .map { (index, name) -> Product(name, index * (Random().nextInt(2) + 1), validity) }
    }

    private val data = listOf("Paquito - Compote pomme banane",
                              "Paturages - yaourt banane",
                              "Pate à tartiner Banane",
                              "Jus d'orange",
                              "Jus de pomme",
                              "Pomme 300g",
                              "oranges au kg",
                              "jus d'oranges pressé",
                              "nutella",
                              "Merchurochrome - Pansemants bande économique",
                              "Bandes de cire froide",
                              "Flan, la bande de 630g"
    )
}