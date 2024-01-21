package infrastructure.rest;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import infrastructure.service.WalkAroundCheckService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RequestScoped
@Path("/generate-pdf")
@Slf4j
public class GeneratePdfResource {

    @ConfigProperty(name = "wac.html.file.path")
    String htmlFilePath;

    @Inject
    WalkAroundCheckService wacService;

    @GET
    @Path("/wac")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/pdf")
    public Response exportWac() throws IOException {
        String content = wacService.loadTemplateAndReplacePlaceholders();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.useFastMode();
        builder.useDefaultPageSize(297, 420, PdfRendererBuilder.PageSizeUnits.MM);

        builder.withHtmlContent(content, null);
        builder.testMode(false);
        builder.toStream(outputStream);
        builder.run();
        outputStream.close();
        return Response.ok(outputStream.toByteArray())
                .header("content-disposition", "attachment; filename = wac.pdf")
                .build();
    }

    @GET
    @Path("/html")
    @Produces(MediaType.TEXT_HTML)
    public Response exportHtml() throws IOException {
        String content = wacService.loadTemplateAndReplacePlaceholders();
        return Response.ok().entity(content).build();
    }


}
