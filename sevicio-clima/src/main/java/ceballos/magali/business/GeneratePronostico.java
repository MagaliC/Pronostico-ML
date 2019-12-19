package ceballos.magali.business;

import static ceballos.magali.Constantes.GIRO;
import static ceballos.magali.Constantes.SEMI_GIRO;

import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import ceballos.magali.data.domain.Clima;
import ceballos.magali.data.domain.Pronostico;
import ceballos.magali.data.mapper.PronosticoMapper;

@Service
public class GeneratePronostico {

	@Value("${pronostico.anguloInicialF}")
	private Integer anguloInicialF;

	@Value("${pronostico.anguloInicialV}")
	private Integer anguloInicialV;

	@Value("${pronostico.anguloInicialB}")
	private Integer anguloInicialB;

	@Value("${pronostico.velocidadAngularF}")
	private Integer velocidadAngularF;

	@Value("${pronostico.velocidadAngularV}")
	private Integer velocidadAngularV;

	@Value("${pronostico.velocidadAngularB}")
	private Integer velocidadAngularB;

	@Value("${pronostico.distanciaAlSolF}")
	private Integer distanciaAlSolF;

	@Value("${pronostico.distanciaAlSolV}")
	private Integer distanciaAlSolV;

	@Value("${pronostico.distanciaAlSolB}")
	private Integer distanciaAlSolB;

	@Value("${pronostico.cantidadDeDias}")
	private Integer cantidadDeDias;

	@Autowired
	SqlSessionFactory sqlSessionFactory;

	@Autowired
	MatematicaHelper mh;

	/**
	 * Craga de datos la tabla pronostico. <b>metodo asincrono</b>
	 */
	@Async
	public void run() {

		List<Planeta> planetas = preparaPlanetas();

		Clima previo = Clima.NORMAL;
		int contadorPeriodosLluvia = 0, contadorPeriodosSequia = 0, contadorPeriodosCondOp = 0;
		for (int i = 0; i < this.cantidadDeDias; i++) {

			Pronostico pronostico = new Pronostico();
			pronostico.setDia(i);

			// Cargo las posiciones actuales de los planetas
			planetas.forEach(p -> {
				switch (p.getNombre()) {
				case F:
					pronostico.setAnguloFerengi(p.getPosicion());
					break;
				case V:
					pronostico.setAnguloVulcano(p.getPosicion());
					break;
				case B:
					pronostico.setAnguloBetasoide(p.getPosicion());
					break;
				}
			});

			calcularCondiciones(planetas, pronostico);

			if (Clima.LLUVIA.name().equals(pronostico.getClima())) {
				if (previo != Clima.LLUVIA) {
					// Cuando paso de otro clima a clima de lluvia,
					// es el comienzo de un nuevo periodo
					contadorPeriodosLluvia++;
				}
				// Si es dia de lluvia cargo el nro de periodo de lluvia
				pronostico.setPeriodo(contadorPeriodosLluvia);
			}

			if (Clima.SEQUIA.name().equals(pronostico.getClima())) {
				if (previo != Clima.SEQUIA) {
					// Cuando paso de otro clima a clima de sequia,
					// es el comienzo de un nuevo periodo
					contadorPeriodosSequia++;
				}
				// Si es dia de sequia cargo el nro de periodo de sequia
				pronostico.setPeriodo(contadorPeriodosSequia);
			}
			
			if (Clima.CONDICIONES_OPTIMAS.name().equals(pronostico.getClima())) {
				if (previo != Clima.CONDICIONES_OPTIMAS) {
					// Cuando paso de otro clima a clima de condiciones optimas,
					// es el comienzo de un nuevo periodo
					contadorPeriodosCondOp++;
				}
				// Si es dia de condiciones optimas cargo el nro de periodo de condiciones optimas
				pronostico.setPeriodo(contadorPeriodosCondOp);
			}
			
			persistir(pronostico);

			planetas.forEach(p -> p.avanzarUnDia());
			previo = Clima.valueOf(pronostico.getClima());
		}

	}

	private List<Planeta> preparaPlanetas() {
		List<Planeta> res = Arrays.asList(
				new Planeta(Planeta.Nombre.F, this.anguloInicialF, this.velocidadAngularF, this.distanciaAlSolF),
				new Planeta(Planeta.Nombre.V, this.anguloInicialV, this.velocidadAngularV, this.distanciaAlSolV),
				new Planeta(Planeta.Nombre.B, this.anguloInicialB, this.velocidadAngularB, this.distanciaAlSolB));
		return res;
	}

	/**
	 * 
	 * @param planetas
	 * @param pronostico
	 */
	private void calcularCondiciones(List<Planeta> planetas, Pronostico pronostico) {

		Planeta.sortByPosition(planetas);
		Planeta p1 = planetas.get(0);
		Planeta p2 = planetas.get(1);
		Planeta p3 = planetas.get(2);

		// Para c/planeta obtengo su "pendiente" ( ver doc: Planeta.getPendiente() )
		int pp1 = p1.getPendiente();
		int pp2 = p2.getPendiente();
		int pp3 = p3.getPendiente();

		// CONDICIONES PARA SEQUIA
		if (pp1 == pp2 && pp1 == pp3) {
			pronostico.setPerimetro(0);
			pronostico.setClima(Clima.SEQUIA.name());
			return;
		}

		// Obtengo la distancia angular enter los planetas
		int angulo12 = p2.getPosicion() - p1.getPosicion();
		int angulo23 = p3.getPosicion() - p2.getPosicion();
		int angulo31 = (GIRO - p3.getPosicion()) + p1.getPosicion();

		// Si el sol esta fuera de triangulo, hay chances de "condiciones optimas"
		// Si el sol esta dentro del triangulo, ES periodo de lluvia y hay chances de
		// "pico de lluvias"
		boolean solFueraDelTriangulo = false;
		if (angulo12 > SEMI_GIRO || angulo23 > SEMI_GIRO || angulo31 > SEMI_GIRO) {
			solFueraDelTriangulo = true;
		}

		// En ambos casos, tanto para chances de "condiciones optimas" como "pico de
		// lluvias"
		// necesitamos calcular los lados del triangulo.
		Double lado12 = mh.getladoOpuesto(angulo12, p1.getDistanciaAlSol(), p2.getDistanciaAlSol());
		Double lado23 = mh.getladoOpuesto(angulo23, p2.getDistanciaAlSol(), p3.getDistanciaAlSol());
		Double lado31 = mh.getladoOpuesto(angulo31, p3.getDistanciaAlSol(), p1.getDistanciaAlSol());

		// Si realmente es "pico de lluvias", se calcula fuera de este metodo
		// para que pueda hacer ese calculo, le pasamos el perimetro
		if (!solFueraDelTriangulo) {
			pronostico.setPerimetro(lado12 + lado23 + lado31);
			pronostico.setClima(Clima.LLUVIA.name());
			return;
		}

		// Busco el lado mas largo
		Double ladoMX = null;
		Double ladoA1 = null;
		Double ladoA2 = null;

		if (lado12 > lado23 && lado12 > lado31) {
			ladoMX = lado12;
			ladoA1 = lado23;
			ladoA2 = lado31;
		} else if (lado23 > lado12 && lado23 > lado31) {
			ladoMX = lado23;
			ladoA1 = lado12;
			ladoA2 = lado31;
		} else if (lado31 > lado12 && lado31 > lado23) {
			ladoMX = lado31;
			ladoA1 = lado12;
			ladoA2 = lado23;
		} else {
			// si llegue aca quiere decir que NO hay
			// un maximo por que 2 ó 3 lados son iguales.
			// Sin un maximo NO hay "condiciones optimas"
			pronostico.setPerimetro(0);
			pronostico.setClima(Clima.NORMAL.name());
			return;
		}

		double margenErr = 10;
		// Para que haya "condiciones optimas", el lado de longitud maxima
		// debe ser igual a la suma de los otros 2.
		if (((ladoA1 + ladoA2) - ladoMX) <= margenErr) {
			// el perimetro solo nos interesa en temporada de lluvia,
			// entonce aca le ponemos cero
			pronostico.setPerimetro(0);
			pronostico.setClima(Clima.CONDICIONES_OPTIMAS.name());
			return;
		}

		pronostico.setPerimetro(0);
		pronostico.setClima(Clima.NORMAL.name());
	}

	/**
	 * Método para persistir los datos del clima en un momento dado del tiempo.
	 * 
	 * @param pronostico
	 */
	private void persistir(Pronostico pronostico) {

		try (SqlSession ss = sqlSessionFactory.openSession()) {
			PronosticoMapper opm = ss.getMapper(PronosticoMapper.class);
			opm.createPronostico(pronostico);
		}

	}

}
