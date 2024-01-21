package infrastructure.service;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.helper.ConditionalHelpers;
import com.github.jknack.handlebars.helper.StringHelpers;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import service.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
@Slf4j
public class WalkAroundCheckService {

    @ConfigProperty(name = "wac.html.file.path")
    String htmlFilePath;

    public String loadTemplateAndReplacePlaceholders() throws IOException {
        String templateSource = new String(Files.readAllBytes(Paths.get(htmlFilePath)), StandardCharsets.UTF_8);
        Handlebars handlebars = new Handlebars();
        handlebars.registerHelpers(StringHelpers.class);
        handlebars.registerHelpers(ConditionalHelpers.class);

        handlebars.registerHelper("equals", (object, options) -> {
            CharSequence result;
            Object param0 = options.param(0);
            if (param0 == null) {
                throw new IllegalArgumentException("found 'null', expected 'string'");
            }
            if (object != null && object.toString().equals(param0.toString())) {
                result = options.fn(options.context);
            } else {
                result = null;
            }
            return result;
        });


        Template template = handlebars.compileInline(templateSource);

        WacReportDto dto = this.getWalkAroundCheck();

        Map<String, Object> data = new HashMap<>();
        data.put("orderNumber", dto.getWorkOrderInfo().getOrderNumber());
        data.put("dropofDate", dto.getWorkOrderInfo().getDropOfDate());
        data.put("pickupDate", dto.getWorkOrderInfo().getPickupDate());
        data.put("customerName", dto.getCustomerInfo().getName());
        data.put("customerSurname", dto.getCustomerInfo().getSurname());
        data.put("customerPhone", dto.getCustomerInfo().getPhone());
        data.put("customerEmail", dto.getCustomerInfo().getEmail());
        data.put("userName", dto.getUserInfo().getName());
        data.put("userSurname", dto.getUserInfo().getSurname());
        data.put("dealerName", dto.getDealerInfo().getName());
        data.put("dealerAddress", dto.getDealerInfo().getAddress());
        data.put("dealerCity", dto.getDealerInfo().getCity());
        data.put("dealerZipcode", dto.getDealerInfo().getZipcode());
        data.put("vehicleBrand", dto.getVehicleInfo().getBrand());
        data.put("vehicleModel", dto.getVehicleInfo().getModel());
        data.put("vehicleMileage", dto.getVehicleInfo().getMileage());
        data.put("vehicleLicencePlate", dto.getVehicleInfo().getLicencePlate());
        data.put("items", dto.getDetails().getItems().stream().collect(Collectors.toList()));

        String result = template.apply(data);
        return result;

    }

    public WacReportDto getWalkAroundCheck() {
        WacReportDto report = new WacReportDto();
        report.setCustomerInfo(new CustomerInfoDto("Betiel", "Abraham", "goitommillion5@gmail.com", "1231-123"));
        report.setDealerInfo(new DealerInfoDto("Emil Frey", "Bern-Ostermundigen", "Milchstrasse 15", "3077"));
        report.setUserInfo(new UserInfoDto("Dominik", "Banger"));
        report.setVehicleInfo(new VehicleInfoDto("ZH641292", "Toyota", "Yaris Cross", 1000));
        report.setWorkOrderInfo(new WorkOrderInfoDto("132-23G-0016", LocalDate.of(2024, 1, 15), LocalDate.of(2024, 1, 16)));
        WacDetailsDto details = new WacDetailsDto();
        Set<WacItemInfoDto> items = new HashSet<>();
        WacItemInfoDto item1 = new WacItemInfoDto();
        item1.setName("Brakes edit");
        item1.setStatus(WacItemInfoDto.WacItemStatus.OK);
        WacItemInfoDto item2 = new WacItemInfoDto();
        item2.setName("Electrical edit 3");
        item2.setStatus(WacItemInfoDto.WacItemStatus.OK);
        WacItemInfoDto item3 = new WacItemInfoDto();
        item3.setName("Innenraum");
        item3.setStatus(WacItemInfoDto.WacItemStatus.OK);
        WacItemInfoDto item4 = new WacItemInfoDto();
        item4.setName("Scheibenwaschanlage");
        item4.setStatus(WacItemInfoDto.WacItemStatus.NOT_OK);
        List<WacItemRepairInfoDto> repairs = new ArrayList<>();
        repairs.add(new WacItemRepairInfoDto("Repair 1", "Description of repair 1", BigDecimal.valueOf(125)));
        repairs.add(new WacItemRepairInfoDto("Repair 2", "Description of repair 2", BigDecimal.valueOf(442)));
        item4.setRepairs(repairs);

        WacItemInfoDto item5 = new WacItemInfoDto();
        item5.setName("Klimaanlage");
        item5.setStatus(WacItemInfoDto.WacItemStatus.NOT_OK);
        List<WacItemRepairInfoDto> repairs5 = new ArrayList<>();
        repairs5.add(new WacItemRepairInfoDto("Klima Repair 1", "Description of klima repair 1", BigDecimal.valueOf(11)));
        repairs5.add(new WacItemRepairInfoDto("Klima Repair 2", "Description of klima repair 2", BigDecimal.valueOf(222)));
        item5.setRepairs(repairs);

        WacItemInfoDto item6 = new WacItemInfoDto();
        item6.setName("Motor");
        item6.setStatus(WacItemInfoDto.WacItemStatus.OK);

        items.add(item1);
        items.add(item2);
        items.add(item3);
        items.add(item4);
        items.add(item5);
        items.add(item6);
        details.setItems(items);
        report.setDetails(details);
        return report;

    }

    /*
    public byte[] generatePdfFromHtml(String htmlContent) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.getSharedContext().getTextRenderer().setSmoothingThreshold(0.0f);
            renderer.layout();

            try (PDDocument document = new PDDocument()) {
                PDPageTree pages = document.getPages();

                try (ByteArrayOutputStream tempOutputStream = new ByteArrayOutputStream()) {
                    renderer.createPDF(tempOutputStream);

                    try (PDDocument tempDocument = PDDocument.load(tempOutputStream.toByteArray())) {
                        PDPage importedPage = document.importPage(tempDocument.getPage(0));
                        pages.add(importedPage);
                    }
                }

                document.save(outputStream);
            }

            return outputStream.toByteArray();
        }
    }*/


}
