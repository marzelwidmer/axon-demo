import ch.keepcalm.account.coreapi.*
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate
import org.slf4j.LoggerFactory

@Aggregate
class BankAccount /*: Serializable */ {

    private val log = LoggerFactory.getLogger(BankAccount::class.java)

    @AggregateIdentifier
    private lateinit var bankAccountId: String

    private var balance: Int = 0

    constructor()

    @CommandHandler
    constructor(command: CreateBankAccountCommand) {
        AggregateLifecycle.apply(BankAccountCreatedEvent(command.bankAccountId,
                command.balance))
    }

    // Add some money
    @CommandHandler
    @Throws(NotEnoughFundsException::class)
    fun on(command: DepositCashCommand) = AggregateLifecycle.apply(

            CashDepositedEvent(bankAccountId = bankAccountId, amount = command.amount)
    )

    // Take some money
    @CommandHandler
    @Throws(InsufficientBalanceException::class)
    fun on(command: WithdrawCashCommand) {
        val amount = command.amount
        if (balance - amount < 0) {
            throw InsufficientBalanceException("The 'amount' <= 0 is an invalid value.")
        }
        AggregateLifecycle.apply(CashWithdrawnEvent(bankAccountId,command.amount))

//        require(command.amount > 0) { "The 'amount' <= 0 is an invalid value." }
//        if(command.amount < 0) { throw NotEnoughFundsException() }

    }

    @EventSourcingHandler
    fun on(event: BankAccountCreatedEvent) {
        bankAccountId = event.bankAccountId
        balance = event.balance
        log.debug("event {}", "${bankAccountId} ${balance}")
    }

    @EventSourcingHandler
    fun on(event: CashDepositedEvent) {
        balance.plus(event.amount)
        log.debug("event {}", event.amount)
    }

    @EventSourcingHandler
    fun on(event: CashWithdrawnEvent) {
        balance.minus(event.amount)
        log.debug("event {}", event.amount)
    }


}

