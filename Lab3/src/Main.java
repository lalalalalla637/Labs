import java.lang.reflect.*;

/*public class Main {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
       /* //1
        try {
            Class<?> myClass = Class.forName("java.util.Date");
        } catch (ClassNotFoundException e) {
            System.out.println("Class was not found");
        }
        try {
            Class<?> myClass2 = Class.forName("Student");
        } catch (ClassNotFoundException e) {
            System.out.println("Class was not found");
        }
//2
        Student student = new Student();
        Class studentClass = student.getClass();
        //3
        Class<Student> studentClass2 = Student.class;
        Class<Integer> studentClass3 = Integer.class;
        Class<Integer> intt = int.class;*/

        /*Student student = new Student();
        Class studentClass = student.getClass();

        Field[] declaredFields = studentClass.getDeclaredFields();
        for (Field field :declaredFields) {
            System.out.println(field);
        }
        Method[] declaredMethods =
                studentClass.getDeclaredMethods();
        for (Method method : declaredMethods) {
            System.out.println(method);
        }
        Field[] Fields = studentClass.getFields();
        Method[] Methods = studentClass.getMethods();*/


        /*Student student = new Student();
        Class studentClass = student.getClass();
        int classModifiers = studentClass.getModifiers();
        System.out.println(classModifiers);*/

        /*Student newStudent = new Student("Max", "KI-21");
        String group = newStudent.getGroup();
        String name = null;
        try {
            Field field =
                    newStudent.getClass().getDeclaredField("name");
            field.setAccessible(true);
            name = (String) field.get(newStudent);
        } catch (NoSuchFieldException |
                 IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println(name+"\t"+group);*/

        /*Student newStudent = new Student("Max", "KI-21");
        Method[] methods =
                newStudent.getClass().getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getName().equals("getGroup")) {

                System.out.println(methods[i].invoke(newStudent));
            }
        }*/
        /*Object obj = new Student("Anna", "KI-21");
        Student Ann = (Student)obj;
        System.out.println(Ann);*/

        /*Student student = new Student();
        Class studentClass = student.getClass();


        try {
            Student Alice = (Student)studentClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        try {
            Object o = studentClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }*/

//    }
//}

public class Main {
    public static void main(String[] args) {
        func fun = new func();
        Calc e = (Calc)
                Proxy.newProxyInstance(fun.getClass().getClassLoader(),
                        fun.getClass().getInterfaces(), new FunHandler(fun));
        e.f(1.0);
    }
}
