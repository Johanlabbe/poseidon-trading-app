package com.poseidon.web.mvc;

import com.poseidon.domain.Rule;
import com.poseidon.exception.EntityNotFoundException;
import com.poseidon.repository.RuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/ruleName")
@RequiredArgsConstructor
public class RuleListController {

    private final RuleRepository repository;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("ruleNames", repository.findAll(Sort.by(Sort.Direction.DESC, "id")));
        return "ruleName/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("ruleName", new Rule());
        return "ruleName/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid @ModelAttribute("ruleName") Rule ruleName, BindingResult result, RedirectAttributes ra) {
        if (result.hasErrors()) return "ruleName/add";
        repository.save(ruleName);
        ra.addFlashAttribute("msg", "Rule créé");
        return "redirect:/ruleName/list";
    }

    @GetMapping("/update/{id}")
    public String showUpdate(@PathVariable Integer id, Model model) {
        Rule rule = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rule not found: " + id));
        model.addAttribute("ruleName", rule);
        return "ruleName/update";
    }

    @PostMapping("/update/{id}")
    public String doUpdate(@PathVariable Integer id, @Valid @ModelAttribute("ruleName") Rule ruleName, BindingResult result, RedirectAttributes ra) {
        if (result.hasErrors()) return "ruleName/update";
        ruleName.setId(id);
        repository.save(ruleName);
        ra.addFlashAttribute("msg", "Rule mis à jour");
        return "redirect:/ruleName/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes ra) {
        if (!repository.existsById(id)) throw new EntityNotFoundException("Rule not found: " + id);
        repository.deleteById(id);
        ra.addFlashAttribute("msg", "Rule supprimé");
        return "redirect:/ruleName/list";
    }
}