package ru.task.siyatovskiy.rzd.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.task.siyatovskiy.rzd.configuration.FileProperties;
import ru.task.siyatovskiy.rzd.model.Address;
import ru.task.siyatovskiy.rzd.model.Hierarchy;
import ru.task.siyatovskiy.rzd.utils.AddressProcessingException;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HierarchyService {

    private final XmlParserService xmlParserService;
    private final FileProperties fileProperties;

    public List<String> getFullAddressChains() {
        try {

            String hierarchyFilePath = fileProperties.getAddressHierarchy();
            List<Hierarchy> hierarchies = xmlParserService.parseHierarchies(hierarchyFilePath);

            String addressFilePath = fileProperties.getAddressObjects();
            List<Address> addresses = xmlParserService.parseAddresses(addressFilePath);

            return buildAddressChains(hierarchies, addresses, "проезд");
        } catch (FileNotFoundException | XMLStreamException e) {
            throw new AddressProcessingException("Ошибка при обработке данных адресов или иерархий", e);
        }
    }

    private List<String> buildAddressChains(List<Hierarchy> hierarchies, List<Address> addresses, String filterTypeName) {
        List<Address> filteredAddresses = filterAddressesByType(addresses, filterTypeName);

        return filteredAddresses.stream()
                .map(address -> buildAddressChain(address.getObjectId(), hierarchies, addresses))
                .filter(chain -> !chain.isEmpty())
                .toList();
    }

    private List<Address> filterAddressesByType(List<Address> addresses, String typeName) {
        return addresses.stream()
                .filter(a -> typeName.equalsIgnoreCase(a.getTypeName()))
                .toList();
    }

    private String buildAddressChain(Long objectId, List<Hierarchy> hierarchies, List<Address> addresses) {
        StringBuilder addressChain = new StringBuilder();
        Optional<Hierarchy> hierarchyOpt = findHierarchyByObjectId(objectId, hierarchies);

        while (hierarchyOpt.isPresent()) {
            Hierarchy hierarchy = hierarchyOpt.get();
            findAddressByObjectId(hierarchy.getObjectId(), addresses)
                    .ifPresent(address -> addressChain.insert(0, address.getTypeName() + " " + address.getName() + ", "));

            hierarchyOpt = findHierarchyByObjectId(hierarchy.getParentObjId(), hierarchies);
        }

        return addressChain.toString().replaceAll(", $", "");
    }

    private Optional<Hierarchy> findHierarchyByObjectId(Long objectId, List<Hierarchy> hierarchies) {
        return hierarchies.stream()
                .filter(h -> h.getObjectId().equals(objectId))
                .findFirst();
    }

    private Optional<Address> findAddressByObjectId(Long objectId, List<Address> addresses) {
        return addresses.stream()
                .filter(a -> a.getObjectId().equals(objectId))
                .findFirst();
    }

}
