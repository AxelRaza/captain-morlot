package com.example.marinegame.model

import java.io.Serializable
import kotlin.random.Random


class Game private constructor() : Serializable {

    private object HOLDER {
        val INSTANCE = Game()
    }

    companion object {
        val instance: Game by lazy { HOLDER.INSTANCE }
        const val NB_MAX_PLAYERS = 18
        const val NB_MIN_PLAYERS = 2
        const val MATELOT = "Matelot"
        const val PIRATE = "Pirate"
        const val PIRATES = "Pirates"
        const val MOUSSAILLON = "Moussaillon"
        const val MOUSSAILLONS = "Moussaillons"
        const val GAME_DATA = "Game"
        const val NB_PIRATES = "NbPirates"
        const val NB_MOUSSAILLONS = "NbMoussaillons"
    }

    private var playersList : ArrayList<Player>
    private var nbPirates : Int
    private var nbMoussaillons : Int

    init {
        playersList = ArrayList()
        nbPirates = 1
        nbMoussaillons = 1
    }

    fun playersList() : ArrayList<Player> {
        return playersList
    }

    fun createPlayer(name : String, role : String) : Player {
        return Player(name, role)
    }

    fun addPlayer(player : Player) {
        playersList.add(player)
    }

    fun getNbPlayers() : Int {
        return playersList.size
    }

    fun removePlayerAt(position : Int) {
        playersList.removeAt(position)
    }

    fun removePlayer(player : Player) {
        playersList.remove(player)
    }

    fun exist(playerName : String) : Boolean {
        for(currentPlayer in playersList) {
            if(playerName.toUpperCase().equals(currentPlayer.name.toUpperCase()))
                return true
        }
        return false
    }

    fun rolesInit() {
        for(player in playersList) {
            if(player.role == MOUSSAILLON)
                player.role = ""
            if(player.role == PIRATE)
                player.role = ""
        }
    }

    fun rolesAssign() {
        playersList.shuffle()

        if(playersList.size > NB_MIN_PLAYERS) {
            if(playersList.size - nbPirates - nbMoussaillons < (nbPirates + nbMoussaillons) && playersList.size > NB_MIN_PLAYERS+1)
                if(nbPirates < nbMoussaillons) nbMoussaillons-- else nbPirates--
            for(i in 0 until nbPirates)
                playersList[i].role = PIRATE
            for(i in nbPirates until nbPirates + nbMoussaillons)
                playersList[i].role = MOUSSAILLON
        }

        for(player in playersList) {
            if(player.role.equals(MATELOT) || player.role.equals(""))
                player.role = MATELOT
        }
    }

    fun setNbPirates(pirates : Int) {
        nbPirates = pirates
    }

    fun setNbMoussaillons(moussaillons : Int) {
        nbMoussaillons = moussaillons
    }

    fun getNbPirates() : Int {
        var pirates = 0
        for(player in playersList) {
            if(player.role.equals(PIRATE))
                pirates++
        }
        return pirates
    }

    fun getNbMoussaillons() : Int {
        var moussaillons = 0
        for(player in playersList) {
            if(player.role.equals(MOUSSAILLON))
                moussaillons++
        }
        return moussaillons
    }

    fun moussaillonExist() : Boolean {
        for(player in playersList) {
            if(player.role.equals(MOUSSAILLON))
                return true
        }
        return false
    }

    fun pirateExist() : Boolean {
        for(player in playersList) {
            if(player.role.equals(PIRATE))
                return true
        }
        return false
    }

    fun getRandomPlayer() : Player {
        return playersList[Random.nextInt(0, playersList.size)]
    }

    fun getWinner() : Player {
        return playersList.first()
    }

}