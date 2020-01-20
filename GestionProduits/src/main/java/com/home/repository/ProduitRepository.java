package com.home.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.home.domaine.Produit;

@Repository
@Transactional
public interface ProduitRepository extends JpaRepository<Produit, Long>{

	
	public Page<Produit> findByDesignationContains(String st,Pageable pageable);
	
}
