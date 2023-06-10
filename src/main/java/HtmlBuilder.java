import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;

import freemarker.template.TemplateNotFoundException;
import org.apache.commons.io.FileUtils;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class HtmlBuilder {
    private final StringWriter writer;
    private final Configuration configuration;
    private final Map<String, Object> context;

    public HtmlBuilder() {
        writer = new StringWriter();
        configuration = new Configuration(Configuration.VERSION_2_3_31);
        configuration.setDefaultEncoding("UTF-8");

        context = new HashMap<>();
    }

    public HtmlBuilder add(String key, Object value) {
        context.put(key, value);
        return this;
    }

    public String generate(String templateFilePath, String outputFilePath) throws IOException, TemplateException {
        File templateFile = new File(templateFilePath);
        if (!templateFile.exists()) {
            throw new IOException("Template file not found: " + templateFilePath);
        }

        configuration.setDirectoryForTemplateLoading(templateFile.getParentFile());

        Template template;
        try {
            template = configuration.getTemplate(templateFile.getName());
        } catch (TemplateNotFoundException e) {
            throw new IOException("Template file not found: " + templateFilePath);
        }
        template.process(context, writer);
        String generatedHtml = writer.toString();
        writeToFile(generatedHtml, outputFilePath);
        FileUtils.forceDelete(templateFile);
        return generatedHtml;
    }

    private void writeToFile(String content, String filePath) throws IOException {
        FileWriter fileWriter = new FileWriter(filePath);
        fileWriter.write(content);
        fileWriter.close();
    }
}
