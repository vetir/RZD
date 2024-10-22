package ru.task.siyatovskiy.rzd.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.task.siyatovskiy.rzd.service.HierarchyService;

import java.util.List;

@RestController
@RequestMapping("/api/hierarchy")
@RequiredArgsConstructor
public class HierarchyController {

    private final HierarchyService hierarchyService;

    @GetMapping("/full-chain")
    public List<String> getFullAddressChains() {
        return hierarchyService.getFullAddressChains();
    }
}
