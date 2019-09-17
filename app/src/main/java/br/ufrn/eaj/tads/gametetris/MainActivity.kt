package br.ufrn.eaj.tads.gametetris

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*
import android.view.LayoutInflater
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import br.ufrn.eaj.tads.gametetris.domain.I
import br.ufrn.eaj.tads.gametetris.domain.L
import br.ufrn.eaj.tads.gametetris.domain.O
import br.ufrn.eaj.tads.gametetris.domain.S
import br.ufrn.eaj.tads.gametetris.domain.T


class MainActivity : AppCompatActivity() {

    val LINHA = 36
    val COLUNA = 26
    var running = true
    var speed:Long = 300

    var pt = I(3,15)


    var board = Array(LINHA) {
        Array(COLUNA){0}
    }

    var boardView = Array(LINHA){
        arrayOfNulls<ImageView>(COLUNA)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridboard.rowCount = LINHA
        gridboard.columnCount = COLUNA

        val inflater = LayoutInflater.from(this)

        for (i in 0 until LINHA) {
            for (j in 0 until COLUNA) {
                boardView[i][j] = inflater.inflate(R.layout.inflate_image_view, gridboard, false) as ImageView
                gridboard.addView( boardView[i][j])
            }
        }

        gameRun()

        btnDireita.setOnClickListener {
            pt.moveRight()
        }
        btnEsquerda.setOnClickListener {
            pt.moveLeft()
        }
        btnRotacionar.setOnClickListener {
            pt.moveRotate()
        }
    }

    fun gameRun(){
        Thread{
            while(running){
                Thread.sleep(speed)
                runOnUiThread{
                    //limpa tela
                    for (i in 0 until LINHA) {
                        for (j in 0 until COLUNA) {
                            if (board[i][j] == 0){
                                boardView[i][j]!!.setImageResource(R.drawable.white)
                            }
                        }
                    }


                        pt.moveDown()



                    try {

                        boardView[pt.pointA.line][pt.pointA.column]!!.setImageResource(R.drawable.green)
                        boardView[pt.pointB.line][pt.pointB.column]!!.setImageResource(R.drawable.green)
                        boardView[pt.pointC.line][pt.pointC.column]!!.setImageResource(R.drawable.green)
                        boardView[pt.pointD.line][pt.pointD.column]!!.setImageResource(R.drawable.green)
                     /*       when(pt.color){
                                1->{
                                    boardView[pt.pointA.line][pt.pointA.column]!!.setImageResource(R.drawable.green)
                                    boardView[pt.pointB.line][pt.pointB.column]!!.setImageResource(R.drawable.green)
                                    boardView[pt.pointC.line][pt.pointC.column]!!.setImageResource(R.drawable.green)
                                    boardView[pt.pointD.line][pt.pointD.column]!!.setImageResource(R.drawable.green)
                                }
                                2->{
                                    boardView[pt.pointA.line][pt.pointA.column]!!.setImageResource(R.drawable.black)
                                    boardView[pt.pointB.line][pt.pointB.column]!!.setImageResource(R.drawable.black)
                                    boardView[pt.pointC.line][pt.pointC.column]!!.setImageResource(R.drawable.black)
                                    boardView[pt.pointD.line][pt.pointD.column]!!.setImageResource(R.drawable.black)
                                }
                                3->{
                                    boardView[pt.pointA.line][pt.pointA.column]!!.setImageResource(R.drawable.gray)
                                    boardView[pt.pointB.line][pt.pointB.column]!!.setImageResource(R.drawable.gray)
                                    boardView[pt.pointC.line][pt.pointC.column]!!.setImageResource(R.drawable.gray)
                                    boardView[pt.pointD.line][pt.pointD.column]!!.setImageResource(R.drawable.gray)
                                }

                            }
*/
                    }catch (e:ArrayIndexOutOfBoundsException ) {

                    }

                }
            }
        }.start()
    }



}


