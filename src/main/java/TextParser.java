import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextParser {

    public static void main(String[] args) {
        String inputText = "Executive Summary:\n\npaymob is a fintech company based in Egypt, specializing in providing payment processing and integration solutions. Our digital payment solution is designed to cater specifically to the needs of small businesses. With a current team of 25 employees, our primary goal is to expand our presence in the MENA region.\n\nCompany Description:\n\npaymob is a leading fintech company in Egypt, offering innovative payment processing and integration solutions. Our mission is to empower small businesses by enabling secure, convenient, and seamless digital payment transactions. With a strong focus on customer satisfaction, we strive to provide cutting-edge technology and exceptional service.\n\nProducts and Services:\n\nWe offer a comprehensive digital payment solution that includes a range of features designed to meet the specific requirements of small businesses. Our services include online payment gateway integration, mobile payment solutions, QR code payments, and secure card processing capabilities. By leveraging the latest technology, we enable businesses to enhance their customer experience while streamlining payment processes.\n\nMarket Analysis:\n\nThe market for digital payment solutions in the MENA region is growing rapidly due to the increasing adoption of e-commerce and the transformation of traditional businesses to digital platforms. Small businesses, in particular, face numerous challenges in embracing digital payment methods, such as costly integration processes and lack of accessible solutions. paymob aims to bridge this gap by providing affordable and user-friendly payment solutions tailored to the needs of small businesses.\n\nStrategy and Implementation:\n\nOur strategy revolves around expansion in the MENA region, capitalizing on the untapped market potential. By focusing on targeted marketing campaigns and strategic partnerships, we aim to increase our brand presence and attract a larger customer base. Additionally, we will invest in research and development to continually enhance our product offerings and stay ahead of competitors.\n\nOrganization and Management Team:\n\npaymob is led by a team of experienced professionals with expertise in fintech, payment solutions, and business development. With a diverse skill set and a shared vision, our management team is committed to driving the growth and success of the company. Each member brings valuable industry knowledge and strategic acumen, ensuring that paymob is well-positioned for continuous expansion.\n\nFinancial Plan:\n\nOur financial plan is centered around sustainable growth and profitability. We anticipate steady revenue streams from transaction fees and service charges. As we expand our reach and build a larger customer base, we expect to achieve economies of scale, which will further enhance our financial performance. To support our growth goals, we will actively seek external funding and strategic partnerships that align with our vision.";

        // Define the pattern for titles
        // the titles are Executive Summary, Company Description, Products and Services, Market Analysis, Strategy and Implementation, Organization and Management Team, Financial Plan
        // pattern for Executive Summary
        Pattern execSumPat = Pattern.compile("Executive Summary:\\n\\n");
        // pattern for Company Description
        Pattern comDescPat = Pattern.compile("Company Description:\\n\\n");
        // pattern for Products and Services
        Pattern prodServPat = Pattern.compile("Products and Services:\\n\\n");
        // pattern for Market Analysis
        Pattern markAnalPat = Pattern.compile("Market Analysis:\\n\\n");
        // pattern for Strategy and Implementation
        Pattern stratImplPat = Pattern.compile("Strategy and Implementation:\\n\\n");
        // pattern for Organization and Management Team
        Pattern orgManTeamPat = Pattern.compile("Organization and Management Team:\\n\\n");
        // pattern for Financial Plan
        Pattern finPlanPat = Pattern.compile("Financial Plan:\\n\\n");

        // Split the input text by titles
        String[] sections = inputText.split(execSumPat.pattern());
        String[] sections2 = sections[1].split(comDescPat.pattern());
        String[] sections3 = sections2[1].split(prodServPat.pattern());
        String[] sections4 = sections3[1].split(markAnalPat.pattern());
        String[] sections5 = sections4[1].split(stratImplPat.pattern());
        String[] sections6 = sections5[1].split(orgManTeamPat.pattern());
        String[] sections7 = sections6[1].split(finPlanPat.pattern());

        // Print the sections
        for (String section : sections2) {
            System.out.println(section);
            System.out.println("--------------------------------------------------");
        }
//
//        // Print the sections
//        for (String section : sections2) {
//            System.out.println(section);
//        }
//
//        // Print the sections
//        for (String section : sections3) {
//            System.out.println(section);
//        }
//
//        // Print the sections
//        for (String section : sections4) {
//            System.out.println(section);
//        }
//
//        // Print the sections
//        for (String section : sections5) {
//            System.out.println(section);
//        }
//
//        // Print the sections
//        for (String section : sections6) {
//            System.out.println(section);
//        }
//
//        // Print the sections
//        for (String section : sections7) {
//            System.out.println(section);
//        }


//        Pattern titlePattern = Pattern.compile("([A-Z][a-z]+\\s[A-Z][a-z]+):");
//
//        // Split the input text by titles
//        String[] sections = inputText.split(titlePattern.pattern());
//
//        // Print the sections
//        for (String section : sections) {
//            System.out.println(section);
//        }
//
//        // Define the pattern for the content of each section
//        Pattern contentPattern = Pattern.compile("([A-Z][a-z]+\\s[A-Z][a-z]+):\\n\\n(.*)\\n\\n");
//
//        // Print the content of each section
//        for (String section : sections) {
//            Matcher matcher = contentPattern.matcher(section);
//            if (matcher.find()) {
//                System.out.println(matcher.group(2));
//            }
//        }

        System.out.println("Done!");
    }
}
