package io.archinyx.multithreading

import io.archinyx.multithreading.models.Person
import io.archinyx.multithreading.models.PersonFullInfo
import io.archinyx.multithreading.services.FullPersonInfoService
import io.archinyx.multithreading.services.PersonService
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.BlockingQueue

fun main() {
    val personsQueue: BlockingQueue<List<Person>> = ArrayBlockingQueue(1)
    val fetchPersonsTask = PersonService(personsQueue)
    Thread(fetchPersonsTask).apply { start() }

    val persons = personsQueue.take()

    val fullPersonsInfoQueue: BlockingQueue<List<PersonFullInfo>> = ArrayBlockingQueue(1)
    val fetchFullPersonsInfoTask = FullPersonInfoService(persons, fullPersonsInfoQueue)
    Thread(fetchFullPersonsInfoTask).apply { start() }

    val fullInfo = fullPersonsInfoQueue.take()
    println("Full users info : \n $fullInfo")
}