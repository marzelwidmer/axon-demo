package ch.keepcalm.account.event

import java.util.*

data class WithdrawnEvent (val id: UUID, val amount: Int)