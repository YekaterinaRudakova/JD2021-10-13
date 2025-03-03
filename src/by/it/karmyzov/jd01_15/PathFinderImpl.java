package by.it.karmyzov.jd01_15;

import java.io.File;

class PathFinderImpl implements PathFinder {

    PathFinderImpl() {
    }

    public String getStringPath(Class<?> aClass, String filename) {
        String root = System.getProperty("user.dir");
        System.out.println(root);
        String className = aClass.getName();
        String packageName = className.replace(aClass.getSimpleName(), "");
        String packagePath = packageName.replace(".", File.separator);
        return root + File.separator + "src" + File.separator + packagePath + filename;
    }
}
