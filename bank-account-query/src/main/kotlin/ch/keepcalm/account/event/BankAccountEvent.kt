package ch.keepcalm.account.event


//                        _       
//    _____   _____ _ __ | |_ ___ 
//   / _ \ \ / / _ \ '_ \| __/ __|
//  |  __/\ V /  __/ | | | |_\__ \
//   \___| \_/ \___|_| |_|\__|___/
//
data class DepositEvent (val id: String, val amount: Int)
data class WithdrawEvent (val id: String, val amount: Int)

//                                             
//    ___ ___  _ __ ___  _ __ ___   ___  _ __  
//   / __/ _ \| '_ ` _ \| '_ ` _ \ / _ \| '_ \ 
//  | (__ (_) | | | | | | | | | | | (_) | | | |
//   \___\___/|_| |_| |_|_| |_| |_|\___/|_| |_|
//
class FindAllWalletsQuery