package com.leetCode.string;

public class BinaryAddition {
    public static void main(String[] args) {
        String s1 = "1010";
        String s2 = "1011";
        addString(s1, s2);
    }

    private static void addString(String s1, String s2) {
        int pre = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= s1.length() || i <= s2.length(); i++) {
            int v1 = s1.length() - i < 0 ? 0 : Integer.parseInt(s1.charAt(s1.length() - i) + "");
            int v2 = s2.length() - i < 0 ? 0 : Integer.parseInt(s2.charAt(s2.length() - i) + "");
            int tmp = v1 + v2 + pre;
            if (tmp > 1) {
                pre = 1;
                sb.append(tmp - 2);
            } else {
                pre = 0;
                sb.append(tmp);
            }
        }
        if (pre > 0) {
            sb.append(pre);
        }
        System.out.println(sb.reverse().toString());
    }

    /*
    student(id, name)
    teacher(id, name)
    course(id, name)
    teacher_course(id, teacher_id, course_id)
    student_course(id, student_id, teacher_course_id)

    student_course(id, student_id, teacher_course_id)

    select s.student_id,s.name
        from course c
        left join teacher_course tc on c.name = '数据结构' and tc.course_id = course.id
        left join student_course sc on sc.teacher_course_id = tc.id
        left join student s on sc.student_id = s.id


    */
}
