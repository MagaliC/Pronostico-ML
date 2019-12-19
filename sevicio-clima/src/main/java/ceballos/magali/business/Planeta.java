package ceballos.magali.business;

import static ceballos.magali.Constantes.GIRO;
import static ceballos.magali.Constantes.SEMI_GIRO;

import java.util.Comparator;
import java.util.List;

public class Planeta {
	
	public static enum Nombre { V, F, B }

	/**
	 * Nombre del planeta
	 */
	private final Nombre nombre;

	/**
	 * Posicion angular del planeta (en grados)
	 */
	private Integer posicion;

	/**
	 * Velocidad angular del planeta (en grados)
	 */
	private final Integer velocidad;

	/**
	 * Disntancia al sol en Km.
	 */
	private final Integer distanciaAlSol;

	public Planeta(Nombre nombre, Integer posicion, Integer velocidad, Integer distanciaAlSol) {
		this.nombre = nombre;
		this.posicion = posicion;
		this.velocidad = velocidad;
		this.distanciaAlSol = distanciaAlSol;
	}

	public Nombre getNombre() {
		return nombre;
	}

	public String getNombreStr() {
		return nombre.name();
	}

	public Integer getPosicion() {
		return posicion;
	}

	public Integer getVelocidad() {
		return velocidad;
	}

	public Integer getDistanciaAlSol() {
		return distanciaAlSol;
	}
	
	/**
	 * Si el planeta esta en la primera mitad del giro al rededor del sol
	 * devuleve su posicion. Si esta en la segunda mitad, devuelve la posicion opuesta.
	 * Esta posicion opuesta esta en linea con la posicion actual del planeta y el sol.
	 * 
	 * @return
	 */
	public Integer getPendiente() {
		return posicion<SEMI_GIRO ? posicion : (posicion-SEMI_GIRO);
	}

	/**
	 * Cambia la posicion del planeta segun la distancia que se mueve en un dia.
	 */
	public void avanzarUnDia() {

		int nuevaPos = this.posicion + this.velocidad;

		this.posicion = nuevaPos < 0 ? (nuevaPos + GIRO) : nuevaPos >= GIRO ? (nuevaPos - GIRO) : nuevaPos;
	}

	public static class PosicionComparator implements Comparator<Planeta> {

		@Override
		public int compare(Planeta o1, Planeta o2) {
			
			return o1.posicion.compareTo(o2.posicion);
		}
	}
	
	/**
	 * Modifica la lista recibida como parametro ordenandola segun el
	 * campo posicion. 
	 * 
	 * @param planetas lista de planetas.
	 */
	public static void sortByPosition(List<Planeta> planetas) {
	
		planetas.sort(new PosicionComparator());
	}
}
