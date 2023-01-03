package io.github.marwoj.reactorperformance

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@SpringBootApplication
@EnableReactiveMongoRepositories
class ReactorPerformanceApplication

fun main(args: Array<String>) {
    runApplication<ReactorPerformanceApplication>(*args)
}
