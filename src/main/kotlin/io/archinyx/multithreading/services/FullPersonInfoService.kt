package io.archinyx.multithreading.services

import io.archinyx.multithreading.models.Address
import io.archinyx.multithreading.models.Nationality
import io.archinyx.multithreading.models.Person
import io.archinyx.multithreading.models.PersonFullInfo
import java.util.concurrent.BlockingQueue

class FullPersonInfoService(
    private val persons: List<Person>,
    private val queue: BlockingQueue<List<PersonFullInfo>>
) : Runnable {

    private val nationalities = hashMapOf<String, Nationality>()

    override fun run() {
        println("\n Start handling user info")

        val fullInfo = persons.map { person ->
            val address = fetchUserAddress(person.id)
            val nationality = fetchUserNationality(address.city)
            PersonFullInfo(person, address, nationality)
        }

        queue.put(fullInfo)
    }

    private fun fetchUserAddress(userId: Long): Address {
        println("Fetch address for user $userId")

        Thread.sleep(500)
        val address = addresses[userId] ?: defaultAddress

        println("Address of user $userId is ${address.city}")
        return address
    }

    private fun fetchUserNationality(city: String): Nationality {
        println("Fetch nationality for city $city")

        Thread.sleep(500)
        val nationality = nationalities[city] ?: retrieveNationality(city)

        println("Nationality of city $city is ${nationality.value} \n")
        return nationality
    }

    private val addresses = hashMapOf<Long, Address>(
        Pair(1, Address("Los-Angeles")),
        Pair(2, Address("Munich")),
        Pair(3, Address("Munich")),
        Pair(4, Address("Bangkok")),
        Pair(5, Address("Bangkok"))
    )

    private val defaultAddress = Address("Los-Angeles")

    private fun retrieveNationality(city: String) =
        when (city) {
            "Los-Angeles" -> Nationality("American")
            "Munich" -> Nationality("German")
            "Bangkok" -> Nationality("Thai")

            else -> Nationality("American")
        }.also { nationality -> nationalities[city] = nationality }

}