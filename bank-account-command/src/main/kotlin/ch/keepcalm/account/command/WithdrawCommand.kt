package ch.keepcalm.account.command

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.*

data class WithdrawCommand (@TargetAggregateIdentifier val id: UUID, val amount: Int)