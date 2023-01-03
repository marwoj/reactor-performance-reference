package io.github.marwoj.reactorperformance

import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux

@RestController
class ActivitiesController(
    val activityRepository: ActivityRepository,
) {
    @GetMapping("/activities/indexed")
    fun getActivitiesIndexed(@RequestParam groupNumber: Int) =
        activityRepository.findAllByGroupNumberIndexed(groupNumber)

    @GetMapping("/activities/non-indexed")
    fun getActivitiesNonIndexed(@RequestParam groupNumber: Int) =
        activityRepository.findAllByGroupNumberNonIndexed(groupNumber)

    @PostMapping("/activities")
    fun addActivities(@RequestBody activityCount: ActivityCount): Mono<Void> =
        activityCount.groupIdToIdSequence().toFlux() // [(1, 1), (1, 2), (2, 1), (2, 2), (3, 1), (3, 2)]
            .window(10000)
            .delayUntil { ids ->
                ids.map {
                    Activity(
                        title = "Activity: group: ${it.first}, activity: ${it.second}",
                        groupNumberIndexed = it.first, groupNumberNonIndexed = it.first
                    )
                }
                    .collectList()
                    .delayUntil { activityRepository.saveAll(it) }
            }.then()

    @DeleteMapping("/activities")
    fun deleteAllActivities() = activityRepository.deleteAll()
}

data class ActivityCount(
    val groupsNumber: Int,
    val activitiesInGroup: Int,
)

fun ActivityCount.groupIdToIdSequence(): List<Pair<Int, Int>> =
    (1..groupsNumber)
        .flatMap { groupNumber ->
            (1..activitiesInGroup)
                .map { activityNumber -> groupNumber to activityNumber }
        }