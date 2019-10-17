package ch.keepcalm.account.api

import org.axonframework.commandhandling.callbacks.LoggingCallback
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/command")
class BankAccountCommandController(private val commandGateway: CommandGateway) {

    @GetMapping
    fun createBankAccount() {
          val id = UUID.randomUUID().toString()
        commandGateway.send(CreateBankAccountCommand(id, 1000), LoggingCallback.INSTANCE)

//        Thread.sleep(500)
//        commandGateway.send<Any, Any>(DepositCashCommand(id, 42), LoggingCallback.INSTANCE)
//        Thread.sleep(500)
//        commandGateway.send<Any, Any>(DepositCashCommand(id, 42), LoggingCallback.INSTANCE)
        Thread.sleep(500)
        commandGateway.send<Any, Any>(WithdrawCashCommand(id, 84), LoggingCallback.INSTANCE)
//                commandGateway.send(new WithdrawCashCommand(idd, 1337), LoggingCallback.INSTANCE);
    }


    @GetMapping("/{id}")
    fun createBankAccount(@PathVariable id: String) {
      //  val id = UUID.randomUUID().toString()
        commandGateway.send(CreateBankAccountCommand(id, 1000), LoggingCallback.INSTANCE)
//        commandGateway.send<Any, Any>(DepositCashCommand(id, 42), LoggingCallback.INSTANCE)
//        commandGateway.send<Any, Any>(DepositCashCommand(id, 42), LoggingCallback.INSTANCE)
//        commandGateway.send<Any, Any>(WithdrawCashCommand(id, 84), LoggingCallback.INSTANCE)
        //        commandGateway.send(new WithdrawCashCommand(idd, 1337), LoggingCallback.INSTANCE);
    }

    @PutMapping(path = ["/{id}"])
    fun withdrawMoney(@RequestBody money: Money, @PathVariable id: String) {
        commandGateway.send<Any, Any>(WithdrawCashCommand(id, money.amount), LoggingCallback.INSTANCE)
    }

}
data class Money(val amount: Int)