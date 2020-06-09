package Setup;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class Counter {

        private static List findClasses(File directory, String packageName) throws ClassNotFoundException {
                List classes = new ArrayList();
                if (!directory.exists()) {
                        return classes;
                }
                File[] files = directory.listFiles();
                for (File file : files) {
                        if (file.isDirectory()) {
                                assert !file.getName().contains(".");
                                classes.addAll(findClasses(file, packageName + "." + file.getName()));
                        } else if (file.getName().endsWith(".class")) {
                                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
                        }
                }
                return classes;
        }


        private static Class[] getClasses(String packageName)
                throws ClassNotFoundException, IOException {
                ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                assert classLoader != null;
                String path = packageName.replace('.', '/');
                Enumeration resources = classLoader.getResources(path);
                List dirs = new ArrayList();
                while (resources.hasMoreElements()) {
                        URL resource = (URL) resources.nextElement();
                        dirs.add(new File(resource.getFile()));
                }
                ArrayList classes = new ArrayList();
                for (Object directory : dirs) {
                        classes.addAll(findClasses((File) directory, packageName));
                }
                return (Class[]) classes.toArray(new Class[classes.size()]);
        }


        public static int testCounter() throws IOException, ClassNotFoundException {
                int testcaseCount=0;
                for (Class cl : getClasses("Scenarios")) {
                        Method[] methods = cl.getDeclaredMethods();

                        for (Method method : methods) {
                                Annotation[] annotations = method.getDeclaredAnnotations();
                                if (annotations.length == 0 && method.getName().startsWith("test")) {
                                        testcaseCount++;
                                } else {
                                        for (Annotation annotation : annotations) {
                                                if (annotation.annotationType().toString()
                                                        .equals("interface org.junit.Test")) {
                                                        testcaseCount++;
                                                }
                                        }
                                }
                        }
                }
                return testcaseCount;
        }
}






