USE olework;

DROP TABLE IF exists pronostico;
CREATE TABLE pronostico (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT
   COMMENT 'PK',

  anguloF INT NOT NULL
   COMMENT 'Ángulo en donde se encuentra el planeta Ferengi',
  anguloV INT NOT NULL
   COMMENT 'Ángulo en donde se encuentra el planeta Vulcano',
  anguloB INT NOT NULL
   COMMENT 'Ángulo en donde se encuentra el planeta Betasoide',
  dia INT NOT NULL
   COMMENT 'Número de dia',
  clima VARCHAR(30) NULL
   COMMENT 'Condición climática',
  perimetro DOUBLE NULL
   COMMENT 'perimetro del triangulo para calcular el pico máximo de lluvia',
  periodo INT NULL
   COMMENT 'nro de periodo de lluvia',
  ts_create TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
   COMMENT 'Creation timestamp',
  ts_modif TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
   COMMENT 'Modification timestamp',
  PRIMARY KEY (id)
);

