package br.ufrn.eaj.tads.gametetris.domain

class Point (var line:Int, var column:Int) {

    fun moveDown(){
        line++
    }

    fun moveLeft(){
        column--
    }

    fun moveRight(){
        column++
    }
}