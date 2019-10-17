package ch.keepcalm.account

import ch.keepcalm.account.api.*
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate
import org.slf4j.LoggerFactory
import org.springframework.util.Assert

@Aggregate
class BankAccountAggregate {

    private val log = LoggerFactory.getLogger(BankAccountAggregate::class.java)

    @AggregateIdentifier
    private lateinit var id: String

    private var balance: Int = 0

    constructor() // Needed for AXON

    // CreateBankAccount
    @CommandHandler
    constructor(command: CreateBankAccountCommand){
        this.id = command.id
        log.debug("handling {}", id)
        Assert.hasLength(id, "Missing id")
        AggregateLifecycle.apply(BankAccountCreatedEvent(command.id, command.balance))
    }
    @EventSourcingHandler
    protected fun on(event: BankAccountCreatedEvent) {
        this.id = event.id
        this.balance = event.balance
        log.debug("event {}", id)
    }


    // WithdrawCash
    @CommandHandler
    @Throws(NotEnoughFundsException::class)
    protected fun handle(command: WithdrawCashCommand) {
        val amount = command.amount
        if (balance - amount < 0) {
            throw NotEnoughFundsException()
        }
        log.debug("handling {}", command.amount)
        AggregateLifecycle.apply(CashWithdrawnEvent(id= id, amount = amount))
    }
    @EventSourcingHandler
    protected fun on(event: CashWithdrawnEvent) {
        balance = balance.minus(event.amount)
        log.debug("event {}", balance)
    }


    // DepositCash
    @CommandHandler
    protected fun handle(command: DepositCashCommand) {
        log.debug("handling {}", command.amount)
        AggregateLifecycle.apply(CashDepositedEvent(id=command.id, amount = command.amount))
    }
    @EventSourcingHandler
    protected fun on(event: CashDepositedEvent){
        balance = balance.plus(event.amount)
        log.debug("event {}", balance)
    }

}