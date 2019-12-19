package ceballos.magali.business;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MatematicaHelperTest {

	/**
	 * Margen de error que uso para estas pruebas
	 */
	public final Double MARGEN =0.001;
	
	// Tipicamente esta clase deberia instanciarla usado spring, es decir,
	// anontandola con @autowired en vez de usando la palabre clave 'new'.
	// Pero en el caso de esta clase en particular puede correr sin cargar
	// sring previamente.
	// Como esto hace que los test corran mas rapido, uso la clase sin Spring.
	MatematicaHelper mh = new MatematicaHelper();
	
	// Valido los resultados con:
	// https://www.calculatorsoup.com/calculators/geometry-plane/triangle-law-of-cosines.php
	// Pongo el combo "calculate" en "Side A"
	
	
	@Test
	public void testGetladoOpuesto01() {
		
		Double res =mh.getladoOpuesto(90.0, 3, 4);

		assertEquals(5.0, res, MARGEN);
		System.out.println(res);
	}
	
	@Test
	public void testGetladoOpuesto02() {
		
		Double res =mh.getladoOpuesto(1.0, 7, 9);

		assertEquals(2.00479, res, MARGEN);
		System.out.println(res);
	}
	
	@Test
	public void testGetladoOpuesto03() {
		
		Double res =mh.getladoOpuesto(65.222, 8.03, 6.78);

		assertEquals(8.05074, res, MARGEN);
		System.out.println(res);
	}
	
	@Test
	public void testGetladoOpuesto04() {
		
		Double res =mh.getladoOpuesto(156.501, 8.03, 6.78);

		assertEquals(14.5019, res, MARGEN);
		System.out.println(res);
	}
	
	@Test
	public void testGetladoOpuesto05() {
		
		Double res =mh.getladoOpuesto(180, 5, 6);

		assertEquals(11, res, MARGEN);
		System.out.println(res);
	}
	
	@Test
	public void testGetladoOpuesto06() {
		
		Double res =mh.getladoOpuesto(270.0, 3, 4);

		assertEquals(5, res, MARGEN);
		System.out.println(res);
	}
}
