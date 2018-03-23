/**************************/
/*TABELA DE CONTA CONTÁBIL*/
/**************************/
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*********************/
/*TABELAS DE CLIENTES*/
/*********************/
CREATE TABLE cliente (
  id_cliente bigint(20) NOT NULL AUTO_INCREMENT,
  zempresa bigint(20) DEFAULT NULL,
  cnpj varchar(18) DEFAULT NULL,
  cpf varchar(14) DEFAULT NULL,
  bairro varchar(40) DEFAULT NULL,
  cep varchar(9) DEFAULT NULL,
  cidade varchar(40) DEFAULT NULL,
  complemento varchar(20) DEFAULT NULL,
  logradouro varchar(50) DEFAULT NULL,
  numero varchar(10) DEFAULT NULL,
  tipo_logradouro varchar(15) DEFAULT NULL,
  uf varchar(2) DEFAULT NULL,
  fantasia varchar(40) NOT NULL,
  ins_estadual varchar(15) DEFAULT NULL,
  ins_municipal varchar(15) DEFAULT NULL,
  razao_social varchar(80) NOT NULL,
  unidade varchar(15) DEFAULT NULL,
  id_conta bigint(20) DEFAULT NULL,
  PRIMARY KEY (id_cliente),
  KEY (id_conta),
  CONSTRAINT FOREIGN KEY (id_conta) REFERENCES conta (id_conta)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE cliente_contatos (
  id_contato bigint(20) NOT NULL AUTO_INCREMENT,
  zempresa bigint(20) DEFAULT NULL,
  celular varchar(15) DEFAULT NULL,
  email varchar(100) DEFAULT NULL,
  nome_contato varchar(50) DEFAULT NULL,
  ramal varchar(5) DEFAULT NULL,
  telefone varchar(15) DEFAULT NULL,
  id_cliente bigint(20) DEFAULT NULL,
  PRIMARY KEY (id_contato),
  KEY (id_cliente),
  CONSTRAINT FOREIGN KEY (id_cliente) REFERENCES cliente (id_cliente)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*************************/
/*TABELAS DE FORNECEDORES*/
/*************************/
CREATE TABLE fornecedor (
  id_fornecedor bigint(20) NOT NULL AUTO_INCREMENT,
  zempresa bigint(20) DEFAULT NULL,
  cnpj varchar(18) DEFAULT NULL,
  cpf varchar(14) DEFAULT NULL,
  bairro varchar(40) DEFAULT NULL,
  cep varchar(9) DEFAULT NULL,
  cidade varchar(40) DEFAULT NULL,
  complemento varchar(20) DEFAULT NULL,
  logradouro varchar(50) DEFAULT NULL,
  numero varchar(10) DEFAULT NULL,
  tipo_logradouro varchar(15) DEFAULT NULL,
  uf varchar(2) DEFAULT NULL,
  fantasia varchar(40) NOT NULL,
  fr_codigo varchar(15) DEFAULT NULL,
  ins_estadual varchar(15) DEFAULT NULL,
  ins_municipal varchar(15) DEFAULT NULL,
  razao_social varchar(80) NOT NULL,
  id_conta bigint(20) DEFAULT NULL,
  PRIMARY KEY (id_fornecedor),
  KEY (id_conta),
  CONSTRAINT FOREIGN KEY (id_conta) REFERENCES conta (id_conta)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE fornecedor_contatos (
  id_contato bigint(20) NOT NULL AUTO_INCREMENT,
  zempresa bigint(20) DEFAULT NULL,
  celular varchar(15) DEFAULT NULL,
  email varchar(100) DEFAULT NULL,
  nome_contato varchar(50) DEFAULT NULL,
  ramal varchar(5) DEFAULT NULL,
  telefone varchar(15) DEFAULT NULL,
  id_fornecedor bigint(20) DEFAULT NULL,
  PRIMARY KEY (id_contato),
  KEY (id_fornecedor),
  CONSTRAINT FOREIGN KEY (id_fornecedor) REFERENCES fornecedor (id_fornecedor)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*******************/
/*TABALAS DE BANCOS*/
/*******************/
CREATE TABLE banco (
  id_banco bigint(20) NOT NULL AUTO_INCREMENT,
  zempresa bigint(20) DEFAULT NULL,
  agencia varchar(10) DEFAULT NULL,
  descricao varchar(50) NOT NULL,
  bairro varchar(40) DEFAULT NULL,
  cep varchar(9) DEFAULT NULL,
  cidade varchar(40) DEFAULT NULL,
  complemento varchar(20) DEFAULT NULL,
  logradouro varchar(50) DEFAULT NULL,
  numero varchar(10) DEFAULT NULL,
  tipo_logradouro varchar(15) DEFAULT NULL,
  uf varchar(2) DEFAULT NULL,
  febraban varchar(5) DEFAULT NULL,
  id_migracao varchar(10) DEFAULT NULL,
  nome_agencia varchar(30) DEFAULT NULL,
  numero_conta varchar(15) DEFAULT NULL,
  seq_arquivo bigint(20) DEFAULT NULL,
  id_conta bigint(20) DEFAULT NULL,
  PRIMARY KEY (id_banco),
  KEY (id_conta),
  CONSTRAINT FOREIGN KEY (id_conta) REFERENCES conta (id_conta)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE banco_contatos (
  id_contato bigint(20) NOT NULL AUTO_INCREMENT,
  zempresa bigint(20) DEFAULT NULL,
  celular varchar(15) DEFAULT NULL,
  email varchar(100) DEFAULT NULL,
  nome_contato varchar(50) DEFAULT NULL,
  ramal varchar(5) DEFAULT NULL,
  telefone varchar(15) DEFAULT NULL,
  id_banco bigint(20) DEFAULT NULL,
  PRIMARY KEY (id_contato),
  KEY (id_banco),
  CONSTRAINT FOREIGN KEY (id_banco) REFERENCES banco (id_banco)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE banco_periodo (
  id_periodo bigint(20) NOT NULL AUTO_INCREMENT,
  zempresa bigint(20) DEFAULT NULL,
  abertura bit(1) NOT NULL,
  data_final date DEFAULT NULL,
  data_inicio date DEFAULT NULL,
  fechado bit(1) NOT NULL,
  saldo_inicial decimal(19,2) DEFAULT NULL,
  id_banco bigint(20) DEFAULT NULL,
  PRIMARY KEY (id_periodo),
  KEY (id_banco),
  CONSTRAINT FOREIGN KEY (id_banco) REFERENCES banco (id_banco)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE banco_carteira (
  id_carteira bigint(20) NOT NULL AUTO_INCREMENT,
  zempresa bigint(20) DEFAULT NULL,
  agencia varchar(10) DEFAULT NULL,
  cod_carteira varchar(2) DEFAULT NULL,
  cod_mestre varchar(7) DEFAULT NULL,
  conta varchar(15) DEFAULT NULL,
  descricao varchar(50) DEFAULT NULL,
  layout_cnab varchar(4) DEFAULT NULL,
  id_banco bigint(20) DEFAULT NULL,
  PRIMARY KEY (id_carteira),
  KEY (id_banco),
  CONSTRAINT FOREIGN KEY (id_banco) REFERENCES banco (id_banco)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/********************************/
/*TABALAS DE COBRANÇAS E BOLETOS*/
/********************************/
CREATE TABLE cobranca (
  id_cobranca bigint(20) NOT NULL AUTO_INCREMENT,
  zempresa bigint(20) DEFAULT NULL,
  descricao varchar(50) NOT NULL,
  ins_linha01 varchar(80) DEFAULT NULL,
  ins_linha02 varchar(80) DEFAULT NULL,
  ins_linha03 varchar(80) DEFAULT NULL,
  ins_linha04 varchar(80) DEFAULT NULL,
  ins_linha05 varchar(80) DEFAULT NULL,
  ins_linha06 varchar(80) DEFAULT NULL,
  ins_linha07 varchar(80) DEFAULT NULL,
  ins_linha08 varchar(80) DEFAULT NULL,
  id_conta bigint(20) DEFAULT NULL,
  PRIMARY KEY (id_cobranca),
  KEY (id_conta),
  CONSTRAINT FOREIGN KEY (id_conta) REFERENCES conta (id_conta)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE cobranca_item (
  id_item bigint(20) NOT NULL AUTO_INCREMENT,
  zempresa bigint(20) DEFAULT NULL,
  dia_vencimento int(11) DEFAULT NULL,
  fracao1 decimal(19,6) DEFAULT NULL,
  fracao2 decimal(19,6) DEFAULT NULL,
  fracao3 decimal(19,6) DEFAULT NULL,
  valor decimal(19,2) DEFAULT NULL,
  id_cliente bigint(20) DEFAULT NULL,
  id_cobranca bigint(20) DEFAULT NULL,
  PRIMARY KEY (id_item),
  KEY (id_cliente),
  KEY (id_cobranca),
  CONSTRAINT FOREIGN KEY (id_cobranca) REFERENCES cobranca (id_cobranca),
  CONSTRAINT FOREIGN KEY (id_cliente) REFERENCES cliente (id_cliente)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE boleto (
  id_boleto bigint(20) NOT NULL AUTO_INCREMENT,
  zempresa bigint(20) DEFAULT NULL,
  ins_linha01 varchar(80) DEFAULT NULL,
  ins_linha02 varchar(80) DEFAULT NULL,
  ins_linha03 varchar(80) DEFAULT NULL,
  ins_linha04 varchar(80) DEFAULT NULL,
  ins_linha05 varchar(80) DEFAULT NULL,
  ins_linha06 varchar(80) DEFAULT NULL,
  ins_linha07 varchar(80) DEFAULT NULL,
  ins_linha08 varchar(80) DEFAULT NULL,
  lancamento date NOT NULL,
  status_boleto varchar(15) DEFAULT NULL,
  id_banco bigint(20) DEFAULT NULL,
  id_conta bigint(20) DEFAULT NULL,
  PRIMARY KEY (id_boleto),
  KEY (id_banco),
  KEY (id_conta),
  CONSTRAINT FOREIGN KEY (id_conta) REFERENCES conta (id_conta),
  CONSTRAINT FOREIGN KEY (id_banco) REFERENCES banco (id_banco)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE boleto_item (
  id_item bigint(20) NOT NULL AUTO_INCREMENT,
  zempresa bigint(20) DEFAULT NULL,
  documento bigint(20) DEFAULT NULL,
  valor0 decimal(19,2) DEFAULT NULL,
  valor1 decimal(19,2) DEFAULT NULL,
  valor2 decimal(19,2) DEFAULT NULL,
  valor3 decimal(19,2) DEFAULT NULL,
  vencimento date DEFAULT NULL,
  id_boleto bigint(20) DEFAULT NULL,
  id_cliente bigint(20) DEFAULT NULL,
  PRIMARY KEY (id_item),
  KEY (id_boleto),
  KEY (id_cliente),
  CONSTRAINT FOREIGN KEY (id_boleto) REFERENCES boleto (id_boleto),
  CONSTRAINT FOREIGN KEY (id_cliente) REFERENCES cliente (id_cliente)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*****************************/
/*TABALAS DE CONTAS A RECEBER*/
/*****************************/
CREATE TABLE receber (
  id_receber bigint(20) NOT NULL AUTO_INCREMENT,
  zempresa bigint(20) DEFAULT NULL,
  documento varchar(15) DEFAULT NULL,
  historico varchar(250) DEFAULT NULL,
  lancamento date DEFAULT NULL,
  valor decimal(19,2) NOT NULL,
  vencimento date DEFAULT NULL,
  id_boleto bigint(20) DEFAULT NULL,
  id_item bigint(20) DEFAULT NULL,
  id_cliente bigint(20) DEFAULT NULL,
  id_conta bigint(20) DEFAULT NULL,
  PRIMARY KEY (id_receber),
  KEY (id_boleto),
  KEY (id_item),
  KEY (id_cliente),
  KEY (id_conta),
  CONSTRAINT FOREIGN KEY (id_boleto) REFERENCES boleto (id_boleto),
  CONSTRAINT FOREIGN KEY (id_item) REFERENCES boleto_item (id_item),
  CONSTRAINT FOREIGN KEY (id_cliente) REFERENCES cliente (id_cliente),
  CONSTRAINT FOREIGN KEY (id_conta) REFERENCES conta (id_conta)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/***************************/
/*TABALAS DE CONTAS A PAGAR*/
/***************************/
CREATE TABLE pagar (
  id_pagar bigint(20) NOT NULL AUTO_INCREMENT,
  zempresa bigint(20) DEFAULT NULL,
  documento varchar(15) DEFAULT NULL,
  historico varchar(250) DEFAULT NULL,
  lancamento date DEFAULT NULL,
  valor decimal(19,2) NOT NULL,
  vencimento date DEFAULT NULL,
  id_conta bigint(20) DEFAULT NULL,
  id_fornecedor bigint(20) DEFAULT NULL,
  PRIMARY KEY (id_pagar),
  KEY (id_conta),
  KEY (id_fornecedor),
  CONSTRAINT FOREIGN KEY (id_fornecedor) REFERENCES fornecedor (id_fornecedor),
  CONSTRAINT FOREIGN KEY (id_conta) REFERENCES conta (id_conta)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/**********************************/
/*TABELAS DE LANÇAMENTO BANCARIA*/
/**********************************/
CREATE TABLE lancamento (
  id_lcto bigint(20) NOT NULL AUTO_INCREMENT,
  zempresa bigint(20) DEFAULT NULL,
  cheque bit(1) DEFAULT NULL,
  data_lcto date NOT NULL,
  desconto decimal(19,2) NOT NULL,
  documento varchar(15) NOT NULL,
  historico varchar(250) NOT NULL,
  juros decimal(19,2) NOT NULL,
  multa decimal(19,2) NOT NULL,
  origem_lcto varchar(3) DEFAULT NULL,
  tipo_lcto varchar(10) NOT NULL,
  valor_lcto decimal(19,2) NOT NULL,
  id_periodo bigint(20) DEFAULT NULL,
  id_conta bigint(20) DEFAULT NULL,
  id_pagar bigint(20) DEFAULT NULL,
  id_receber bigint(20) DEFAULT NULL,
  id_lcto_transf bigint(20) DEFAULT NULL,
  PRIMARY KEY (id_lcto),
  KEY (id_periodo),
  KEY (id_conta),
  KEY (id_pagar),
  KEY (id_receber),
  KEY (id_lcto_transf),
  CONSTRAINT FOREIGN KEY (id_lcto_transf) REFERENCES lancamento (id_lcto),
  CONSTRAINT FOREIGN KEY (id_receber) REFERENCES receber (id_receber),
  CONSTRAINT FOREIGN KEY (id_pagar) REFERENCES pagar (id_pagar),
  CONSTRAINT FOREIGN KEY (id_conta) REFERENCES conta (id_conta),
  CONSTRAINT FOREIGN KEY (id_periodo) REFERENCES banco_periodo (id_periodo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;