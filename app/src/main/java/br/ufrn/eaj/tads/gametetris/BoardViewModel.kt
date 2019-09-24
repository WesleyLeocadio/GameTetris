package br.ufrn.eaj.tads.gametetris

import androidx.lifecycle.ViewModel

class BoardViewModel : ViewModel() {
    val LINHA = 26
    val COLUNA = 20

    var board = Array(LINHA) {
        Array(COLUNA) { 0 }
    }
}