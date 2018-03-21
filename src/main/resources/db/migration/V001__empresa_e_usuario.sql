CREATE TABLE empresa (
  id_empresa bigint(20) NOT NULL AUTO_INCREMENT,
  cnae varchar(9) DEFAULT NULL,
  cnpj varchar(18) DEFAULT NULL,
  celular varchar(15) DEFAULT NULL,
  email varchar(100) DEFAULT NULL,
  nome_contato varchar(50) DEFAULT NULL,
  ramal varchar(5) DEFAULT NULL,
  telefone varchar(15) DEFAULT NULL,
  cpf varchar(14) DEFAULT NULL,
  data_abertura date DEFAULT NULL,
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
  natureza_juridica varchar(5) DEFAULT NULL,
  ramo_atividade varchar(30) DEFAULT NULL,
  razao_social varchar(80) NOT NULL,
  PRIMARY KEY (id_empresa)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE usuario (
  id_usuario bigint(20) NOT NULL AUTO_INCREMENT,
  bloqueado bit(1) NOT NULL,
  credencial varchar(20) NOT NULL,
  nome varchar(50) NOT NULL,
  senha varchar(255) NOT NULL,
  ultimo_acesso date DEFAULT NULL,
  PRIMARY KEY (id_usuario),
  UNIQUE KEY (credencial)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE usuario_empresa (
  id_usuario bigint(20) NOT NULL,
  id_empresa bigint(20) NOT NULL,
  KEY (id_empresa),
  KEY (id_usuario),
  CONSTRAINT FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario),
  CONSTRAINT FOREIGN KEY (id_empresa) REFERENCES empresa (id_empresa)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE usuario_roles (
  id_role bigint(20) NOT NULL AUTO_INCREMENT,
  role varchar(20) DEFAULT NULL,
  id_usuario bigint(20) DEFAULT NULL,
  PRIMARY KEY (id_role),
  KEY (id_usuario),
  CONSTRAINT FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
