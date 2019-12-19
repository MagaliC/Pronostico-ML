package ceballos.magali.data.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
public class PronosticoMapperTest {

	@Autowired
	SqlSessionFactory sqlSessionFactory;

	protected static int availableId;

	private final String CLIMA1 = "CLIMA1";
	private final String CLIMA2 = "CLIMA2";
	
	@Test
	public void test01() {
		assertNotNull(sqlSessionFactory);
	}
	
	@Test
	public void test02() {

		Pronostico op =  new Pronostico();
		op.setAnguloBetasoide(0);
		op.setAnguloFerengi(0);
		op.setAnguloVulcano(0);
		op.setPerimetro(0);
		op.setClima(CLIMA1);
		op.setDia(1);

		int createdRows = 0;
		SqlSession ss = null;
		try {
			
			ss = sqlSessionFactory.openSession();
			PronosticoMapper opm = ss.getMapper(PronosticoMapper.class);
			createdRows = opm.createPronostico(op);
			PronosticoMapperTest.availableId = op.getId();
		} catch (Exception e) {
			fail(e.getMessage());
		} finally {
			if (ss != null) {
				ss.close();
			}
		}

		assertEquals(1, createdRows);
		assertNotNull(PronosticoMapperTest.availableId);
	}
	
	@Test
	public void test03() {

		Pronostico op =  new Pronostico();
		op.setId(PronosticoMapperTest.availableId);
		op.setAnguloBetasoide(0);
		op.setAnguloFerengi(0);
		op.setAnguloVulcano(0);
		op.setPerimetro(0);
		op.setClima(CLIMA2);
		op.setDia(1);
		
		int rows = 0;
		try (SqlSession ss = sqlSessionFactory.openSession() ){
			PronosticoMapper opm = ss.getMapper(PronosticoMapper.class);
			
			rows = opm.updatePronosticoById(op);
		}
		
		assertEquals(1, rows);
	}
	
	@Test
	public void test04() {

		Pronostico op = null;
		
		try (SqlSession ss = sqlSessionFactory.openSession() ){
			PronosticoMapper opm = ss.getMapper(PronosticoMapper.class);
			
			op = opm.getPronosticoById(PronosticoMapperTest.availableId);
		}
		
		assertNotNull(op);
		assertEquals(CLIMA2, op.getClima());
	}
	
	@Test
	public void test05() {

		int rows = 0;
		try (SqlSession ss = sqlSessionFactory.openSession() ){
			PronosticoMapper opm = ss.getMapper(PronosticoMapper.class);
			
			rows = opm.deletePronostico(availableId);
		}
		
		assertEquals(1, rows);
	}
}
