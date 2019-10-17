package ch.keepcalm.account.api

import org.axonframework.commandhandling.callbacks.LoggingCallback
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*


@RestController
@RequestMapping("/command")
class BankAccountCommandController(private val commandGateway: CommandGateway) {

    @GetMapping
    fun createBankAccount() {
        val id = UUID.randomUUID().toString()
        commandGateway.send(CreateBankAccountCommand(id, 1000), LoggingCallback.INSTANCE)
//        commandGateway.send<Any, Any>(DepositCashCommand(id, 42), LoggingCallback.INSTANCE)
//        commandGateway.send<Any, Any>(DepositCashCommand(id, 42), LoggingCallback.INSTANCE)
//        commandGateway.send<Any, Any>(WithdrawCashCommand(id, 84), LoggingCallback.INSTANCE)
        //        commandGateway.send(new WithdrawCashCommand(idd, 1337), LoggingCallback.INSTANCE);
    }


}