package ch.keepcalm.account.api

import org.axonframework.commandhandling.callbacks.LoggingCallback
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/command")
class BankAccountCommandController(private val commandGateway: CommandGateway) {

    @PostMapping(path = ["/accounts"])
    fun createBankAccount(@RequestBody money: Money): String {
        val id = UUID.randomUUID().toString()
        commandGateway.send(CreateBankAccountCommand(id, money.amount), LoggingCallback.INSTANCE)
        return id
    }

    @PutMapping(path = ["/accounts/{id}/withdrawal"])
    fun withdrawCash(@RequestBody money: Money, @PathVariable id: String) {
        commandGateway.send<Any, Any>(WithdrawCashCommand(id, money.amount), LoggingCallback.INSTANCE)
    }

    @PutMapping(path = ["/accounts/{id}/deposit"])
    fun depositCash(@RequestBody money: Money, @PathVariable id: String) {
        commandGateway.send<Any, Any>(DepositCashCommand(id, money.amount), LoggingCallback.INSTANCE)
    }

}
data class Money(val amount: Int)