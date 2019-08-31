package br.com.fti.admcon.modulos.repositorio.empresa;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fti.admcon.modulos.entidades.empresa.Produto;

public interface RepProduto extends JpaRepository<Produto, Long> {

}
