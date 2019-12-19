package ceballos.magali.business;

import org.springframework.stereotype.Service;

@Service
public class MatematicaHelper {

	/**
	 * Dado un tringulo del cual se conoce 1 angulo y el largo
	 * de los 2 lados adyacentes, devuleve le largo del lado opuesto al angulo.
	 * 
	 * @param angulo angulo en grados
	 * @param lado1	largo de uno de los lados
	 * @param lado2 largo del otro lado.
	 * 
	 * @return largo del lado opuesto al angulo.
	 */
	public Double getladoOpuesto(double angulo, double lado1, double lado2) {

		return Math.sqrt(Math.pow(lado1, 2) + Math.pow(lado2, 2) - 2 * lado1 * lado2 * Math.cos(Math.toRadians(angulo)));
	}
}
