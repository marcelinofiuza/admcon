/***************************************/
/*TABELA UNIDADE DE MEDIDA             */
/***************************************/
CREATE TABLE produto_medida (
  id_medida bigint(20) NOT NULL AUTO_INCREMENT,
  zempresa bigint(20) DEFAULT NULL,
  unidade varchar(5) NOT NULL,
  descricao varchar(50) NOT NULL,
  numeral decimal(19,2) DEFAULT NULL,
  PRIMARY KEY (id_medida)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO produto_medida (unidade, descricao, numeral) VALUES ('UN',   'Unidade', 	'1');
INSERT INTO produto_medida (unidade, descricao, numeral) VALUES ('PAR',  'Pares', 		'2');
INSERT INTO produto_medida (unidade, descricao, numeral) VALUES ('PC',   'Peça', 		'1');
INSERT INTO produto_medida (unidade, descricao, numeral) VALUES ('CJ',   'Conjunto', 	'1');
INSERT INTO produto_medida (unidade, descricao, numeral) VALUES ('DEZ',  'Dezena', 		'10');
INSERT INTO produto_medida (unidade, descricao, numeral) VALUES ('CEM',  'Centena', 	'100');
INSERT INTO produto_medida (unidade, descricao, numeral) VALUES ('MIL',  'Milheiro', 	'1000');

/***************************************/
/*TABELA PRODUTO CATEGORIA             */
/***************************************/
CREATE TABLE produto_categoria (
  id_categoria bigint(20) NOT NULL AUTO_INCREMENT,
  zempresa bigint(20) DEFAULT NULL,
  descricao varchar(50) NOT NULL,
  componente bit(1) NOT NULL,
  estoque bit(1) NOT NULL,
  venda bit(1) NOT NULL,
  PRIMARY KEY (id_categoria)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO produto_categoria (descricao, componente, estoque, venda) VALUES ('Acabado'			,1,0,1);
INSERT INTO produto_categoria (descricao, componente, estoque, venda) VALUES ('Semi-Acabado'	,0,1,0);
INSERT INTO produto_categoria (descricao, componente, estoque, venda) VALUES ('Revenda'			,0,1,1);
INSERT INTO produto_categoria (descricao, componente, estoque, venda) VALUES ('Embalagem'		,0,1,0);
INSERT INTO produto_categoria (descricao, componente, estoque, venda) VALUES ('Consumo'			,0,0,0);

/***************************************/
/*TABELA PRODUTO GRUPO                 */
/***************************************/
CREATE TABLE produto_grupo (
  id_grupo bigint(20) NOT NULL AUTO_INCREMENT,
  zempresa bigint(20) DEFAULT NULL,
  descricao varchar(50) NOT NULL,
  PRIMARY KEY (id_grupo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO produto_grupo (descricao) VALUES ('Amigurumi');
INSERT INTO produto_grupo (descricao) VALUES ('Agulha Emborrachada');
INSERT INTO produto_grupo (descricao) VALUES ('Agulha Cabo Plástivo');
INSERT INTO produto_grupo (descricao) VALUES ('Agulha Cabo Bambú');
INSERT INTO produto_grupo (descricao) VALUES ('Olhos Simples');
INSERT INTO produto_grupo (descricao) VALUES ('Olhos Gatos');
INSERT INTO produto_grupo (descricao) VALUES ('Olhos Ovais');
INSERT INTO produto_grupo (descricao) VALUES ('Fuças');
INSERT INTO produto_grupo (descricao) VALUES ('Fuças Cachorro');

/***************************************/
/*TABELA PRODUTO -                     */
/***************************************/
CREATE TABLE produto (
  id_produto bigint(20) NOT NULL AUTO_INCREMENT,
  zempresa bigint(20) DEFAULT NULL,
  ativo bit(1) NOT NULL,
  descricao varchar(100) DEFAULT NULL,
  sku varchar(50) DEFAULT NULL,
  gtin varchar(50) DEFAULT NULL,
  mpn varchar(50) DEFAULT NULL,
  ncm varchar(50) DEFAULT NULL,
  peso decimal(19,3) DEFAULT NULL,
  altura decimal(19,0) DEFAULT NULL,
  largura decimal(19,0) DEFAULT NULL,
  profundidade decimal(19,0) DEFAULT NULL,
  aliq_acrescimo decimal(19,3) DEFAULT NULL,
  valor_acrescimo decimal(19,2) DEFAULT NULL,  
  preco_venda decimal(19,2) DEFAULT NULL,
  id_grupo bigint(20) DEFAULT NULL,
  id_categoria bigint(20) DEFAULT NULL,
  id_medida bigint(20) DEFAULT NULL,
  PRIMARY KEY (id_produto),
  KEY (id_grupo),
  KEY (id_categoria),
  KEY (id_medida),
  CONSTRAINT FOREIGN KEY (id_grupo) REFERENCES produto_grupo (id_grupo),
  CONSTRAINT FOREIGN KEY (id_categoria) REFERENCES produto_categoria (id_categoria),
  CONSTRAINT FOREIGN KEY (id_medida) REFERENCES produto_medida (id_medida)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/***************************************/
/*TABELA PRODUTO COMPONENTE            */
/***************************************/
CREATE TABLE produto_componente (
  id_componente bigint(20) NOT NULL AUTO_INCREMENT,
  zempresa bigint(20) DEFAULT NULL,
  qtd_utilizada decimal(19,3) NOT NULL,
  id_item bigint(20) DEFAULT NULL,
  id_produto bigint(20) DEFAULT NULL,
  PRIMARY KEY (id_componente),
  KEY (id_item),
  KEY (id_produto),  
  CONSTRAINT FOREIGN KEY (id_item) REFERENCES produto (id_produto),
  CONSTRAINT FOREIGN KEY (id_produto) REFERENCES produto (id_produto)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/***************************************/
/*TABELA Estoque Header                */
/***************************************/
CREATE TABLE estoque_header (
  id_estoque bigint(20) NOT NULL AUTO_INCREMENT,
  zempresa bigint(20) DEFAULT NULL,
  data_inicio date DEFAULT NULL,
  data_final date DEFAULT NULL,
  abertura bit(1) NOT NULL,
  fechado bit(1) NOT NULL,
  PRIMARY KEY (id_estoque)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

/***************************************/
/*TABELA Estoque Item                  */
/***************************************/
CREATE TABLE estoque_item (
  id_item bigint(20) NOT NULL AUTO_INCREMENT,
  zempresa bigint(20) DEFAULT NULL,
  id_estoque bigint(20) DEFAULT NULL,
  id_produto bigint(20) DEFAULT NULL,
  quantidade decimal(19,3) DEFAULT NULL,
  unitario decimal(19,2) DEFAULT NULL,
  PRIMARY KEY (id_item),
  KEY(id_estoque),
  KEY(id_produto),
  CONSTRAINT FOREIGN KEY (id_estoque) REFERENCES estoque_header (id_estoque),
  CONSTRAINT FOREIGN KEY (id_produto) REFERENCES produto (id_produto)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*****************************************/
/*TABELA LACAMENTO MOVIMENTAÇÃO MATERIAL */
/*****************************************/
CREATE TABLE estoque_lancamento (
  id_lancamento bigint(20) NOT NULL AUTO_INCREMENT,
  zempresa bigint(20) DEFAULT NULL,
  data_lcto date DEFAULT NULL,
  documento varchar(15) DEFAULT NULL,
  origem_estoque varchar(6) DEFAULT NULL,
  sentido_estoque varchar(1) DEFAULT NULL,
  quantidade decimal(19,3) DEFAULT NULL, 
  unitario decimal(19,2) DEFAULT NULL, 
  id_estoque bigint(20) DEFAULT NULL,
  id_produto bigint(20) DEFAULT NULL,
  PRIMARY KEY (id_lancamento),
  KEY (id_estoque),
  KEY (id_produto),
  CONSTRAINT FOREIGN KEY (id_estoque) REFERENCES estoque_header (id_estoque),
  CONSTRAINT FOREIGN KEY (id_produto) REFERENCES produto (id_produto)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;  