/***************************************/
/*TABELA UNIDADE DE MEDIDA             */
/***************************************/
CREATE TABLE unidade_medida (
  id_um bigint(20) NOT NULL AUTO_INCREMENT,
  unidade varchar(5) NOT NULL,
  descricao varchar(50) NOT NULL,
  numeral decimal(19,2) DEFAULT NULL,
  PRIMARY KEY (id_um)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO unidade_medida (unidade, descricao, numeral) VALUES ('UN',  'Unidade', '1');
INSERT INTO unidade_medida (unidade, descricao, numeral) VALUES ('PAR', 'Pares', '2');
INSERT INTO unidade_medida (unidade, descricao, numeral) VALUES ('PC',  'Peça', '1');
INSERT INTO unidade_medida (unidade, descricao, numeral) VALUES ('CJ',  'Conjunto', '1');

/***************************************/
/*TABELA PRODUTO CATEGORIA             */
/***************************************/
CREATE TABLE categoria (
  id_categoria bigint(20) NOT NULL AUTO_INCREMENT,
  descricao varchar(50) NOT NULL,
  PRIMARY KEY (id_categoria)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO categoria (descricao) VALUES ('Acabado');
INSERT INTO categoria (descricao) VALUES ('Semi-Acabado');
INSERT INTO categoria (descricao) VALUES ('Embalagem');
INSERT INTO categoria (descricao) VALUES ('Consumo');

/***************************************/
/*TABELA PRODUTO GRUPO                 */
/***************************************/
CREATE TABLE produto_grupo (
  id_grupo bigint(20) NOT NULL AUTO_INCREMENT,
  zempresa bigint(20) DEFAULT NULL,
  descricao varchar(50) NOT NULL,
  PRIMARY KEY (id_grupo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/***************************************/
/*TABELA PRODUTO -                     */
/***************************************/
CREATE TABLE produto (
  id_produto bigint(20) NOT NULL AUTO_INCREMENT,
  zempresa bigint(20) DEFAULT NULL,
  aliq_acrescimo decimal(19,2) DEFAULT NULL,
  ativo bit(1) NOT NULL,
  controla_estoque bit(1) NOT NULL,
  descricao varchar(100) DEFAULT NULL,
  preco_venda decimal(19,2) DEFAULT NULL,
  valor_acrescimo decimal(19,2) DEFAULT NULL,
  id_grupo bigint(20) DEFAULT NULL,
  id_categoria bigint(20) DEFAULT NULL,
  id_um bigint(20) DEFAULT NULL,
  PRIMARY KEY (id_produto),
  KEY (id_grupo),
  KEY (id_categoria),
  KEY (id_um),
  CONSTRAINT FOREIGN KEY (id_grupo) REFERENCES produto_grupo (id_grupo),
  CONSTRAINT FOREIGN KEY (id_categoria) REFERENCES categoria (id_categoria),
  CONSTRAINT FOREIGN KEY (id_um) REFERENCES unidade_medida (id_um)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/***************************************/
/*TABELA PRODUTO COMPONENTE            */
/***************************************/
CREATE TABLE produto_componente (
  id_componente bigint(20) NOT NULL AUTO_INCREMENT,
  zempresa bigint(20) DEFAULT NULL,
  qtd_utilizada decimal(19,2) NOT NULL,
  id_produto bigint(20) DEFAULT NULL,
  PRIMARY KEY (id_componente),
  KEY (id_produto),
  CONSTRAINT FOREIGN KEY (id_componente) REFERENCES produto (id_produto),
  CONSTRAINT FOREIGN KEY (id_produto) REFERENCES produto (id_produto)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
