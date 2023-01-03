package io.github.marwoj.reactorperformance

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface ActivityRepository : ReactiveMongoRepository<Activity, String> {
    fun findAllByGroupNumberIndexed(groupNumber: Int): Flux<Activity>
    fun findAllByGroupNumberNonIndexed(groupNumber: Int): Flux<Activity>
}