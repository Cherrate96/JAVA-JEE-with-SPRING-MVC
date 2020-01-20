package com.home.controller;

import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.home.domaine.Produit;
import com.home.repository.ProduitRepository;

@Controller
public class ProductController {
	@Autowired
	private ProduitRepository produitRepository;
	

@RequestMapping(value = "/admin/home",method = RequestMethod.GET)
public String Home(Model model,@RequestParam(name = "page",defaultValue = "0") int page,@RequestParam(name = "motCle",defaultValue = "") String st)
{
	Page<Produit> produits=produitRepository.findByDesignationContains(st, PageRequest.of(page, 5));
	model.addAttribute("pages", new int[produits.getTotalPages()]);
	model.addAttribute("produit", produits.getContent());
	model.addAttribute("motCle", st);
	model.addAttribute("CurrentPage", page);
	
	return "home";
}

@GetMapping("/admin/save")
public String FormAdd(Model map)
{
	map.addAttribute("produit", new Produit());
	return "AjouterProduit";
}


@RequestMapping(value = "/admin/save",method = RequestMethod.POST)
public String AjouterProduit(Model model,@Valid Produit p,BindingResult bindingResult)
{
	if(bindingResult.hasErrors()) return "AjouterProduit";
	produitRepository.save(p);
	
	return "redirect:/admin/home";
}

@RequestMapping(value = "/admin/layout",method = RequestMethod.GET)
public String Layout()
{
	return "/admin/layout";
}


@RequestMapping(value = "/admin/editer",method = RequestMethod.GET)
public String EditerProduit(Long id,Map map)
{
	Produit produit=produitRepository.findById(id).get();
	
	map.put("produit", produit);
	return "Modifier";
}


@RequestMapping(value = "/admin/delete",method = RequestMethod.GET)
public String Supprimer(Long id, int page,String mc)
{
	produitRepository.deleteById(id);
	
	return "redirect:/admin/home?page="+page+"&motCle="+mc;
}

@GetMapping("/")
public String def()
{
	return "redirect:/admin/home";
}
@GetMapping("/login")
public String login()
{
	return "login";
}

}
