package ceballos.magali.rest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ceballos.magali.business.GeneratePronostico;
import ceballos.magali.business.PronosticoBO;
import ceballos.magali.business.ResetPronostico;
import ceballos.magali.data.domain.PeriodoClima;
import ceballos.magali.data.domain.PicoMaximo;
import ceballos.magali.data.domain.Pronostico;

@RestController
public class PronosticoController {

	/**
	 * Servicios varios con logica de negocio del pronostico
	 */
	@Autowired
	PronosticoBO bo;
	
	/**
	 * Servicio asincronico para borrar la tabla de pronostico
	 */
	@Autowired
	ResetPronostico resetJob;
	
	/**
	 * Servicio asincronico para generar los datos de la tabla de pronostico
	 */
	@Autowired
	GeneratePronostico generaJob;
	
	/**
	 * Ej:
	 * <pre>
	 * curl -vv http://localhost:8080/ping
	 * </pre>
	 */
    @GetMapping("/ping")
    public ResponseEntity<String> pingPong() {

        return ResponseEntity.ok().body("pong");
    }
    
    /**
	 * Ej:
	 * <pre>
	 * curl -vv http://localhost:8080/pronostico/dia/1
	 * </pre>
	 * 
     */
    @GetMapping("/pronostico/dia/{dia}")
    public ResponseEntity<Map<String,Object>> getPronostico(@PathVariable Integer dia) {
    	
		Pronostico op = bo.getPronosticoDelDia(dia);
		
		if(op==null) {
			return ResponseEntity.notFound().build();
		}
		
		Map<String, Object> resp = new HashMap<String, Object>();
		resp.put("Dia", op.getDia());
		resp.put("Clima", op.getClima());
        return ResponseEntity.ok().body(resp);
    }

	/**
	 * Inicia el borrado de la tabla "pronostico".
	 * <p>
	 * Ej:
	 * <pre>
	 * curl -vv http://localhost:8080/data/reset
	 * </pre>
	 */
    @GetMapping("/data/reset")
    public ResponseEntity<String> dataReset() {

    	// el metodo run es asincronico
    	resetJob.run();
    	
        return ResponseEntity.ok().build();
    }

	/**
	 * Inicia la carga de la tabla "pronostico".
	 * <p>
	 * Ej:
	 * <pre>
	 * curl -vv http://localhost:8080/data/generate
	 * </pre>
	 */
    @GetMapping("/data/generate")
    public ResponseEntity<String> dataGenerate() {

    	// el metodo run es asincronico
    	generaJob.run();
    	
        return ResponseEntity.ok().build();
    }
    
	/**
	 * <p>
	 * Ej:
	 * <pre>
	 * curl -vv http://localhost:8080/data/size
	 * </pre>
	 */
    @GetMapping("/data/size")
    public ResponseEntity<Map<String,Object>> dataSize() {
    	
        return ResponseEntity.ok().body(bo.getPronosticoSize());
    }

    
    /**
	 * Ej:
	 * <pre>
	 * curl -vv http://localhost:8080/pronostico/periodoclima
	 * </pre>
	 * 
     */
    @GetMapping("/pronostico/periodoclima")
    public ResponseEntity<List<PeriodoClima>> getPeriodo() {
    	
    	List<PeriodoClima> op = bo.getPeriodoClima();
		
		if(op==null) {
			return ResponseEntity.notFound().build();
		}
		
        return ResponseEntity.ok().body(op);
    }
    
    /**
	 * Ej:
	 * <pre>
	 * curl -vv http://localhost:8080/pronostico/picomaximo
	 * </pre>
	 * 
     */
    @GetMapping("/pronostico/picomaximo")
    public ResponseEntity<PicoMaximo> getPicoMaximo() {
    	
    	PicoMaximo op = bo.getPicoMaximo();
		
		if(op==null) {
			return ResponseEntity.notFound().build();
		}
		
        return ResponseEntity.ok().body(op);
    }

}
