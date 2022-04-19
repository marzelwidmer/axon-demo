package ch.keepcalm.account.api

import org.axonframework.eventhandling.EventHandler
import org.axonframework.queryhandling.QueryHandler
import org.springframework.stereotype.Component

@Component
class BankAccountEventHandler (private val bankAccountViewRepository: BankAccountViewRepository){

    @EventHandler
    fun on (event: BankAccountCreatedEvent) = bankAccountViewRepository.save(BankAccountView(id = event.id, balance = event.balance))

    @EventHandler
    fun on (event: CashDepositedEvent)  {
        val result = bankAccountViewRepository.findById(event.id).get()
        val balance  = result.balance?.plus(event.amount)
        bankAccountViewRepository.save(BankAccountView(id = event.id, balance = balance))
    }

    @EventHandler
    fun on (event: CashWithdrawnEvent){
        val result = bankAccountViewRepository.findById(event.id).get()
        val balance  = result.balance?.minus(event.amount)
        bankAccountViewRepository.save(BankAccountView(id = event.id, balance = balance))
    }

        @QueryHandler
        fun answer(query: FindAllBankAccountsQuery) = bankAccountViewRepository.findAll()

}
