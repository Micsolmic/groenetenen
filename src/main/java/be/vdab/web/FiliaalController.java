package be.vdab.web;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import be.vdab.entities.Filiaal;
import be.vdab.exceptions.FiliaalHeeftNogWerknemersException;
import be.vdab.services.FiliaalService;

@Controller
@RequestMapping("/filialen")
public class FiliaalController {

	private static final String FILIALEN_VIEW = "filialen/filialen";
	private static final String TOEVOEGEN_VIEW = "filialen/toevoegen";
	private static final String FILIAAL_VIEW = "filialen/filiaal";
	private static final String REDIRECT_URL_FILIAAL_NIET_GEVONDEN = "redirect:/filialen";
	private static final String REDIRECT_URL_NA_VERWIJDEREN = "redirect:/filialen/{id}/verwijderd";
	private static final String REDIRECT_URL_HEEFT_NOG_WERKNEMERS = "redirect:/filialen/{id}";
	private static final String VERWIJDERD_VIEW = "filialen/verwijderd";
	private final FiliaalService filiaalService;

	FiliaalController(FiliaalService filiaalService) {
		this.filiaalService = filiaalService;
	}

	@GetMapping("{idd}")
	ModelAndView read(@PathVariable long idd) {
		ModelAndView modelAndView = new ModelAndView(FILIAAL_VIEW);
		filiaalService.read(idd).ifPresent(filiaal -> modelAndView.addObject(filiaal));

		return modelAndView;
	}
	
	@GetMapping("{id}/verwijderd")
	String deleted(String naam) {
	return VERWIJDERD_VIEW;
	}
	
	@GetMapping
	ModelAndView findAll() {
		return new ModelAndView(FILIALEN_VIEW, "filialen", filiaalService.findAll());
	}

	@GetMapping("toevoegen")
	String createForm() {
		return TOEVOEGEN_VIEW;
	}
	
	@PostMapping("{id}/verwijderen")
	String delete(@PathVariable long id, RedirectAttributes redirectAttributes) {
	Optional<Filiaal> optionalFiliaal = filiaalService.read(id);
	if (! optionalFiliaal.isPresent()) { 
	return REDIRECT_URL_FILIAAL_NIET_GEVONDEN;
	}
	try {
	filiaalService.delete(id);
	redirectAttributes.addAttribute("id", id) 
	.addAttribute("naam", optionalFiliaal.get().getNaam()); 
	return REDIRECT_URL_NA_VERWIJDEREN; 
	} catch (FiliaalHeeftNogWerknemersException ex) {
	redirectAttributes.addAttribute("id", id)
	.addAttribute("fout", "Filiaal heeft nog werknemers");
	return REDIRECT_URL_HEEFT_NOG_WERKNEMERS;
	}
	}

}
