package ch.keepcalm.account

import ch.keepcalm.account.api.BankAccountCreatedEvent
import ch.keepcalm.account.api.CreateBankAccountCommand
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate
import org.springframework.util.Assert

@Aggregate
class BankAccountAggregate {

    @AggregateIdentifier
    private lateinit var id: String

    constructor() // Needed for AXON

    @CommandHandler
    constructor(command: CreateBankAccountCommand){
        val id = command.id

        Assert.hasLength(id, "Missing id")
        AggregateLifecycle.apply(BankAccountCreatedEvent(command.id, command.balance))
    }

    @EventSourcingHandler
    protected fun on(event: BankAccountCreatedEvent) {
        this.id = event.id
//        this.balance = event.balance
    }
}