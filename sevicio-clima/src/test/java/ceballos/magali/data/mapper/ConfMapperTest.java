package ceballos.magali.data.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ceballos.magali.data.domain.Pronostico;

@RunWith(SpringJUnit4ClassRunner.class)
@EnableAutoConfiguration
@TestPropertySource("classpath:application.properties")
@MapperScan("ceballos.magali.data.mapper")
public class ConfMapperTest {

	@Autowired
	SqlSessionFactory sqlSessionFactory;

	protected static int availableId;

	protected final String KEY1_1 = "hecho";
	protected final String VAL1_1 = "si";

	protected final String KEY1_2 = "pendiente";
	protected final String VAL1_2 = "no";
	
	@Test
	public void test01() {
		assertNotNull(sqlSessionFactory);
	}

	@Test
	public void test02() {

		Pronostico op = new Pronostico();

		int createdRows = 0;
		SqlSession ss = null;
		try {
			ss = sqlSessionFactory.openSession();
			PronosticoMapper opm = ss.getMapper(PronosticoMapper.class);
			createdRows = opm.createPronostico(op);
			
			fail("Debio arrojar excepcion por que el objeto que se va a grabar esta vacio");
		} catch (Exception e) {
			// se espera que arroje esta excepcion
			e.printStackTrace();
		} finally {
			if (ss != null) {
				ss.close();
			}
		}

		assertEquals(0, createdRows);
		assertNull(op.getId());
	}
	
	@Test
	public void test03() {

		Pronostico op = new Pronostico();
//		op.setClave(KEY1_1);
//		op.setValor(VAL1_1);

		int createdRows = 0;
		SqlSession ss = null;
		try {
			
			ss = sqlSessionFactory.openSession();
			PronosticoMapper opm = ss.getMapper(PronosticoMapper.class);
			createdRows = opm.createPronostico(op);
			ConfMapperTest.availableId = op.getId();
		} catch (Exception e) {
			fail(e.getMessage());
		} finally {
			if (ss != null) {
				ss.close();
			}
		}

		assertEquals(1, createdRows);
		assertNotNull(op.getId());
	}
	
	@Test
	public void test04() {

		Pronostico op = new Pronostico();
		op.setId(ConfMapperTest.availableId);
//		op.setClave(KEY1_2);
//		op.setValor(VAL1_2);
		
		int rows = 0;
		try (SqlSession ss = sqlSessionFactory.openSession() ){
			PronosticoMapper opm = ss.getMapper(PronosticoMapper.class);
			
			rows = opm.updatePronosticoById(op);
		}
		
		assertEquals(1, rows);
	}
	
	@Test
	public void test05() {

		Pronostico op = null;
		
		try (SqlSession ss = sqlSessionFactory.openSession() ){
			PronosticoMapper opm = ss.getMapper(PronosticoMapper.class);
			
			op = opm.getPronosticoById(ConfMapperTest.availableId);
		}
		
		assertNotNull(op);
//		assertEquals(KEY1_2, op.getClave());
//		assertEquals(VAL1_2, op.getValor());
	}
	
	@Test
	public void test06() {

		int rows = 0;
		try (SqlSession ss = sqlSessionFactory.openSession() ){
			PronosticoMapper opm = ss.getMapper(PronosticoMapper.class);
			
			rows = opm.deletePronostico(availableId);
		}
		
		assertEquals(1, rows);
	}
}
