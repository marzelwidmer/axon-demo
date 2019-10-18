package ch.keepcalm.account.api

import org.axonframework.modelling.command.TargetAggregateIdentifier
import javax.validation.constraints.NotNull


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
class NotEnoughMoneyException internal constructor(message: String) :  Exception(message)  //RuntimeException(message)
class FindAllBankAccountsQuery