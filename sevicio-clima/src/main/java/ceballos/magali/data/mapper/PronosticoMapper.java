package ceballos.magali.data.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import ceballos.magali.data.domain.PeriodoClima;
import ceballos.magali.data.domain.PicoMaximo;
import ceballos.magali.data.domain.Pronostico;

public interface PronosticoMapper {

    @Select("SELECT \n" +
            "    p.id                       id,\n" +
            "    p.anguloF                  anguloF,\n" +
            "    p.anguloV                  anguloV,\n" +
            "    p.anguloB                  anguloB,\n" +
            "    p.dia                      dia,\n" +
            "    p.clima                	clima,\n" +
            "    p.perimetro                perimetro,\n" +
            "    p.periodo                  periodo,\n" +
            "    p.ts_create                ts_create,\n" +
            "    p.ts_modif                 ts_modif\n" +
            "FROM pronostico p\n" +
            "WHERE p.id=#{id}")
	@Results({
	    @Result(property = "id", column = "id"),
	    @Result(property = "anguloFerengi", column = "anguloF"),
	    @Result(property = "anguloVulcano", column = "anguloV"),
	    @Result(property = "anguloBetasoide", column = "anguloB"),
	    @Result(property = "dia", column = "dia"),
	    @Result(property = "clima", column = "clima"),
	    @Result(property = "perimetro", column = "perimetro"),
	    @Result(property = "periodo", column = "periodo"),
	    @Result(property = "tsCreate", column = "ts_create"),
	    @Result(property = "tsModif", column = "ts_modif")
	})
	public Pronostico getPronosticoById(int id);
    
    
	@Insert("INSERT INTO pronostico "
			+ "(anguloF,            anguloV,          anguloB,            dia,    clima,    perimetro,    periodo)"
			+ " VALUES "
			+ "(#{anguloFerengi}, #{anguloVulcano}, #{anguloBetasoide}, #{dia}, #{clima}, #{perimetro}, #{periodo})")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	public int createPronostico(Pronostico pronostico);
	
	
    @Select("SELECT \n" +
            "    p.id                       id,\n" +
            "    p.anguloF                  anguloF,\n" +
            "    p.anguloV                  anguloV,\n" +
            "    p.anguloB                  anguloB,\n" +
            "    p.dia                      dia,\n" +
            "    p.clima                	clima,\n" +
            "    p.perimetro                perimetro,\n" +
            "    p.periodo                  periodo,\n" +
            "    p.ts_create                ts_create,\n" +
            "    p.ts_modif                 ts_modif\n" +
            "FROM pronostico p\n" +
            "WHERE p.dia=#{dia}")
	@Results({
	    @Result(property = "id", column = "id"),
	    @Result(property = "anguloFerengi", column = "anguloF"),
	    @Result(property = "anguloVulcano", column = "anguloV"),
	    @Result(property = "anguloBetasoide", column = "anguloB"),
	    @Result(property = "dia", column = "dia"),
	    @Result(property = "clima", column = "clima"),
	    @Result(property = "perimetro", column = "perimetro"),
	    @Result(property = "periodo", column = "periodo"),
	    @Result(property = "tsCreate", column = "ts_create"),
	    @Result(property = "tsModif", column = "ts_modif")
	})
	public Pronostico getPronosticoByDia(Integer dia);
    
    
    @Update("UPDATE pronostico SET\n" +
            "anguloF = #{anguloFerengi},\n" +
            "anguloV = #{anguloVulcano},\n" +
            "anguloB = #{anguloBetasoide},\n" +
            "dia = #{dia},\n" +
            "clima = #{clima},\n" +
            "perimetro = #{perimetro},\n" +
            "periodo = #{periodo}\n" +
            "WHERE id = #{id}")
    public int updatePronosticoById(Pronostico pronostico);

    @Select("SELECT count(*) FROM pronostico p")
    public long getCount();
    
    
    @Delete("DELETE FROM pronostico")
    public int deleteAllPronostico();
    
    @Delete("DELETE FROM pronostico WHERE id=#{id}")
    public int deletePronostico(int id);
    

    @Select("SELECT clima, max(periodo) cantidad  \n" + 
    		"FROM pronostico WHERE clima!='NORMAL' group by clima;")
	@Results({
	    @Result(property = "cantidad", column = "cantidad"),
	    @Result(property = "clima", column = "clima")
	})
	public List<PeriodoClima> getPeriodoClima();

    
    @Select("SELECT dia, clima FROM pronostico "+
    		"WHERE clima='LLUVIA' ORDER BY perimetro DESC LIMIT 1;")
	@Results({
	    @Result(property = "dia", column = "dia"),
	    @Result(property = "clima", column = "clima")
	})
	public PicoMaximo getPicoMaximo();
}
