package com.home;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.home.domaine.Produit;
import com.home.repository.ProduitRepository;

@SpringBootApplication
public class GestionProduit1Application implements CommandLineRunner{
@Autowired
	private ProduitRepository produitRepository;
	
	
	
	public static void main(String[] args) {
		SpringApplication.run(GestionProduit1Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		produitRepository.save(new Produit("hp", 5555.3, 33));
		produitRepository.save(new Produit("lenovo", 8555.3, 21));
		produitRepository.save(new Produit("dell", 10000.3, 83));
		produitRepository.save(new Produit("acer", 6000.3, 133));
List<Produit> listprod=produitRepository.findAll();
		for (Produit st : listprod) {
	System.out.println(st.getDesignation());
}
		
	}

}
