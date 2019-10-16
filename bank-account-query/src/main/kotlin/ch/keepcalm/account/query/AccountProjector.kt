package ch.keepcalm.account.query

import ch.keepcalm.account.event.DepositEvent
import ch.keepcalm.account.event.WithdrawEvent
import org.axonframework.eventhandling.EventHandler
import org.axonframework.queryhandling.QueryHandler
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import javax.persistence.EntityManager

// create the model and answer the query
//@Profile("query")
@Component
data class AccountProjector(private val em: EntityManager) {

    private val log = LoggerFactory.getLogger(AccountProjector::class.java)

    // Save a new Summary
    @EventHandler
    fun on(event: DepositEvent) {
        log.debug("projection {}", event)
        em.persist(AccountSummary(id = event.id, initialValue = event.amount, remainingValue = event.amount))
    }

    // Find a summary
    @EventHandler
    fun on(event: WithdrawEvent) {
        log.debug("projection {}", event)
        em.find(AccountSummary::class.java, event.id).remainingValue -= event.amount
    }

    @QueryHandler
    fun handle(query: AccountSummaryQuery) = em.find(AccountSummary::class.java, query.id)
}