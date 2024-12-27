package br.com.mozar7.cm;

import br.com.mozar7.cm.modelo.Tabuleiro;
import br.com.mozar7.cm.visao.TabuleiroConsole;

public class Aplicacao {

	public static void main(String[] args) {
		
		Tabuleiro tabuleiro = new Tabuleiro(5, 5, 5);
		new TabuleiroConsole(tabuleiro);
		
	}
}
