package ceballos.magali.data.domain;

import java.util.Date;

public class Pronostico {

	private Integer id;

	private int anguloFerengi;

	private int anguloVulcano;

	private int anguloBetasoide;

	private int dia;

	private String clima;

	private double perimetro;

	private int periodo;

	private Date tsCreate;

	private Date tsModif;

	public Pronostico() {
	}

	// Tener este constructor y no el constructor por defecto (sin parametros)
	// hacia que me fallaran algunos de los metodos del mapper
	// Cuando agre el constructor por defecto, se soluciono el problema.

	public Pronostico(int anguloF, int anguloV, int anguloB, int i, String clima, double perimetro, int periodo) {
		this.anguloFerengi = anguloF;
		this.anguloVulcano = anguloV;
		this.anguloBetasoide = anguloB;
		this.dia = i;
		this.clima = clima;
		this.perimetro = perimetro;
		this.periodo = periodo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getAnguloFerengi() {
		return anguloFerengi;
	}

	public void setAnguloFerengi(int anguloFerengi) {
		this.anguloFerengi = anguloFerengi;
	}

	public int getAnguloVulcano() {
		return anguloVulcano;
	}

	public void setAnguloVulcano(int anguloVulcano) {
		this.anguloVulcano = anguloVulcano;
	}

	public int getAnguloBetasoide() {
		return anguloBetasoide;
	}

	public void setAnguloBetasoide(int anguloBetasoide) {
		this.anguloBetasoide = anguloBetasoide;
	}

	public int getDia() {
		return dia;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}

	public String getClima() {
		return clima;
	}

	public void setClima(String clima) {
		this.clima = clima;
	}

	public double getPerimetro() {
		return perimetro;
	}

	public void setPerimetro(double perimetro) {
		this.perimetro = perimetro;
	}

	public int getPeriodo() {
		return periodo;
	}

	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}

	public Date getTsCreate() {
		return tsCreate;
	}

	public void setTsCreate(Date tsCreate) {
		this.tsCreate = tsCreate;
	}

	public Date getTsModif() {
		return tsModif;
	}

	public void setTsModif(Date tsModif) {
		this.tsModif = tsModif;
	}

}
