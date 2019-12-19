package ceballos.magali.business;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import ceballos.magali.data.mapper.PronosticoMapper;

@Service
public class ResetPronostico {


	@Autowired
	SqlSessionFactory sqlSessionFactory;
	
	/**
	 * Borra la tabla pronostico.
	 */
	@Async
	public void run() {
		
		try (SqlSession ss = sqlSessionFactory.openSession() ){
			PronosticoMapper opm = ss.getMapper(PronosticoMapper.class);
			
			opm.deleteAllPronostico();
		}
	}
	
}
