package com.hoangnam25.hnam_courseware.utils;

import java.util.List;
import java.util.Random;

public class AvatarUtils {
    private static final Random random = new Random();

    public static String getRandomAvatar() {
        return Constants.AVATARS.get(random.nextInt(Constants.AVATARS.size()));
    }

    public static String getRandomStudentAvatar() {
        List<String> students = Constants.AvatarCategories.STUDENTS;
        return students.get(random.nextInt(students.size()));
    }

    public static String getRandomInstructorAvatar() {
        List<String> teachers = Constants.AvatarCategories.TEACHERS;
        return teachers.get(random.nextInt(teachers.size()));
    }
}