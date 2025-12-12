package com.poseidon.web.mvc;

import com.poseidon.domain.User;
import com.poseidon.exception.EntityNotFoundException;
import com.poseidon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserListController {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder; 

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("users", repository.findAll(Sort.by(Sort.Direction.DESC, "id")));
        return "user/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("user", new User());
        return "user/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid @ModelAttribute("user") User user, BindingResult result, RedirectAttributes ra) {
        if (result.hasErrors()) return "user/add";
        
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
        
        ra.addFlashAttribute("msg", "User créé");
        return "redirect:/user/list";
    }

    @GetMapping("/update/{id}")
    public String showUpdate(@PathVariable Integer id, Model model) {
        User user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
        user.setPassword(""); 
        model.addAttribute("user", user);
        return "user/update";
    }

    @PostMapping("/update/{id}")
    public String doUpdate(@PathVariable Integer id, @Valid @ModelAttribute("user") User user, BindingResult result, RedirectAttributes ra) {
        if (result.hasErrors()) return "user/update";
        
        user.setId(id);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        repository.save(user);
        ra.addFlashAttribute("msg", "User mis à jour");
        return "redirect:/user/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes ra) {
        if (!repository.existsById(id)) throw new EntityNotFoundException("User not found: " + id);
        repository.deleteById(id);
        ra.addFlashAttribute("msg", "User supprimé");
        return "redirect:/user/list";
    }
}