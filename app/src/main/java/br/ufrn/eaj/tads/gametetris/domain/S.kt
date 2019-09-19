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

    override var cod: Int=0

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
    override fun moveRotate() {
        /*
   *                      |C|
   *       |B| |C|  --->  |B| |A|
   *   |D| |A|                |D|
   *
   *
   * */

        if(fleck==1){
            pointB.line=pointA.line
            pointB.column=pointA.column-1
            pointC.line= pointA.line-1
            pointC.column= pointA.column-1
            pointD.line= pointA.line+1
            pointD.column= pointA.column

            fleck=2

        }else{
            pointB.line=pointA.line-1
            pointB.column=pointA.column
            pointC.line=pointA.line-1
            pointC.column=pointA.column+1
            pointD.line=pointA.line
            pointD.column=pointA.column-1

            fleck=1

        }
    }
}