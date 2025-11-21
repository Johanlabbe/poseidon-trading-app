package com.poseidon.web.mvc;

import com.poseidon.domain.Bid;
import com.poseidon.exception.EntityNotFoundException;
import com.poseidon.repository.BidRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/bidList")
@RequiredArgsConstructor
public class BidListController {
  private final BidRepository repository;

  @GetMapping("/list")
  public String list(Model model) {
    model.addAttribute("bidLists", repository.findAll(Sort.by(Sort.Direction.DESC, "bidListId")));
    return "bidList/list";
  }

  @GetMapping("/add")
  public String addForm(Model model) {
    model.addAttribute("bid", new Bid());
    return "bidList/add";
  }

  @PostMapping("/validate")
  public String validate(@Valid @ModelAttribute("bid") Bid bid, BindingResult result, RedirectAttributes ra) {
    if (result.hasErrors()) return "bidList/add";
    repository.save(bid);
    ra.addFlashAttribute("msg", "Bid créé");
    return "redirect:/bidList/list";
  }

  @GetMapping("/update/{id}")
  public String showUpdate(@PathVariable Integer id, Model model) {
    Bid bid = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Bid not found: " + id));
    model.addAttribute("bid", bid);
    return "bidList/update";
  }

  @PostMapping("/update/{id}")
  public String doUpdate(@PathVariable Integer id, @Valid @ModelAttribute("bid") Bid form, BindingResult result, RedirectAttributes ra) {
    if (result.hasErrors()) return "bidList/update";
    form.setBidListId(id);
    repository.save(form);
    ra.addFlashAttribute("msg", "Bid mis à jour");
    return "redirect:/bidList/list";
  }

  @GetMapping("/delete/{id}")
  public String delete(@PathVariable Integer id, RedirectAttributes ra) {
    if (!repository.existsById(id)) throw new EntityNotFoundException("Bid not found: " + id);
    repository.deleteById(id);
    ra.addFlashAttribute("msg", "Bid supprimé");
    return "redirect:/bidList/list";
  }
}
