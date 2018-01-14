package be.vdab.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/filialen")
public class FiliaalController {

	
	private static final String FILIALEN_VIEW = "filialen/filialen";
	private static final String TOEVOEGEN_VIEW = "filialen/toevoegen";
	
	@GetMapping 
	String findAll() {
	return FILIALEN_VIEW; 
	}
	@GetMapping("toevoegen") 
	String createForm() {
	return TOEVOEGEN_VIEW;
	}
	
}
