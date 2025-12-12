package com.poseidon.web.mvc;

import com.poseidon.domain.CurvePoint;
import com.poseidon.exception.EntityNotFoundException;
import com.poseidon.repository.CurvePointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/curvePoint")
@RequiredArgsConstructor
public class CurvePointListController {

    private final CurvePointRepository repository;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("curvePoints", repository.findAll(Sort.by(Sort.Direction.DESC, "id")));
        return "curvePoint/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("curvePoint", new CurvePoint());
        return "curvePoint/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid @ModelAttribute("curvePoint") CurvePoint curvePoint, BindingResult result, RedirectAttributes ra) {
        if (result.hasErrors()) return "curvePoint/add";
        repository.save(curvePoint);
        ra.addFlashAttribute("msg", "CurvePoint créé avec succès");
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/update/{id}")
    public String showUpdate(@PathVariable Integer id, Model model) {
        CurvePoint curvePoint = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("CurvePoint not found: " + id));
        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/update";
    }

    @PostMapping("/update/{id}")
    public String doUpdate(@PathVariable Integer id, @Valid @ModelAttribute("curvePoint") CurvePoint curvePoint, BindingResult result, RedirectAttributes ra) {
        if (result.hasErrors()) return "curvePoint/update";
        curvePoint.setId(id); 
        repository.save(curvePoint);
        ra.addFlashAttribute("msg", "CurvePoint mis à jour");
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes ra) {
        if (!repository.existsById(id)) throw new EntityNotFoundException("CurvePoint not found: " + id);
        repository.deleteById(id);
        ra.addFlashAttribute("msg", "CurvePoint supprimé");
        return "redirect:/curvePoint/list";
    }
}