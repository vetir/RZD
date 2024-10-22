package ru.task.siyatovskiy.rzd.service;

import org.springframework.stereotype.Service;
import ru.task.siyatovskiy.rzd.model.Address;
import ru.task.siyatovskiy.rzd.model.Hierarchy;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class XmlParserService {

    public List<Address> parseAddresses(String filePath) throws FileNotFoundException, XMLStreamException {
        List<Address> addresses = new ArrayList<>();
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(new FileInputStream(filePath));

        while (reader.hasNext()) {
            int event = reader.next();

            if (event == XMLStreamReader.START_ELEMENT && "OBJECT".equals(reader.getLocalName())) {
                Address address = Address.builder()
                        .id(parseLongOrNull(reader.getAttributeValue(null, "ID")))
                        .objectId(parseLongOrNull(reader.getAttributeValue(null, "OBJECTID")))
                        .objectGuid(reader.getAttributeValue(null, "OBJECTGUID"))
                        .changeId(parseLongOrNull(reader.getAttributeValue(null, "CHANGEID")))
                        .name(reader.getAttributeValue(null, "NAME"))
                        .typeName(reader.getAttributeValue(null, "TYPENAME"))
                        .level(parseIntOrNull(reader.getAttributeValue(null, "LEVEL")))
                        .operTypeId(parseIntOrNull(reader.getAttributeValue(null, "OPERTYPEID")))
                        .prevId(parseLongOrNull(reader.getAttributeValue(null, "PREVID")))
                        .nextId(parseLongOrNull(reader.getAttributeValue(null, "NEXTID")))
                        .updateDate(reader.getAttributeValue(null, "UPDATEDATE"))
                        .startDate(reader.getAttributeValue(null, "STARTDATE"))
                        .endDate(reader.getAttributeValue(null, "ENDDATE"))
                        .isActual(parseIntOrNull(reader.getAttributeValue(null, "ISACTUAL")))
                        .isActive(parseIntOrNull(reader.getAttributeValue(null, "ISACTIVE")))
                        .build();

                addresses.add(address);
            }
        }
        reader.close();
        return addresses;
    }

    public List<Hierarchy> parseHierarchies(String filePath) throws FileNotFoundException, XMLStreamException {
        List<Hierarchy> hierarchies = new ArrayList<>();
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(new FileInputStream(filePath));

        while (reader.hasNext()) {
            int event = reader.next();

            if (event == XMLStreamReader.START_ELEMENT && "ITEM".equals(reader.getLocalName())) {
                Hierarchy hierarchy = Hierarchy.builder()
                        .id(parseLongOrNull(reader.getAttributeValue(null, "ID")))
                        .objectId(parseLongOrNull(reader.getAttributeValue(null, "OBJECTID")))
                        .parentObjId(parseLongOrNull(reader.getAttributeValue(null, "PARENTOBJID")))
                        .changeId(parseLongOrNull(reader.getAttributeValue(null, "CHANGEID")))
                        .prevId(parseLongOrNull(reader.getAttributeValue(null, "PREVID")))
                        .nextId(parseLongOrNull(reader.getAttributeValue(null, "NEXTID")))
                        .updateDate(reader.getAttributeValue(null, "UPDATEDATE"))
                        .startDate(reader.getAttributeValue(null, "STARTDATE"))
                        .endDate(reader.getAttributeValue(null, "ENDDATE"))
                        .isActive(parseIntOrNull(reader.getAttributeValue(null, "ISACTIVE")))
                        .build();

                hierarchies.add(hierarchy);
            }
        }
        reader.close();
        return hierarchies;
    }

    private Long parseLongOrNull(String value) {
        return (value != null && !value.isEmpty()) ? Long.parseLong(value) : null;
    }

    private Integer parseIntOrNull(String value) {
        return (value != null && !value.isEmpty()) ? Integer.parseInt(value) : null;
    }
}
