package br.ufrn.eaj.tads.gametetris

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProviders
import br.ufrn.eaj.tads.gametetris.domain.*
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    //36
    //26
    val LINHA = 26
    val COLUNA = 20
    var running = true
    var speed: Long = 250
    var pontos = 0


    var proxPeca = 1
    var escolhido = 0
    var random = Random

    var pause = 0
    var pt: Piece = P(0, COLUNA / 2)


    var boardView = Array(LINHA) {
        arrayOfNulls<ImageView>(COLUNA)
    }

    val vm: BoardViewModel by lazy {
        ViewModelProviders.of(this)[BoardViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val settings = getSharedPreferences("Wesley", Context.MODE_PRIVATE)
        escolhido = settings.getInt("velocidade", 0)
        speed = escolhido.toLong()


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
                if ((vm.board[pt.pointA.line][pt.pointA.column + 1] == 0) && (vm.board[pt.pointB.line][pt.pointB.column + 1] == 0)
                    && (vm.board[pt.pointC.line][pt.pointC.column + 1] == 0) && (vm.board[pt.pointD.line][pt.pointD.column + 1] == 0)) {//bateu no lado direito
                    pt.moveRight()
                }
            } catch (e: ArrayIndexOutOfBoundsException) {}
        }
        btnEsquerda.setOnClickListener {
            try {
                if ((vm.board[pt.pointA.line][pt.pointA.column - 1] == 0) && (vm.board[pt.pointB.line][pt.pointB.column - 1] == 0)
                    && (vm.board[pt.pointC.line][pt.pointC.column - 1] == 0) && (vm.board[pt.pointD.line][pt.pointD.column - 1] == 0)
                ) {//bateu no lado direito
                    pt.moveLeft()
                }
            } catch (e: ArrayIndexOutOfBoundsException) { }
        }

        btnPause.setOnClickListener {
            if (pause == 0) {
                running = false
                var i = Intent(this, StartActivity::class.java)
                var b = Bundle()
                b.putBoolean("cod", true)
                i.putExtras(b)
                startActivityForResult(i, 99)
                pause = 1
            } else {
                running = true
                pause = 0
                gameRun()
            }
        }

        btnBaixo.setOnClickListener {
            try {
                if (verificarProximaLinha()) {//bateu no lado direito
                    pt.moveDown()
                }
            } catch (e: ArrayIndexOutOfBoundsException) {
                Log.i("ERRO", "Bateu esquerda")
            }
        }

        btnRotacionar.setOnClickListener {
            try {
                if (pt.cod == 0) {
                    if (pt.fleck == 2) {
                        if (verificarProximaLinha()) {

                            if (verificarColunas()) {
                                if (verificarProximaLinha()) {
                                    pt.moveRotate()
                                }
                            }
                        }
                    } else {
                        if (verificarProximaLinha()) {
                            pt.moveRotate()
                        }
                    }
                } else {
                    if (pt.fleck == 1) {
                        if ((vm.board[pt.pointA.line][pt.pointA.column - 2] == 0) && (vm.board[pt.pointB.line][pt.pointB.column - 2] == 0)
                            && (vm.board[pt.pointC.line][pt.pointC.column - 2] == 0) && (vm.board[pt.pointD.line][pt.pointD.column - 2] == 0)) {
                            if (verificarColunas()) {
                                pt.moveRotate()
                            }
                        }
                    } else {
                        if ((vm.board[pt.pointA.line + 2][pt.pointA.column] == 0) && (vm.board[pt.pointB.line + 2][pt.pointB.column] == 0)
                            && (vm.board[pt.pointC.line + 2][pt.pointC.column] == 0) && (vm.board[pt.pointD.line + 2][pt.pointD.column] == 0)) {//bateu no lado direito
                            if (verificarProximaLinha()) {
                                pt.moveRotate()
                            }
                        }
                    }
                }
            } catch (e: ArrayIndexOutOfBoundsException) {}
        }
    }

    fun gameRun() {
        Thread {
            while (running) {
                Thread.sleep(speed)
                runOnUiThread {
                    for (i in 0 until LINHA) {
                        for (j in 0 until COLUNA) {
                            if (vm.board[i][j] == 0) {
                                boardView[i][j]!!.setImageResource(R.drawable.black)
                            }
                        }
                    }

                    if (pt.pointC.line < LINHA - 1 && pt.pointD.line < LINHA - 1 && pt.pointA.line < LINHA - 1 && pt.pointB.line < LINHA - 1) {
                        if ((vm.board[pt.pointA.line + 1][pt.pointA.column] == 1) || (vm.board[pt.pointB.line + 1][pt.pointB.column] == 1) ||
                            (vm.board[pt.pointC.line + 1][pt.pointC.column] == 1) || (vm.board[pt.pointD.line + 1][pt.pointD.column] == 1)
                        ) {
                            setBoard()
                            setBoardView()
                            identificarLinha()
                            novaPieca()
                        } else {
                            pt.moveDown()
                        }

                    } else {
                        setBoard()
                        setBoardView()
                        identificarLinha()
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

    override fun onPause() {
        super.onPause()
        running = false
    }

    override fun onRestart() {
        super.onRestart()

        running = true
        gameRun()

    }

    fun verificarProximaLinha(): Boolean {
        return (vm.board[pt.pointA.line + 1][pt.pointA.column] == 0) && (vm.board[pt.pointB.line + 1][pt.pointB.column] == 0)
                && (vm.board[pt.pointC.line + 1][pt.pointC.column] == 0) && (vm.board[pt.pointD.line + 1][pt.pointD.column] == 0)

    }

    fun verificarColunas(): Boolean {
        return ((vm.board[pt.pointA.line][pt.pointA.column - 1] == 0) && (vm.board[pt.pointB.line][pt.pointB.column - 1] == 0)
                && (vm.board[pt.pointC.line][pt.pointC.column - 1] == 0) && (vm.board[pt.pointD.line][pt.pointD.column - 1] == 0) && (vm.board[pt.pointA.line][pt.pointA.column + 1] == 0)
                && (vm.board[pt.pointB.line][pt.pointB.column + 1] == 0) && (vm.board[pt.pointC.line][pt.pointC.column + 1] == 0) && (vm.board[pt.pointD.line][pt.pointD.column + 1] == 0)
                )

    }

    fun removeLinha(i: Int) {
        for (j in 0 until COLUNA) {
            vm.board[i][j] = 0
        }
        for (linha in i downTo 1) {
            for (coluna in COLUNA - 1 downTo 0) {
                vm.board[linha][coluna] = vm.board[linha - 1][coluna]
            }
        }
        pontos += COLUNA
        textPontos.text = "$pontos"

    }

    fun identificarLinha() {
        for (i in LINHA - 1 downTo 0) {
            var cont = 0
            for (j in COLUNA - 1 downTo 0) {
                if (vm.board[i][j] == 1) {
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
            if (vm.board[x][d] == 1) {
                cont++
            }
        }
        if (cont == COLUNA) { return true }
        return false
    }

    fun setBoard() {
        vm.board[pt.pointA.line][pt.pointA.column] = 1
        vm.board[pt.pointB.line][pt.pointB.column] = 1
        vm.board[pt.pointC.line][pt.pointC.column] = 1
        vm.board[pt.pointD.line][pt.pointD.column] = 1
    }

    fun setBoardView() {
        boardView[pt.pointA.line][pt.pointA.column]!!.setImageResource(getCor(pt.id))
        boardView[pt.pointB.line][pt.pointB.column]!!.setImageResource(getCor(pt.id))
        boardView[pt.pointC.line][pt.pointC.column]!!.setImageResource(getCor(pt.id))
        boardView[pt.pointD.line][pt.pointD.column]!!.setImageResource(getCor(pt.id))
    }

    fun gameOver() {
        for (i in 0 until COLUNA) {
            if (vm.board[3][i] == 1) {
                running = false
                var i = Intent(this, ResultadoActivity::class.java)
                var mensagem = textPontos.text.toString()
                var b = Bundle()
                b.putString("PONTUACAO", mensagem)
                i.putExtras(b)
                startActivity(i)
                finish()
                break
            }
        }
    }

    fun getCor(id: Int): Int {
        return when (id) {
            0 -> {
                R.drawable.black
            }
            1 -> {
                R.drawable.amarelo
            }
            2 -> {
                R.drawable.vermelho
            }
            3 -> {
                R.drawable.verde
            }
            4-> {
                R.drawable.vermelho
            }
            else -> {
                R.drawable.azul
            }
        }
    }

    fun novaPieca() {
        if (running) {gameOver()}
        pt=P(0, COLUNA / 2)

        var colum = random.nextInt(2,18)
        var novaPeca = proxPeca
        when(novaPeca) {
            0 -> {pt = L(1, colum)}
            1 -> {pt = I(1, colum)}
            2 -> {pt = O(1, colum)}
            3 -> {pt = T(1, colum)}
            4 -> {pt = P(1, colum)}
            else->{pt = S(1, colum)}

        }
        proxPeca = random.nextInt(6)
        when(proxPeca) {
            0 -> {idProximaPeca.setImageResource(R.drawable.peca_i)}
            1 -> { idProximaPeca.setImageResource(R.drawable.peca_l)}
            2 -> {idProximaPeca.setImageResource(R.drawable.peca_o)}
            3 -> { idProximaPeca.setImageResource(R.drawable.peca_t)}
            4 -> {idProximaPeca.setImageResource(R.drawable.peca_p)}
            else->{idProximaPeca.setImageResource(R.drawable.peca_s)}

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            99 -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        var i = Intent(this, MainActivity::class.java)
                        startActivity(i)
                    }
                }
            }
        }
    }

}