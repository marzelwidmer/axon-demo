package ch.keepcalm.account.coreapi

import org.axonframework.modelling.command.TargetAggregateIdentifier


data class CreateBankAccountCommand(@TargetAggregateIdentifier val bankAccountId: String, val balance: Int)
data class DepositCashCommand(@TargetAggregateIdentifier val bankAccountId: String, val amount: Int)
data class WithdrawCashCommand(@TargetAggregateIdentifier val bankAccountId: String, val amount: Int)

data class BankAccountCreatedEvent(val bankAccountId: String, val balance: Int)
data class CashDepositedEvent(val bankAccountId: String, val amount: Int)
data class CashWithdrawnEvent(val bankAccountId: String, val amount: Int)

//data class CreateBankAccountCommand(val bankAccountId: String, val balance: Int)

//data class CreateWalletCommand(
//        val bankAccountId: String,
//        val balance: Int
//)

//data class DepositCashCommand(
//        @TargetAggregateIdentifier val bankAccountId: String,
//        val amount: Int
//)
//
//data class WithdrawCashCommand(
//        @TargetAggregateIdentifier val bankAccountId: String,
//        val amount: Int
//)
//
////data class WalletCreatedEvent(
////        val bankAccountId: String,
////        val balance: Int
////)
//
//data class CashWithdrawnEvent(
//        val bankAccountId: String,
//        val amount: Int
//)
//
//data class CashDepositedEvent(
//        val bankAccountId: String,
//        val amount: Int
//)

class InsufficientBalanceException internal constructor(message: String) : RuntimeException(message)
class NotEnoughFundsException : Exception()
class FindAllWalletsQuery