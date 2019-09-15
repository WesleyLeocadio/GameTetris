package br.ufrn.eaj.tads.gametetris.domain
/*
   *
   *       |B| |C|
   *   |D| |A|
   *
   *
   * */

class S(line:Int,column:Int) :Piece(line, column) {

    init {
        pointB = Point(line-1,column)
        pointC = Point(line-1,column+1)
        pointD = Point(line,column-1)

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