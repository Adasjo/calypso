package se.datasektionen.calypso.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.datasektionen.calypso.auth.DAuthUserDetails;
import se.datasektionen.calypso.exceptions.ResourceNotFoundException;
import se.datasektionen.calypso.models.entities.Item;
import se.datasektionen.calypso.models.repositories.ItemRepository;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Controller
@PreAuthorize("hasAuthority('post')")
public class EditController {

	private final ItemRepository itemRepository;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM YYYY HH:mm", Locale.forLanguageTag("sv"));

	@Autowired
	public EditController(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}

	@GetMapping("/admin/new")
	public String newForm(Authentication auth, Model model) {
		DAuthUserDetails user = (DAuthUserDetails) auth.getPrincipal();

		Item item = new Item();
		item.setAuthor(user.getUser());
		item.setAuthorDisplay(user.getName());

		model.addAttribute("item", item);
		model.addAttribute("now", LocalDateTime.now().format(formatter));
		model.addAttribute("formatter", formatter);

		return "edit";
	}

	@GetMapping("/admin/edit")
	public String editForm(@RequestParam(name = "id") Long id, Model model) {
		Item item = itemRepository.findOne(id);

		if (item == null)
			throw new ResourceNotFoundException();

		model.addAttribute("item", item);
		model.addAttribute("now", LocalDateTime.now().format(formatter));
		model.addAttribute("formatter", formatter);

		return "edit";
	}

	@PostMapping("/admin/edit")
	public String doEdit(@Valid Item item, BindingResult bindingResult, Model model) {
		model.addAttribute("now", LocalDateTime.now().format(formatter));
		model.addAttribute("formatter", formatter);

		if (bindingResult.hasErrors())
			return "edit";

		item = itemRepository.save(item);

		return "redirect:/admin/edit?saved=true&id=" + item.getId();
	}

}
