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
import br.ufrn.eaj.tads.gametetris.domain.L
import br.ufrn.eaj.tads.gametetris.domain.O


class MainActivity : AppCompatActivity() {

    val LINHA = 36
    val COLUNA = 26
    var running = true
    var speed:Long = 300

    var pt = L(1,16)




    //val board = Array(LINHA, { IntArray(COLUNA) })

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
                    //move peça atual
                    pt.moveDown()
                    //print peça

                    try {
                            when(pt.color){
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

                            }

                    }catch (e:ArrayIndexOutOfBoundsException ) {
                        //se a peça passou das bordas eu vou parar o jogo
                        running = false
                    }

                }
            }
        }.start()
    }
}
