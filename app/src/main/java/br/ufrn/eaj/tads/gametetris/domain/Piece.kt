package br.ufrn.eaj.tads.gametetris.domain

abstract class Piece(var line:Int, var column:Int){

    var pointA = Point(line,column)
    lateinit var pointB: Point
    lateinit var pointC: Point
    lateinit var pointD: Point

    abstract fun moveDown()
    abstract fun moveLeft()
    abstract fun moveRight()
    abstract fun moveRotate()
}