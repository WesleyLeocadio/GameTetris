package br.ufrn.eaj.tads.gametetris.domain
/*
   *
   *       |D|
   *   |C| |A| |B|
   *
   *
   * */

class T(line:Int,column:Int) :Piece(line, column) {

    init {
        pointB = Point(line,column+1)
        pointC = Point(line,column-1)
        pointD = Point(line-1,column)

    }

    override var color: Int=4

    override fun moveTop(){
        pointA.moveTop()
        pointB.moveTop()
        pointC.moveTop()
        pointD.moveTop()
    }

    override fun moveRight() {
        pointA.moveRight()
        pointB.moveRight()
        pointC.moveRight()
        pointD.moveRight()
    }

    override fun moveDown() {
        pointA.moveDown()
        pointB.moveDown()
        pointC.moveDown()
        pointD.moveDown()

    }

    override fun moveLeft() {
        pointA.moveLeft()
        pointB.moveLeft()
        pointC.moveLeft()
        pointD.moveLeft()
    }
    override fun moveRotate() {}
}