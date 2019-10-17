package ch.keepcalm.account.commandmodel

import ch.keepcalm.account.coreapi.CreateBankAccountCommand
import ch.keepcalm.account.coreapi.DepositCashCommand
import ch.keepcalm.account.coreapi.WithdrawCashCommand
import org.axonframework.commandhandling.callbacks.LoggingCallback
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/command")
class BankAccountCommandController(private val commandGateway: CommandGateway) {

//    @GetMapping("/bank")
//    fun createBankAccount() {
//        val bankAccountId = UUID.randomUUID().toString()
//        commandGateway.send(CreateBankAccountCommand(bankAccountId, 1000), LoggingCallback.INSTANCE)
//        commandGateway.send<Any, Any>(DepositCashCommand(bankAccountId, 42), LoggingCallback.INSTANCE)
//        commandGateway.send<Any, Any>(DepositCashCommand(bankAccountId, 42), LoggingCallback.INSTANCE)
//        commandGateway.send<Any, Any>(WithdrawCashCommand(bankAccountId, 84), LoggingCallback.INSTANCE)
//    }

    @GetMapping
    fun createBankAccount() {
        val bankAccountId = UUID.randomUUID().toString()
        commandGateway.send(CreateBankAccountCommand(bankAccountId, 1000), LoggingCallback.INSTANCE)
//        commandGateway.send<Any, Any>(DepositCashCommand(bankAccountId, 42), LoggingCallback.INSTANCE)
//        commandGateway.send<Any, Any>(DepositCashCommand(bankAccountId, 42), LoggingCallback.INSTANCE)
//        commandGateway.send<Any, Any>(WithdrawCashCommand(bankAccountId, 84), LoggingCallback.INSTANCE)
        //        commandGateway.send(new WithdrawCashCommand(bankAccountId, 1337), LoggingCallback.INSTANCE);
    }
}