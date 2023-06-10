import freemarker.template.TemplateException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, TemplateException {
        String templateFilePath = "src/main/java/template.ftl";
        String outputFilePath = "output.html";
        String html = new HtmlBuilder()
                .add("title", "LpSolutions")
                .add("repositoryUrl", "https://github.com/balage91/LpSolutionTestRepo")
                .add("nameParameter", "Szabó-Demény Balázs")
                .add("emailParameter", "sz.d.balazs@gmail.com")
                .generate(templateFilePath, outputFilePath);
        System.out.println(html);
    }
}