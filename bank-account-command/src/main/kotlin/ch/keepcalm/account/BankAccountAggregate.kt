package ch.keepcalm.account

import ch.keepcalm.account.api.*
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

    private var balance: Int = 0


    constructor() // Needed for AXON


    // CreateBankAccount
    @CommandHandler
    constructor(command: CreateBankAccountCommand){
        val id = command.id

        Assert.hasLength(id, "Missing id")
        AggregateLifecycle.apply(BankAccountCreatedEvent(command.id, command.balance))
    }
    @EventSourcingHandler
    protected fun on(event: BankAccountCreatedEvent) {
        this.id = event.id
        this.balance = event.balance
    }


    // WithdrawCash
    @CommandHandler
    @Throws(NotEnoughFundsException::class)
    protected fun handle(command: WithdrawCashCommand) {
        val amount = command.amount
        if (balance - amount < 0) {
            throw NotEnoughFundsException()
        }
        AggregateLifecycle.apply(CashWithdrawnEvent(id= id, amount = amount))
    }
    @EventSourcingHandler
    fun on(event: CashWithdrawnEvent) {
        balance.minus(event.amount)
    }


}