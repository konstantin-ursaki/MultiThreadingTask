package io.archinyx.multithreading.services

import io.archinyx.multithreading.models.Person
import java.util.concurrent.BlockingQueue

class PersonService(private val queue: BlockingQueue<List<Person>>) : Runnable {

    override fun run() {
        println("Start downloading list of first data")

        Thread.sleep(2000)

        val data = fetchPersons()
        println("Finished downloading list of first data")

        queue.put(data)
    }

    private fun fetchPersons(): List<Person> =
        listOf(
            Person(1, "Joe Smith"),
            Person(2, "Samantha Jackson"),
            Person(3, "Boris Johnson"),
            Person(4, "Will Smith"),
            Person(5, "Nickolas Trump")
        )

}