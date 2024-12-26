package br.com.mozar7.cm.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Tabuleiro {
	
	private int linhas;
	private int colunas;
	private int minas;
	
	private final List<Campo> CAMPOS = new ArrayList<>();
	
	public Tabuleiro(int linhas, int colunas, int minas) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas = minas;
		
		gerarCampos();
		associarVizinhos();
		sortearMinas();
	}
	
	public void abrir(int linha, int coluna) {
		CAMPOS.parallelStream()
			.filter(c -> c.getLINHA() == linha && c.getCOLUNA() == coluna)
			.findFirst()
			.ifPresent(c -> c.abrir());
			
	}
	
	public void alternarMarcacao(int linha, int coluna) {
		CAMPOS.parallelStream()
			.filter(c -> c.getLINHA() == linha && c.getCOLUNA() == coluna)
			.findFirst()
			.ifPresent(c -> c.alternarMarcacao());
			
	}

	private void gerarCampos() {
		for (int linha = 0; linha < linhas; linha++) {
			for (int coluna = 0; coluna < colunas; coluna++) {
				CAMPOS.add(new Campo(linha, coluna));
			}
		}
	}
	
	private void associarVizinhos() {
		for(Campo c1: CAMPOS) {
			for(Campo c2: CAMPOS) {
				c1.adicionarVizinho(c2);
			}
		}
	}
	
	private void sortearMinas() {
		long minasArmadas = 0;
		Predicate<Campo> minado = c -> c.isMinado();
		
		do {
			minasArmadas = CAMPOS.stream()
					.filter(minado)
					.count();
			int aleatorio = (int) (Math.random() * CAMPOS.size());
			CAMPOS.get(aleatorio).minar();
		} while(minasArmadas < minas);
	}
	
	public boolean objetivoAlcancado() {
		return CAMPOS.stream()
			.allMatch(c -> c.objetivoAlcancado());
	}
	
	public void reiniciar() {
		CAMPOS.stream()
			.forEach(c -> c.reiniciar());
		sortearMinas();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		int i = 0;
		for (int l = 0; l < linhas; l++) {
			for (int c = 0; c < colunas; c++) {
				sb.append(" ");
				sb.append(CAMPOS.get(i));
				sb.append(" ");
				i++;
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}

}
