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
import br.ufrn.eaj.tads.gametetris.domain.*
import java.util.*


class MainActivity : AppCompatActivity() {

    val LINHA = 36
    val COLUNA = 26
    var running = true
    var speed:Long = 200

    var pt:Piece = L(3,15)



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
            try {
                if((board[pt.pointA.line][pt.pointA.column+1] == 0) && (board[pt.pointB.line][pt.pointB.column+1] == 0)
                    && (board[pt.pointC.line][pt.pointC.column+1] == 0) && (board[pt.pointD.line][pt.pointD.column+1] == 0)) {//bateu no lado direito
                    pt.moveRight()
                }
            }catch (e:ArrayIndexOutOfBoundsException){
                Log.i("ERRO","Bateu direita")
            }
        }
        btnEsquerda.setOnClickListener {
            try {
                if((board[pt.pointA.line][pt.pointA.column-1] == 0) && (board[pt.pointB.line][pt.pointB.column-1] == 0)
                    && (board[pt.pointC.line][pt.pointC.column-1] == 0) && (board[pt.pointD.line][pt.pointD.column-1] == 0)) {//bateu no lado direito
                    pt.moveLeft()
                }
            }catch (e:ArrayIndexOutOfBoundsException){

                Log.i("ERRO","Bateu esquerda")
            }

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
                                boardView[i][j]!!.setImageResource(R.drawable.black)
                            }
                        }
                    }

                    if(pt.pointC.line< LINHA-1 && pt.pointD.line< LINHA-1 && pt.pointA.line< LINHA-1&&pt.pointB.line< LINHA-1){
                        if((board[pt.pointA.line+1][pt.pointA.column]== 1)||(board[pt.pointB.line+1][pt.pointB.column]== 1)||(board[pt.pointC.line+1][pt.pointC.column]== 1)||(board[pt.pointD.line+1][pt.pointD.column]== 1)) {
                            setBoard()
                            setBoardView()
                            novaPieca()
                        }else{
                            pt.moveDown()
                        }

                    }else{
                        setBoard()
                        setBoardView()
                        novaPieca()
                    }

                    try {
                      setBoardView()
                    }catch (e:ArrayIndexOutOfBoundsException ) {

                    }

                }
            }
        }.start()
    }


    fun createRadom_Peça(): Int {
        val r = Random()
        val aleatorio = r.nextInt(5)
        Log.i("random", "Número random: $aleatorio")
        return aleatorio
    }

    fun setBoard(){
        board[pt.pointA.line][pt.pointA.column]=1
        board[pt.pointB.line][pt.pointB.column]=1
        board[pt.pointC.line][pt.pointC.column]=1
        board[pt.pointD.line][pt.pointD.column]=1

    }

    fun setBoardView(){
        boardView[pt.pointA.line][pt.pointA.column]!!.setImageResource(R.drawable.green)
        boardView[pt.pointB.line][pt.pointB.column]!!.setImageResource(R.drawable.green)
        boardView[pt.pointC.line][pt.pointC.column]!!.setImageResource(R.drawable.green)
        boardView[pt.pointD.line][pt.pointD.column]!!.setImageResource(R.drawable.green)

    }

    fun novaPieca(){

        var novaPeca= createRadom_Peça()
        Log.i("Radom", "number: $novaPeca")

        if(novaPeca==0) {
            pt = L(3, 17)
        }else if(novaPeca==1){
            pt =I(3,15)

        }else if(novaPeca==2){
            pt =O(3,15)

        }else if(novaPeca==3){
            pt =T(3,15)

        }else{
            pt =S(3,15)
        }
    }
    fun verificar_linha(){


    }



}


