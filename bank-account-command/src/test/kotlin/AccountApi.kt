//package ch.keepcalm.account
//
// import command.CreateDepositCommand
// import org.axonframework.commandhandling.gateway.CommandGateway
//import org.springframework.web.bind.annotation.PostMapping
//import org.springframework.web.bind.annotation.RequestBody
//import org.springframework.web.bind.annotation.RequestMapping
//import org.springframework.web.bind.annotation.RestController
//import java.util.*
//import java.util.concurrent.CompletableFuture
//
////      _                             _      _          _
////     / \   ___ ___ ___  _   _ _ __ | |_   / \   _ __ (_)
////    / _ \ / __/ __/ _ \| | | | '_ \| __| / _ \ | '_ \| |
////   / ___ \ (__ (__ (_) | |_| | | | | |_ / ___ \| |_) | |
////  /_/   \_\___\___\___/ \__,_|_| |_|\__/_/   \_\ .__/|_|
////                                               |_|
//@RequestMapping(path = ["/api/accounts/command"])
//@RestController
//class AccountApi(private val commandGateway: CommandGateway) {
//
//    @PostMapping(path  = ["/createDeposit"])
//    fun createDeposit(@RequestBody deposit: Deposit): CompletableFuture<String> = commandGateway.send(CreateDepositCommand(UUID.randomUUID(), deposit.amount))
//}
//
//data class Deposit(val amount: Int)
//
