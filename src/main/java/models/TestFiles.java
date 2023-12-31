//Shady Nessim 900191322 Salma Aly 900203182
package models;

import java.util.List;

public class TestFiles {
    public static void main(String[] args) {
        // create dummy users
        User user1 = new User("salma", "salma", "s1@s.s", "123456789", "USA");
        User user2 = new User("shady", "shady", "s2@s.s", "123456789", "EGY");

        // add users to file
        UserFileHandler userFileHandler = new UserFileHandler();
        userFileHandler.addObject(user1);
        userFileHandler.addObject(user2);

        // get users from file
        List<User> userList = userFileHandler.getObjects();
        System.out.println(userList);

        // create dummy business plans
        Plan plan1 = new Plan("salma", "plan1",
                new String[]{
                        "ABC Company",
                        "ABC Company is a tech startup specializing in software development.",
                        "United States",
                        "We offer custom software solutions for businesses.",
                        "Our target audience includes small and medium-sized enterprises.",
                        "Currently, we have 10 employees.",
                        "Our goals include expanding our client base and increasing revenue."
                },
                new String[]{
                        "ABC Company is a startup based in the United States, focused on providing innovative software solutions for businesses. Our goal is to cater to the needs of small and medium-sized enterprises by offering tailored software services.",
                        "ABC Company was founded with the vision of revolutionizing the software development industry. We pride ourselves on our team of skilled professionals dedicated to delivering high-quality solutions.",
                        "At ABC Company, we specialize in developing custom software applications tailored to meet the unique requirements of our clients. Our services include software design, development, testing, and maintenance.",
                        "In the competitive landscape of the software industry, ABC Company aims to carve a niche by providing cutting-edge solutions. Our market analysis indicates a growing demand for customized software, and we are well-positioned to capitalize on this trend.",
                        "Our strategy involves leveraging the latest technologies to create robust and scalable software solutions. We emphasize collaboration with clients throughout the development process to ensure their needs are met.",
                        "ABC Company's success is attributed to our dedicated team of professionals. From experienced software developers to skilled project managers, each member plays a crucial role in our organization.",
                        "We project steady growth in revenue over the next few years. Our financial plan includes careful budgeting, investment in research and development, and strategic marketing initiatives."
                });

        Plan plan2 = new Plan("shady", "plan2",
                new String[]{
                        "XYZ Solutions",
                        "XYZ Solutions is a consulting firm providing business advisory services.",
                        "Canada",
                        "We offer strategic consulting services to various industries.",
                        "Our target clients are large enterprises seeking strategic guidance.",
                        "We have a team of 15 experienced consultants.",
                        "Our goals include achieving client satisfaction and expanding our service offerings."
                },
                new String[]{
                        "XYZ Solutions is a leading business advisory firm based in Canada. Our expertise lies in providing strategic consulting services to diverse industries, with a focus on large enterprises seeking innovative solutions.",
                        "At XYZ Solutions, we are committed to helping businesses thrive through strategic guidance and expert consulting. Our team of experienced consultants brings a wealth of knowledge and insights to our clients.",
                        "Our services encompass a wide range of strategic consulting offerings, including market analysis, financial planning, and organizational development. We tailor our services to meet the specific needs of our clients.",
                        "As a business advisory firm, XYZ Solutions operates in a dynamic market. Our market analysis reveals opportunities for growth, and we are poised to capitalize on emerging trends and industry shifts.",
                        "Our strategy involves continuous learning and adaptation to the evolving business landscape. We implement tailored solutions for each client, ensuring that our recommendations align with their unique goals.",
                        "The success of XYZ Solutions is rooted in the expertise of our management team and the dedication of our consultants. Our leadership brings years of industry experience to guide our clients toward success.",
                        "XYZ Solutions is on a trajectory of sustainable growth. Our financial plan includes prudent financial management, strategic investments in technology, and initiatives to expand our service offerings."
                });

        Plan plan3 = new Plan("salma", "plan3",
                new String[]{
                        "123 Enterprises",
                        "123 Enterprises is a retail business specializing in electronics.",
                        "United Kingdom",
                        "We sell a wide range of electronic products, including smartphones and gadgets.",
                        "Our target customers are tech enthusiasts and individuals seeking the latest gadgets.",
                        "Currently, we have 20 employees.",
                        "Our goals include expanding our product line and opening new retail locations."
                },
                new String[]{
                        "123 Enterprises is a dynamic retail business based in the United Kingdom, specializing in the sale of cutting-edge electronic products. Our commitment is to provide tech enthusiasts with the latest gadgets.",
                        "At 123 Enterprises, we curate a diverse range of electronic products, including smartphones, gadgets, and accessories. Our emphasis is on offering quality products that meet the evolving needs of our customers.",
                        "We take pride in our extensive product line, which features the latest technological innovations. Our services include personalized customer support, product demonstrations, and an online shopping platform.",
                        "The market analysis indicates a growing demand for innovative electronic products, and 123 Enterprises is well-positioned to meet this demand. Our focus on quality and customer satisfaction sets us apart in the competitive market.",
                        "Our strategy involves continuous innovation, ensuring that we stay ahead of industry trends. We implement effective marketing campaigns and explore partnerships to enhance our product offerings.",
                        "123 Enterprises is driven by a dedicated team of professionals passionate about technology. From our skilled sales team to efficient operations staff, each member contributes to our success.",
                        "We project steady growth in revenue as we expand our product line and reach new customers. Our financial plan includes investments in marketing, technology, and the opening of strategically located retail outlets."
                });

        // add plans to file
        PlanFileHandler planFileHandler = new PlanFileHandler();
        planFileHandler.addObject(plan1);
        planFileHandler.addObject(plan2);
        planFileHandler.addObject(plan3);

        // get plans from file
        List<Plan> planList = planFileHandler.getObjects();
        for (Plan plan : planList) {
            System.out.println(plan);
        }
    }
}
