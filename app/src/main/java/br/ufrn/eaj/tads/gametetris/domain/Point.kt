package br.ufrn.eaj.tads.gametetris.domain

class Point (var line:Int, var column:Int) {


    fun moveLeft(){
        column--
    }

    fun moveRight(){
        column++
    }

    fun moveDown(){
        line++
    }
    fun moveTop(){
        line--
    }

}