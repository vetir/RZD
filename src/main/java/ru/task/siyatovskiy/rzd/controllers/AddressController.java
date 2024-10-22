package ru.task.siyatovskiy.rzd.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.task.siyatovskiy.rzd.service.AddressService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/description")
    public List<String> getAddressDescription(@RequestParam String date,
                                              @RequestParam List<Long> objectIds) {
        LocalDate parsedDate = LocalDate.parse(date);
        return addressService.getAddressDescriptions(objectIds, parsedDate);
    }
}
