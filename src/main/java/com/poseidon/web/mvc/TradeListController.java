package com.poseidon.web.mvc;

import com.poseidon.domain.Trade;
import com.poseidon.exception.EntityNotFoundException;
import com.poseidon.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/trade")
@RequiredArgsConstructor
public class TradeListController {

    private final TradeRepository repository;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("trades", repository.findAll(Sort.by(Sort.Direction.DESC, "tradeId")));
        return "trade/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("trade", new Trade());
        return "trade/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid @ModelAttribute("trade") Trade trade, BindingResult result, RedirectAttributes ra) {
        if (result.hasErrors()) return "trade/add";
        repository.save(trade);
        ra.addFlashAttribute("msg", "Trade créé");
        return "redirect:/trade/list";
    }

    @GetMapping("/update/{id}")
    public String showUpdate(@PathVariable Integer id, Model model) {
        Trade trade = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Trade not found: " + id));
        model.addAttribute("trade", trade);
        return "trade/update";
    }

    @PostMapping("/update/{id}")
    public String doUpdate(@PathVariable Integer id, @Valid @ModelAttribute("trade") Trade trade, BindingResult result, RedirectAttributes ra) {
        if (result.hasErrors()) return "trade/update";
        trade.setTradeId(id); 
        repository.save(trade);
        ra.addFlashAttribute("msg", "Trade mis à jour");
        return "redirect:/trade/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes ra) {
        if (!repository.existsById(id)) throw new EntityNotFoundException("Trade not found: " + id);
        repository.deleteById(id);
        ra.addFlashAttribute("msg", "Trade supprimé");
        return "redirect:/trade/list";
    }
}