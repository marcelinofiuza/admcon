/***************************************/
/*TABELA PRODUTO UM - UNIDADE DE MEDIDA*/
/***************************************/
CREATE TABLE produto_um (
  id_um varchar(5) NOT NULL,
  zempresa bigint(20) DEFAULT NULL,
  descricao varchar(50) NOT NULL,
  numeral decimal(19,2) DEFAULT NULL,
  PRIMARY KEY (id_um)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/***************************************/
/*TABELA PRODUTO CATEGORIA             */
/***************************************/
CREATE TABLE produto_categoria (
  id_categoria bigint(20) NOT NULL AUTO_INCREMENT,
  zempresa bigint(20) DEFAULT NULL,
  descricao varchar(50) NOT NULL,
  PRIMARY KEY (id_categoria)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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
  id_categoria bigint(20) DEFAULT NULL,
  id_um varchar(5) DEFAULT NULL,
  PRIMARY KEY (id_produto),
  KEY (id_categoria),
  KEY (id_um),
  CONSTRAINT FOREIGN KEY (id_um) REFERENCES produto_um (id_um),
  CONSTRAINT FOREIGN KEY (id_categoria) REFERENCES produto_categoria (id_categoria)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;