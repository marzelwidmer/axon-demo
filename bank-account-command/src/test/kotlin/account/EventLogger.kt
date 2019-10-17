package ch.keepcalm.account

import org.axonframework.eventhandling.EventHandler
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class EventLogger {

    private val log = LoggerFactory.getLogger(EventLogger::class.java)

    @EventHandler
    fun on(any: Any) {
        log.info("Handling event: {}", any.javaClass)
    }
}