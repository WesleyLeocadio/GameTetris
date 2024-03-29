package br.ufrn.eaj.tads.gametetris.domain

class I(line:Int, column:Int) :Piece(line, column){
    /*
    *       |D|
    *       |A|
    *       |B|
    *       |C|
    *
    * */

    init {
        id=1
        pointB = Point(line+1,column)
        pointC = Point(line+2,column)
        pointD = Point(line-1,column)
    }

    override var cod: Int=1

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
        //fleck ==1 centro
        //fleck ==2 direita
        /*
           *       |D|
           *       |A|
           *       |B|  ----> |C| |B| |A| |D|
           *       |C|
           *
           * */
        if (fleck==1){
                pointB.line= pointA.line
                pointB.column=pointA.column -1

                pointC.line=pointA.line
                pointC.column=pointA.column - 2

                pointD.line=pointA.line
                pointD.column=pointA.column+1

                fleck = 2

            }else{
                pointB.line=pointA.line +1
                pointB.column= pointA.column

                pointC.line= pointA.line +2
                pointC.column= pointA.column

                pointD.line= pointA.line-1
                pointD.column= pointA.column

                fleck = 1

            }
    }
}