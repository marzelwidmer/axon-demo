package ch.keepcalm.account.api

import org.axonframework.eventhandling.EventHandler
import org.axonframework.queryhandling.QueryHandler
import org.springframework.stereotype.Component

@Component
class BankAccountEventHandler (private val bankAccountViewRepository: BankAccountViewRepository){

    @EventHandler
    fun on (event: BankAccountCreatedEvent) = bankAccountViewRepository.save(BankAccountView(id = event.id, balance = event.balance))

    @EventHandler
    fun on (event: CashDepositedEvent) = bankAccountViewRepository.save(BankAccountView(id = event.id, balance = event.amount))

    @EventHandler
    fun on (event: CashWithdrawnEvent) = bankAccountViewRepository.save(BankAccountView(id = event.id, balance = event.amount))

    @QueryHandler
    fun answer(query: FindAllBankAccountsQuery) = bankAccountViewRepository.findAll()
}