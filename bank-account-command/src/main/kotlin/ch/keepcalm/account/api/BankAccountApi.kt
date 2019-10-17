package ch.keepcalm.account.api

import org.axonframework.modelling.command.TargetAggregateIdentifier
import javax.validation.constraints.NotNull

//                                                 _     
//    ___ ___  _ __ ___  _ __ ___   __ _ _ __   __| |___ 
//   / __/ _ \| '_ ` _ \| '_ ` _ \ / _` | '_ \ / _` / __|
//  | (__ (_) | | | | | | | | | | | (_| | | | | (_| \__ \
//   \___\___/|_| |_| |_|_| |_| |_|\__,_|_| |_|\__,_|___/
//
//open class BaseCommand<T>(@field:TargetAggregateIdentifier @NotNull(message = "Id cannot be null") val id: String)
data class CreateBankAccountCommand(@TargetAggregateIdentifier val id: String, val balance: Int) // : BaseCommand<String>()
data class DepositCashCommand(@TargetAggregateIdentifier val id: String, val amount: Int) //: BaseCommand<String>(id)
data class WithdrawCashCommand(@TargetAggregateIdentifier val id: String, val amount: Int)//: BaseCommand<String>(id)


//                        _       
//    _____   _____ _ __ | |_ ___ 
//   / _ \ \ / / _ \ '_ \| __/ __|
//  |  __/\ V /  __/ | | | |_\__ \
//   \___| \_/ \___|_| |_|\__|___/
//
data class BankAccountCreatedEvent(@NotNull(message = "Id cannot be null")  val id: String, val balance: Int)
data class CashDepositedEvent(@NotNull(message = "Id cannot be null")  val id: String, val amount: Int)
data class CashWithdrawnEvent(@NotNull(message = "Id cannot be null")  val id: String, val amount: Int)


//                                             
//    ___ ___  _ __ ___  _ __ ___   ___  _ __  
//   / __/ _ \| '_ ` _ \| '_ ` _ \ / _ \| '_ \ 
//  | (__ (_) | | | | | | | | | | | (_) | | | |
//   \___\___/|_| |_| |_|_| |_| |_|\___/|_| |_|
//
class InsufficientBalanceException internal constructor(message: String) : RuntimeException(message)
class NotEnoughFundsException : Exception()
class FindAllWalletsQuery