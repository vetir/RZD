package ru.task.siyatovskiy.rzd.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.task.siyatovskiy.rzd.configuration.FileProperties;
import ru.task.siyatovskiy.rzd.model.Address;
import ru.task.siyatovskiy.rzd.utils.AddressProcessingException;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final XmlParserService xmlParserService;
    private final FileProperties fileProperties;

    public List<String> getAddressDescriptions(List<Long> objectIds, LocalDate date) {
        try {
            String addressFilePath = fileProperties.getAddressObjects();
            List<Address> addresses = xmlParserService.parseAddresses(addressFilePath);

            return addresses.stream()
                    .filter(a -> objectIds.contains(a.getObjectId()) &&
                            !date.isBefore(LocalDate.parse(a.getStartDate())) &&
                            !date.isAfter(LocalDate.parse(a.getEndDate())))
                    .map(a -> a.getObjectId() + ": " + a.getTypeName() + " " + a.getName())
                    .toList();

        } catch (FileNotFoundException | XMLStreamException e) {
            throw new AddressProcessingException("Ошибка при получении описаний адресов", e);
        }
    }

}
