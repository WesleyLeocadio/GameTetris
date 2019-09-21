package br.ufrn.eaj.tads.gametetris.domain

abstract class Piece(var line:Int, var column:Int){

    var pointA = Point(line,column)
    lateinit var pointB: Point
    lateinit var pointC: Point
    lateinit var pointD: Point


    abstract fun moveTop()
    abstract fun moveRight()
    abstract fun moveDown()
    abstract fun moveLeft()
    abstract  fun moveRotate()

    abstract var cod:Int
     var fleck:Int=1
     var id:Int=1



}