package br.ufrn.eaj.tads.gametetris

import android.content.Context
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
import android.widget.Toast
import br.ufrn.eaj.tads.gametetris.domain.*
import java.util.*


class MainActivity : AppCompatActivity() {
    //36
    //26
    val LINHA = 26
    val COLUNA = 12
    var running = true
    var speed: Long = 250

    var groupRadio=0
    var escolhido=0

    var pt: Piece = I(0, 6)


    var board = Array(LINHA) {
        Array(COLUNA) { 0 }
    }

    var boardView = Array(LINHA) {
        arrayOfNulls<ImageView>(COLUNA)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val settings = getSharedPreferences("Wesley", Context.MODE_PRIVATE)
        groupRadio = settings.getInt("selecionado", 0)
        escolhido = settings.getInt("velocidade", 0)
             speed=escolhido.toLong()
        Log.i("erro", " NOVO -> grupo 0${groupRadio} escolhido ${escolhido} ")


        gridboard.rowCount = LINHA
        gridboard.columnCount = COLUNA

        val inflater = LayoutInflater.from(this)

        for (i in 0 until LINHA) {
            for (j in 0 until COLUNA) {
                boardView[i][j] =
                    inflater.inflate(R.layout.inflate_image_view, gridboard, false) as ImageView
                gridboard.addView(boardView[i][j])
            }
        }


        gameRun()

        btnDireita.setOnClickListener {
            try {
                if ((board[pt.pointA.line][pt.pointA.column + 1] == 0) && (board[pt.pointB.line][pt.pointB.column + 1] == 0)
                    && (board[pt.pointC.line][pt.pointC.column + 1] == 0) && (board[pt.pointD.line][pt.pointD.column + 1] == 0)
                ) {//bateu no lado direito
                    pt.moveRight()
                }
            } catch (e: ArrayIndexOutOfBoundsException) {
                Log.i("ERRO", "Bateu direita")
            }
        }
        btnEsquerda.setOnClickListener {
            try {
                if ((board[pt.pointA.line][pt.pointA.column - 1] == 0) && (board[pt.pointB.line][pt.pointB.column - 1] == 0)
                    && (board[pt.pointC.line][pt.pointC.column - 1] == 0) && (board[pt.pointD.line][pt.pointD.column - 1] == 0)
                ) {//bateu no lado direito
                    pt.moveLeft()
                }
            } catch (e: ArrayIndexOutOfBoundsException) {

                Log.i("ERRO", "Bateu esquerda")
            }

        }
        btnRotacionar.setOnClickListener {
            try {
                if (pt.cod == 0) {
                    if ((board[pt.pointA.line + 1][pt.pointA.column] == 0) && (board[pt.pointB.line + 1][pt.pointB.column] == 0)
                        && (board[pt.pointC.line + 1][pt.pointC.column] == 0) && (board[pt.pointD.line + 1][pt.pointD.column] == 0)
                    ) {
                        pt.moveRotate()
                    }
                } else {
                    if (pt.fleck == 1) {
                        if ((board[pt.pointA.line][pt.pointA.column - 2] == 0) && (board[pt.pointB.line][pt.pointB.column - 2] == 0)
                            && (board[pt.pointC.line][pt.pointC.column - 2] == 0) && (board[pt.pointD.line][pt.pointD.column - 2] == 0) && (board[pt.pointA.line][pt.pointA.column + 2] == 0)
                            && (board[pt.pointB.line][pt.pointB.column + 2] == 0) &&
                            (board[pt.pointC.line][pt.pointC.column + 2] == 0) && (board[pt.pointD.line][pt.pointD.column + 2] == 0)
                        ) {

                            if ((board[pt.pointA.line][pt.pointA.column - 1] == 0) && (board[pt.pointB.line][pt.pointB.column - 1] == 0)
                                && (board[pt.pointC.line][pt.pointC.column - 1] == 0) && (board[pt.pointD.line][pt.pointD.column - 1] == 0) && (board[pt.pointA.line][pt.pointA.column + 1] == 0)
                                && (board[pt.pointB.line][pt.pointB.column + 1] == 0) &&
                                (board[pt.pointC.line][pt.pointC.column + 1] == 0) && (board[pt.pointD.line][pt.pointD.column + 1] == 0)
                            ) {

                                //linha
                                if ((board[pt.pointA.line + 2][pt.pointA.column] == 0) && (board[pt.pointB.line + 2][pt.pointB.column] == 0)
                                    && (board[pt.pointC.line + 2][pt.pointC.column] == 0) && (board[pt.pointD.line + 2][pt.pointD.column] == 0)
                                ) {//bateu no lado direito

                                    if ((board[pt.pointA.line + 1][pt.pointA.column] == 0) && (board[pt.pointB.line + 1][pt.pointB.column] == 0)
                                        && (board[pt.pointC.line + 1][pt.pointC.column] == 0) && (board[pt.pointD.line + 1][pt.pointD.column] == 0)
                                    ) {
                                        pt.moveRotate()
                                    }
                                }
                            }
                        }
                    } else {
                        pt.moveRotate()


                    }
                }
            } catch (e: ArrayIndexOutOfBoundsException) {

                Log.i("ERRO", "Bateu esquerda")
            }

        }
    }

    fun gameRun() {
        Thread {
            while (running) {
                Thread.sleep(speed)
                runOnUiThread {
                    //limpa tela
                    for (i in 0 until LINHA) {
                        for (j in 0 until COLUNA) {
                            if (board[i][j] == 0) {
                                boardView[i][j]!!.setImageResource(R.drawable.black)
                            }
                        }
                    }

                    if (pt.pointC.line < LINHA - 1 && pt.pointD.line < LINHA - 1 &&
                        pt.pointA.line < LINHA - 1 && pt.pointB.line < LINHA - 1
                    ) {

                        if ((board[pt.pointA.line + 1][pt.pointA.column] == 1) || (board[pt.pointB.line + 1][pt.pointB.column] == 1) ||
                            (board[pt.pointC.line + 1][pt.pointC.column] == 1) || (board[pt.pointD.line + 1][pt.pointD.column] == 1)
                        ) {
                            setBoard()
                            setBoardView()
                            identificarLinha()
                            gameOver()
                            novaPieca()
                        } else {
                            pt.moveDown()
                        }

                    } else {
                        setBoard()
                        setBoardView()
                        identificarLinha()
                        gameOver()
                        novaPieca()
                    }

                    try {
                        setBoardView()
                    } catch (e: ArrayIndexOutOfBoundsException) {

                    }


                }
            }
        }.start()
    }

    fun removeLinha(i: Int) {
        for (j in 0 until COLUNA) {
            board[i][j] = 0
        }
        for (linha in i downTo 1) {
            for (coluna in COLUNA - 1 downTo 0) {
                board[linha][coluna] = board[linha - 1][coluna]
                Log.i("erro", " Linnha ${linha} coluna ${coluna} ==${board[linha][coluna]}")


            }
        }

        for (i in 0 until LINHA) {
            for (j in 0 until COLUNA) {
                when (board[i][j]) {
                    0 -> {
                        boardView[i][j]!!.setImageResource(R.drawable.black)
                    }
                    1 -> {
                        boardView[i][j]!!.setImageResource(R.drawable.green)
                    }
                }
            }
        }
    }

    fun identificarLinha() {

        for (i in LINHA - 1 downTo 0) {
            var cont = 0
            for (j in COLUNA - 1 downTo 0) {
                if (board[i][j] == 1) {
                    cont++
                }
            }
            if (cont == COLUNA) {
                removeLinha(i)

            }

            while (verificandoUltimaLinha(i)) {
                removeLinha(i)
            }
        }
    }

    fun verificandoUltimaLinha(x: Int): Boolean {

        var cont = 0
        for (d in COLUNA - 1 downTo 0) {
            if (board[x][d] == 1) {
                cont++
            }

        }
        if (cont == COLUNA) {
            return true


        }
        return false

    }

    fun createRadom_Peça(): Int {
        val r = Random()
        val aleatorio = r.nextInt(5)
        Log.i("random", "Número random: $aleatorio")
        return aleatorio
    }

    fun setBoard() {
        board[pt.pointA.line][pt.pointA.column] = 1
        board[pt.pointB.line][pt.pointB.column] = 1
        board[pt.pointC.line][pt.pointC.column] = 1
        board[pt.pointD.line][pt.pointD.column] = 1

    }

    fun setBoardView() {
        boardView[pt.pointA.line][pt.pointA.column]!!.setImageResource(R.drawable.green)
        boardView[pt.pointB.line][pt.pointB.column]!!.setImageResource(R.drawable.green)
        boardView[pt.pointC.line][pt.pointC.column]!!.setImageResource(R.drawable.green)
        boardView[pt.pointD.line][pt.pointD.column]!!.setImageResource(R.drawable.green)

    }

    fun gameOver() {
        var cont = 0
        for (i in 0 until COLUNA) {

            if (board[0][i] == 1) {
                Toast.makeText(this, "GAME OVER", Toast.LENGTH_LONG).show()
                break
            }


        }

    }

    fun novaPieca() {
        pt = I(0, 6)

//        var novaPeca= createRadom_Peça()
//        Log.i("Radom", "number: $novaPeca")
//
//        if(novaPeca==0) {
//            pt = L(3, 17)
//        }else if(novaPeca==1){
//            pt =I(3,15)
//
//        }else if(novaPeca==2){
//            pt =O(3,15)
//
//        }else if(novaPeca==3){
//            pt =T(3,15)
//
//        }else{
//            pt =S(3,15)
//        }
    }


}