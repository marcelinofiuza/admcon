CREATE TABLE conta (
  id_conta bigint(20) NOT NULL AUTO_INCREMENT,
  zempresa bigint(20) DEFAULT NULL,
  chave int(11) NOT NULL,
  descricao varchar(50) NOT NULL,
  natureza varchar(10) DEFAULT NULL,
  reduzida varchar(10) DEFAULT NULL,
  status varchar(10) DEFAULT NULL,
  tipo_conta varchar(10) DEFAULT NULL,
  id_conta_pai bigint(20) DEFAULT NULL,
  PRIMARY KEY (id_conta),
  KEY (id_conta_pai),
  CONSTRAINT FOREIGN KEY (id_conta_pai) REFERENCES conta (id_conta)
) ENGINE=InnoDB DEFAULT CHARSET=utf8