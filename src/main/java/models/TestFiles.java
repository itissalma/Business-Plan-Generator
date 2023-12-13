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
        Plan plan1 = new Plan("salma", "plan1", 1, new String[]{"answer 1", "answer 2", "answer 3", "answer 4", "answer 5", "answer 6", "answer 7"}, new String[]{"section 1", "section 2", "section 3", "section 4", "section 5", "section 6", "section 7"});
        Plan plan2 = new Plan("shady", "plan2", 2, new String[]{"answer 1", "answer 2", "answer 3", "answer 4", "answer 5", "answer 6", "answer 7"}, new String[]{"section 1", "section 2", "section 3", "section 4", "section 5", "section 6", "section 7"});
        Plan plan3 = new Plan("salma", "plan3", 3, new String[]{"answer 1", "answer 2", "answer 3", "answer 4", "answer 5", "answer 6", "answer 7"}, new String[]{"section 1", "section 2", "section 3", "section 4", "section 5", "section 6", "section 7"});

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
