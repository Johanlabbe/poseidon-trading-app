package com.poseidon.web.mvc;

import com.poseidon.domain.Rating;
import com.poseidon.exception.EntityNotFoundException;
import com.poseidon.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/rating")
@RequiredArgsConstructor
public class RatingListController {

    private final RatingRepository repository;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("ratings", repository.findAll(Sort.by(Sort.Direction.DESC, "id")));
        return "rating/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("rating", new Rating());
        return "rating/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid @ModelAttribute("rating") Rating rating, BindingResult result, RedirectAttributes ra) {
        if (result.hasErrors()) return "rating/add";
        repository.save(rating);
        ra.addFlashAttribute("msg", "Rating créé");
        return "redirect:/rating/list";
    }

    @GetMapping("/update/{id}")
    public String showUpdate(@PathVariable Integer id, Model model) {
        Rating rating = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rating not found: " + id));
        model.addAttribute("rating", rating);
        return "rating/update";
    }

    @PostMapping("/update/{id}")
    public String doUpdate(@PathVariable Integer id, @Valid @ModelAttribute("rating") Rating rating, BindingResult result, RedirectAttributes ra) {
        if (result.hasErrors()) return "rating/update";
        rating.setId(id);
        repository.save(rating);
        ra.addFlashAttribute("msg", "Rating mis à jour");
        return "redirect:/rating/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes ra) {
        if (!repository.existsById(id)) throw new EntityNotFoundException("Rating not found: " + id);
        repository.deleteById(id);
        ra.addFlashAttribute("msg", "Rating supprimé");
        return "redirect:/rating/list";
    }
}