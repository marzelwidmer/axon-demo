//package ch.keepcalm.account.commandmodel
//
//import ch.keepcalm.account.coreapi.*
//import org.axonframework.commandhandling.CommandHandler
//import org.axonframework.eventsourcing.EventSourcingHandler
//import org.axonframework.modelling.command.AggregateIdentifier
//import org.axonframework.modelling.command.AggregateLifecycle
//import org.axonframework.spring.stereotype.Aggregate
//import java.io.Serializable
//import java.util.*
//
//@Aggregate
//class BankAccount2: Serializable {
//
//    @AggregateIdentifier
//    private lateinit var bankAccountId: UUID
//
//    private var balance: Int = 0
//
//    constructor()
//
//    @CommandHandler
//    constructor(command: CreateBankAccountCommand) {
//        AggregateLifecycle.apply(BankAccountCreatedEvent(command.bankAccountId, command.balance))
//    }
//
//    @CommandHandler
//    fun handle(command: DepositCashCommand) {
//        AggregateLifecycle.apply(CashDepositedEvent(bankAccountId, command.amount))
//    }
//
//    @CommandHandler
//    @Throws(NotEnoughFundsException::class)
//    fun handle(command: WithdrawCashCommand) {
//        val amount = command.amount
//        if (balance - amount < 0) {
//            throw NotEnoughFundsException()
//        }
//        AggregateLifecycle.apply(CashWithdrawnEvent(bankAccountId, amount))
//    }
//
//    @EventSourcingHandler
//    fun on(event: BankAccountCreatedEvent) {
//        bankAccountId = event.bankAccountId
//        balance = event.balance
//    }
//
//    @EventSourcingHandler
//    fun on(event: CashDepositedEvent) {
//        balance.plus(event.amount)
//    }
//
//    @EventSourcingHandler
//    fun on(event: CashWithdrawnEvent) {
//        balance.minus(event.amount)
//    }
//}
