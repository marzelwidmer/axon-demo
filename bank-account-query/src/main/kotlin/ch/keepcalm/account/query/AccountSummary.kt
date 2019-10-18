package ch.keepcalm.account.query

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class AccountSummary(@Id val id: String, var remainingValue: Int, val initialValue: Int)