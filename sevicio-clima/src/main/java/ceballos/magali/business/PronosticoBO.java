package ceballos.magali.business;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ceballos.magali.data.domain.PeriodoClima;
import ceballos.magali.data.domain.PicoMaximo;
import ceballos.magali.data.domain.Pronostico;
import ceballos.magali.data.mapper.PronosticoMapper;

/**
 * Objeto que tiene gran parte de la logica de negocio del pronostico. 
 * 
 * @author MagaliC
 *
 */
@Service
public class PronosticoBO {


	@Autowired
	SqlSessionFactory sqlSessionFactory;
    
    @Value("${pronostico.cantidadDeDias}")
    Long cantidadDeDias;
    
    
	public Map<String, Object> getPronosticoSize() {
		Long count=null;
		
		try (SqlSession ss = sqlSessionFactory.openSession() ){
			PronosticoMapper cm = ss.getMapper(PronosticoMapper.class);
			
			count = cm.getCount();
		}
		
		Map<String, Object> res = new HashMap<String, Object>(2); 
		
		res.put("cantidad-actual", count);
		res.put("cantidad-esperada", cantidadDeDias);
		return res;
	}
        
	public Pronostico getPronosticoDelDia(Integer dia) {
		Pronostico pronostico=null;
		
		try (SqlSession ss = sqlSessionFactory.openSession() ){
			PronosticoMapper cm = ss.getMapper(PronosticoMapper.class);
			
			pronostico = cm.getPronosticoByDia(dia);
		}
		
		return pronostico;
	}
	
	public List<PeriodoClima> getPeriodoClima() {
		List<PeriodoClima> periodo=null;
		
		try (SqlSession ss = sqlSessionFactory.openSession() ){
			PronosticoMapper cm = ss.getMapper(PronosticoMapper.class);
			
			periodo = cm.getPeriodoClima();
		}
		
		return periodo;
	}
	
	public PicoMaximo getPicoMaximo() {
		PicoMaximo p=null;
		
		try (SqlSession ss = sqlSessionFactory.openSession() ){
			PronosticoMapper cm = ss.getMapper(PronosticoMapper.class);
			
			p = cm.getPicoMaximo();
		}
		
		return p;
	}
	
}
